import java.util.HashMap;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.Optional;
import java.util.function.Function;

record KVPair<KT, VT>(KT key, VT val) {
  @Override
	public String toString() {
    return key + " : " + val;
  }
}

interface QuickHeap<KT, VT> {
  int size();
  Optional<KVPair<KT,VT>> pop();
  Optional<KVPair<KT,VT>> peek();
  Optional<VT> get(KT key);
  Optional<VT> remove(KT key);
  Optional<VT> find_n_modify(KT key, VT default_val, Function<? super VT, ? extends VT> modifier);
  default Optional<VT> set(KT key, VT val) {
    return this.find_n_modify(key, val, (x -> val));
  }
  default Stream<KVPair<KT,VT>> priority_stream() {
    return Stream.generate(() -> pop()).flatMap(option -> option.stream()).limit(size());
  }
}

class HashedHeap<KT, VT extends Comparable<? super VT>> implements QuickHeap<KT, VT> {
  KVPair<KT, VT>[] Entries;
  int size = 0;
  Comparator<KVPair<KT, VT>> cmp = (a,b) -> a.val().compareTo(b.val());
  HashMap<KT, Integer> keymap;
  @SuppressWarnings("unchecked")
  KVPair<KT, VT>[] makearray(int cap) {
    return (KVPair<KT, VT>[]) new KVPair[cap];	
  }
  public HashedHeap(boolean maxheap) {
    if (!maxheap) {
      cmp = (a,b) -> b.val().compareTo(a.val());
    }
    keymap = new HashMap<KT, Integer>();
    Entries = makearray(16);
  }
  public int size() {
    return size;
  }
  void resize() {
    if ((size * 75) >= (Entries.length * 100)) {
      KVPair<KT, VT>[] newEntries = makearray(Entries.length * 2);
      System.arraycopy(Entries, 0, newEntries, 0, size);
      Entries = newEntries;
    }
  }
  int left(int pair) {
    return ((2 * pair) + 1);
  }
  int right(int pair) {
    return ((2 * pair) + 2);
  }
  int parent(int pair) {
    return ((pair - 1) / 2);
  }
  void swap(int pair, int swapPair) {
    KVPair<KT, VT> temp = Entries[pair];
    Entries[pair] = Entries[swapPair];
    Entries[swapPair] = temp;
    keymap.put(Entries[pair].key(), pair);
    keymap.put(Entries[swapPair].key(), swapPair);
  }
  int swapUp(int pair) {
    if ((0 <= pair) && (pair < size)) {
      int parentPair = parent(pair);
      while ((pair > 0) && (cmp.compare(Entries[pair], Entries[parentPair]) > 0)) {
        swap(pair, parentPair);
        pair = parentPair;
        parentPair = parent(pair);
      }
    }
    return pair;
  }
  int swapDown(int pair) {
    int swapPair = 0;
    while (swapPair >= 0) {
      swapPair = -1;
      int leftPair = left(pair);
      int rightPair = right(pair);
      if ((leftPair < size) && (cmp.compare(Entries[pair], Entries[leftPair]) < 0)) {
        swapPair = leftPair;
      }
      if ((rightPair < size) && (cmp.compare(Entries[leftPair], Entries[rightPair]) < 0) &&(cmp.compare(Entries[pair], Entries[rightPair]) < 0)) {
        swapPair = rightPair;
      }
      if (swapPair > 0) {
        swap(pair, swapPair);
      }
    }
    return pair;
  }
  public Optional<VT> get(KT key) {
    if (keymap.get(key) == null) {
      return Optional.empty();
    } else {
      return Optional.of(Entries[keymap.get(key)].val());
    }
  }
  private void push(KT key, VT val) {
    if ((key == null) || (val == null)) {
      System.out.println("null push");
    } else {
      Entries[size] = new KVPair(key, val);
      keymap.put(key, size);
      size++;
      resize();
      swapUp(size - 1);
    }
  }
  public Optional<VT> remove(KT key) {
    Optional<VT> remVal = Optional.empty();
    if (keymap.get(key) != null) {
      int remInd = keymap.get(key);
      remVal = Optional.of(Entries[remInd].val());
      keymap.remove(key);
      size--;
      if (size > 0) {
        Entries[remInd] = Entries[size];
        Entries[size] = null;
        keymap.put(Entries[remInd].key(), remInd);
        int newRemInd = swapUp(remInd);
        if (remInd == newRemInd) {
          swapDown(remInd);
        }
      } else if (size == 0) {
        Entries[0] = null;
      }
    }
    return remVal;
  }
  public Optional<KVPair<KT, VT>> pop() {
    Optional<KVPair<KT, VT>> remVal = Optional.ofNullable(Entries[0]);
    if (remVal.isPresent()) {
      remove(Entries[0].key());
    }
    return remVal;
  }
  public Optional<KVPair<KT, VT>> peek() {
    return Optional.ofNullable(Entries[0]);
  }
  public Optional<VT> find_n_modify(KT key, VT defaultVal, Function<? super VT, ? extends VT> modifier) {
    Optional<VT> prevVal = get(key);
    if (prevVal.isPresent()) {
      int ind = keymap.get(key);
      Entries[ind] = new KVPair(key, modifier.apply(Entries[ind].val()));
      int newInd = swapUp(ind);
      if (ind == newInd) {
        swapDown(ind);
      }
    } else {
      push(key, defaultVal);
    }
    return prevVal;
  }
  /*
  public Optional<VT> set(KT key, VT val) {
    return this.find_n_modify(key, val, (x -> val));
  }
  */
  public Stream<KVPair<KT,VT>> priority_stream() {
    return Stream.generate(() -> pop()).flatMap(option -> option.stream()).limit(size);
  }
  public Stream<KT> key_stream() {
    return keymap.keySet().stream();
  }
  public static void main(String[] args) {
    var GPA = new HashedHeap<String,Double>(true); 
    String[] names = {"Mary", "Larz", "Narx", "Parv", "Haten", "Isa", "Nev"};
    for(var n:names) GPA.set(n, ((int)(Math.random() * 401)) / 100.0);
    //GPA.priority_stream().forEach(System.out::println);
    GPA.set("Nev", 0.0);
    GPA.set("Mary", 4.0);
    GPA.remove("Isa");  

    System.out.println(GPA.get("Mary"));
    System.out.println(GPA.get("Narx"));
    System.out.println(GPA.get("Isa"));
    System.out.println(GPA.get("Nev"));
    GPA.priority_stream().forEach(System.out::println);
  }
}