/* String-keyed tries.

You can easily find articles explaining what tries are.
Most often they will have strings as keys and the strings are considered character by character.
The keys themselves determine the structure of the "trie".
The root of the trie is always associated with the empty string as its key.
Let's say you also need keys "ab", "abc", "abx", and "abcd".
This forms the trie:
			""
			|
			a
			|
			ab
		/    \
	abx    abc
					|
				abcd

The key associated with a parent node is always a *prefix* of the keys associated with its descendants (in the subtree beneath it).
A node can have an arbitrary number of children: up to 26 if we're limited to lower case characters, more if other chars are allowed.
Each child extends the parent key by one character.

Notice that even though "a" was not one of the keys we need, we still need a node in the trie that correspond to it, for otherwise we won't be able to form the nodes beneath it.

At each node, we can optionally store a value.
Not all nodes will have values: sometimes only the leave nodes of the tree will have values.
Unlike hashmaps and treemaps, however, we don't need to store the keys inside the nodes: the path from the root to the node defines the key.

The time complexity of searching, inserting or removing a key is O(length_of_key).
Some sources says this is O(n) but that's misleading: n is the not the number of nodes in the trie but the length of the longest key, which correspond to the *depth* of the trie.

Tries were invented by AT&T, the only phone company back in the 1950's to store information about phone numbers.
It allows, for example, numbers with the same area code to be grouped together in the same "subtrie".
Unlike hashmap/treemap, a trie allows us to consider an entire group of values that's associated with a key *prefix*.
Each key prefix defines a subtree of other keys and values.
We can get all numbers at Hofstra with the prefix "516-463".
Even though 516-463 is not an actual phone number, it is a node in the trie: it contains no value but its 10-digit descendants will have values.

This program will make heavy use of both the Optional and Stream monads.
*/
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Stream;
import java.util.function.*;

//record rankedword(String word, int rank) {}
abstract class AbstTrie<KT, KCT, VT> {
	record KVPair<K, V>(K key, V val) {
		@Override
		public String toString() {
			return (key + " : " + val);
		}
	}
	public class Node {
		Optional<VT> item = Optional.empty();
		HashMap<KCT, Node> children = new HashMap<KCT, Node>();
		Node() {}
		Node(VT x) {
			item = Optional.ofNullable(x);
		}
		void cleanup() {
	    HashSet<KCT> to_remove = new HashSet<KCT>();
	    for (var c : children.keySet()) {
				Node child = children.get(c);
				child.cleanup();
				if (child.item.isEmpty() && (child.children.size() == 0)) {
					//this.children.remove(c);
					to_remove.add(c);
					nodes--;
				}
	    }
	    for (var c : to_remove) {
				children.remove(c);
			}
		}
		Stream<KVPair<KT, VT>> stream(KT prefix, int depth) {
	    if (depth < 1) {
				return Stream.empty();
			}
	    return Stream.concat(item.stream().map(i -> new KVPair<KT, VT>(prefix, i)), children.keySet().stream().flatMap(c -> children.get(c).stream(kt_add_kct(prefix, c), depth - 1)));
		}
	}
	int size = 0;
	int nodes = 1;
	Node root = new Node();
	private Optional<Node> continue_node = Optional.of(root);
	private KT continue_key = empty_key();
	public int size() {
		return size;
	}
	public double load_factor() {
		return ((size * 1.0) / nodes);
	}
	public Optional<VT> and_modify(KT key, Function<Optional<VT>, ? extends VT> modifier) {
		Optional<VT> answer = Optional.empty();
		if ((key == null) || (modifier == null)) {
			return answer;
		}
		Node current = root;
		int k = 0;
		while (k < key_length(key)) {
	    current = current.children.computeIfAbsent(kctAt(key, k), p -> {
				nodes++;
				return new Node();
			});
	    k++;
		}
		answer = current.item;
		current.item = Optional.ofNullable(modifier.apply(current.item));
		if (answer.isEmpty() && current.item.isPresent()) {
			size++;
		}
		return answer;
	}
	public Optional<VT> set(KT key, VT val) {
		return and_modify(key, x -> val);
	}
	Optional<VT> search(KT key, boolean delete) {
		Optional<VT> answer = Optional.empty();
		if (key == null) {
			return answer;
		}
		Node current = root;
		int k = 0;
		while (k < key_length(key)) {
	    current = current.children.get(kctAt(key, k));
	    if (current == null) {
				return answer;
			}
	    k++;
		}
		answer = current.item;
		if (delete && answer.isPresent()) {
	    current.item = Optional.empty();
	    size--;
		}
		return answer;
	}
	public Optional<VT> get(KT key) {
		return search(key, false);
	}
	public Optional<VT> remove(KT key) {
		return search(key, true);
	}
	public int cleanup() {
		int snodes = nodes;
		root.cleanup();
		return (snodes - nodes);
	}
	public void reset_continuation() {
		continue_node = Optional.of(root);
		continue_key = empty_key();
	}
	public void begin_continuation(KT start) {
		if (start == null) {
			return;
		}
		continue_key = start;
		Node current = root;
		int k = 0;
		while(k < key_length(start)) {
			current = current.children.get(kctAt(start, k));
			if (current == null) {
				continue_node = Optional.empty();
				return;
			}
			k++;
		}
		continue_node = Optional.of(current);
	}
	public boolean can_continue() {
		return continue_node.isPresent();
	}
	public KT current_key() {
		return continue_key;
	}
	public Optional<VT> current_val() {
		return continue_node.flatMap(n -> n.item);
	}
	public boolean continue_search(KCT nextkct) {
		continue_key = kt_add_kct(continue_key, nextkct);
		continue_node = continue_node.flatMap(cn -> Optional.ofNullable(cn.children.get(nextkct)));
		return continue_node.isPresent();
	}
	public Stream<KVPair<KT, VT>> current_stream(int depth) {
		return continue_node.stream().flatMap(n -> n.stream(continue_key, depth));
	}
	public Stream<KVPair<KT, VT>> current_stream() {
		return current_stream(0x7fffffff);
	}
	public Stream<KVPair<KT, VT>> stream() {
		return root.stream(empty_key(), 0);
	}
	public Stream<KVPair<KT, VT>> stream(KT prefix, int depth) {
		if (prefix == null) {
			return Stream.empty();
		}
		if (depth < 1) {
			depth = 0x7fffffff;
		}
		Node current = root;
		int k = 0;
		while (k < key_length(prefix)) {
	    current = current.children.get(kctAt(prefix, k));
	    if (current == null) {
				return Stream.empty();
			}
	    k++;
		}
		return current.stream(prefix, depth);
	}
	public Stream<KVPair<KT, VT>> stream(KT prefix) {
		return stream(prefix, 0);
	}
	protected abstract int key_length(KT key);
	protected abstract KCT kctAt(KT key, int i);
	protected abstract KT empty_key();
	protected abstract KT kt_add_kct(KT key, KCT keypart);
}
public class StringTrie<VT> extends AbstTrie<String, Character, VT> {
	public int key_length(String key) {
		return key.length();
	}
	public Character kctAt(String key, int i) {
		return key.charAt(i);
	}
	public String empty_key() {
		return "";
	}
	public String kt_add_kct(String key, Character keypart) {
		return (key + keypart);
	}
	public static void main(String[] args) {
		var GPA = new StringTrie<Double>();
		String[] Roster = {"Alex", "Tyrone", "Alexi", "Alexander", "Alexandra", "Al", "Tyler"};
		for(String n : Roster) {
	    GPA.set(n, ((int) (Math.random() * 401)) / 100.0);
		}
		GPA.remove("Alexi");
		GPA.set("Alexander", 3.5);
		System.out.println("size: " + GPA.size());
		for(String n : Roster) {
	    System.out.println(n + " has a GPA of " + GPA.get(n));
		}
		GPA.set("Alexi", 1.0);
		GPA.stream("Alex", 2).forEach(System.out :: println);
		GPA.remove("Alexander");
		GPA.remove("Alexandra");
		System.out.println("size: " + GPA.size());
		System.out.println("load factor: " + GPA.load_factor());
		int cleaned = GPA.cleanup();
		System.out.println("cleaned: " + cleaned);
		System.out.println("load factor: " + GPA.load_factor());
	}
}