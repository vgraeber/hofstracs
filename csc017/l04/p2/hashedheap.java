import java.util.HashMap;
import java.util.Comparator;
import java.util.stream.Stream;

record KVPair<KT, VT>(KT key, VT val) {
  @Override
	public String toString() {
    return key + " : " + val;
  }
}

class HashedHeap<KT, VT extends Comparable<? super VT>> {
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
  public VT get(KT key) {
    if (keymap.get(key) == null) {
      return null;
    } else {
      return Entries[keymap.get(key)].val();
    }
  }
  private void push(KT key, VT val) {
    if ((key == null) || (val == null)) {
      throw new RuntimeException("null push");
    }
    Entries[size] = new KVPair(key, val);
    keymap.put(key, size);
    size++;
    resize();
    swapUp(size - 1);
  }
  public VT remove(KT key) {
    if ((keymap.get(key) != null) && (size > 1)) {
      int remInd = keymap.get(key);
      VT remVal = Entries[remInd].val();
      Entries[remInd] = Entries[size - 1];
      Entries[size - 1] = null;
      size--;
      keymap.remove(key);
      keymap.put(Entries[remInd].key(), remInd);
      int newRemInd = swapUp(remInd);
      if (remInd == newRemInd) {
        swapDown(remInd);
      }
      return remVal;
    } else if (size == 1) {
      VT remVal = Entries[0].val();
      Entries[0] = null;
      size--;
      keymap.remove(key);
      return remVal;
    } else {
      return null;
    }
  }
  public KVPair<KT, VT> pop() {
    if (size < 1) {
      return null;
    } else {
      KVPair<KT, VT> answer = Entries[0];
      remove(answer.key());
      return answer;
    }
  }
  public KVPair<KT, VT> peek() {
    if (size < 1) {
      return null;
    } else {
      return Entries[0];
    }
  }
  public VT set(KT key, VT val) {
    VT ans = get(key);
    if (ans == null) {
      push(key, val);
    } else {
      int ind = keymap.get(key);
      Entries[ind] = new KVPair(key, val);
      int newInd = swapUp(ind);
      if (ind == newInd) {
        swapDown(ind);
      }
    }
    return ans;
  }
  public Stream<KVPair<KT, VT>> priority_stream() {
    return Stream.generate( () -> pop() ).limit(size);
  }
  // Challenge: in addition to a a priority stream, you should implement
  // a non-consuming stream of keys, in no particular order:
  public Stream<KT> key_stream() { 
    // this is optional
    return null;
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

    GPA.priority_stream().forEach(System.out::println);
  }
}