record KVPair<KT, VT>(KT key, VT val) {
  @Override
	public String toString() {
    return key + ":" + val;
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
    Entries = makearray(16);
  }
  public int size() {
    return size;
  }
  protected boolean morespace() {
    return ((size * 75) >= (Entries.length * 100));
  }
  protected void resize() {
    KVPair<KT, VT>[] newEntries = makearray(Entries.legnth * 2);
    System.arraycopy(Entries, 0, newEntries, 0, size);
    Entries = newEntries;
  }
  public VT get(KT key) {
    return Entries[keymap.get(key)].val();
  }
  protected int ind(KVPair<KT, VT> pair) {
    return (int)keymap.ind(pair.key());
  }
  protected KVPair<KT, VT> left(KVPair<KT, VT> pair) {
    return Entries[((2 * ind(pair)) + 1)];
  }
  protected KVPair<KT, VT> right(KVPair<KT, VT> pair) {
    return Entries[((2 * ind(pair)) + 2)];
  }
  protected KVPair<KT, VT> parent(KVPair<KT, VT> pair) {
    return Entries[((ind(pair) - 1) / 2)];
  }
  protected void set(KT key, Integer i) {
    keymap.put(key, i);
  }
  protected void swap(KVPair<KT, VT> pair, KVPair<KT, VT> swappair) {
    Entries[ind(pair)] = swappair;
    Entries[ind(swappair)] = pair;
    int ind = ind(swappair);
    set(swappair.key(), ind(pair));
    set(pair.key(), ind);
  }
  protected KVPair<KT, VT> swapUp(KVPair pair) {
    if ((0 <= ind(pair)) && (ind(pair) < size)) {
      KVPair<KT, VT> parentpair = parent(pair);
      while ((ind(pair) > 0) && (cmp.compare(pair, parentpair) > 0)) {
        swap(pair, parentpair);
        parentpair = parent(pair);
      }
    }
    return pair;
  }
  private void push(KT key, VT val) {
    if ((key == null) || (val == null)) {
      throw new RuntimeException("null push");
    }
    Entries[size] = new KVPair(key, val);
    keymap.put(key, size);
    size++;
    if (morespace()) {
      resize();
    }
    swapUp(Entries[size - 1]);
  }
  public KVPair<KT, VT> pop() {
    KVPair<KT, VT> answer = null; // returned if heap is empty
    /*
    This function should remove the key-value pair that has the highest priority, and return it.  This dummy always returns null. The keymap must always be update accordingly.
    */
    return answer;
  }// must run in O(log n) time
  public KVPair<KT, VT> peek() {
    //This function should return the highest priority pair without delete
    return null;
  }
  public VT remove(KT key) {
    /*
    Find the entry associated with the key and remove it by the following algorithm (similar to push):
    Take the last value (Entries[size-1]) and place it where the deleted value is. Then either swap up or swap down the value until it's in the right place.
    */
    return null;
  }// must run in O(log n) time
  public VT set(KT key, VT val) {
    /*
    Change the value associated with the key.  Return the previous value.  First locate the entry using the keymap. After changing the value, you will need to either swap it up or down the tree.
    If there is no such key in the structure, this function should behave like push.  Note that push is private and should not be called externally, lest it introduces conflicting keys into the structure.  Users must call set, which should guarantee that there are no duplicate keys.
    */
    return null;
  }// must run in amortized worst-case O(log n) time.
  // The following will create a consuming stream in order of
  // priority, after you've completed the implementation
  public Stream<KVPair<KT, VT>> priority_stream() {
    return Stream.generate( () -> pop() ).limit(size);
  }
  // Challenge: in addition to a a priority stream, you should implement
  // a non-consuming stream of keys, in no particular order:
  public Stream<KT> key_stream() { 
    // this is optional
    return null;
  }
  public static void this_can_be_your_main(String[] args) {
    // the default constructor should create a maxheap
    var GPA = new HashedHeap<String,Double>(true); 
    String[] names = {"Mary", "Larz", "Narx", "Parv", "Haten", "Isa", "Nev"};
    for(var n:names) GPA.set(n, ((int)(Math.random() * 401)) / 100.0);
    //  this should print from highest to lowest GPA
    //GPA.priority_stream().forEach(System.out::println);
    //  but GPA will be empty afterwards.
    GPA.set("Nev", 0.0);
    GPA.set("Mary", 4.0);
    GPA.remove("Isa");  

    System.out.println(GPA.get("Mary"));
    System.out.println(GPA.get("Narx"));	
    System.out.println(GPA.get("Isa"));	

    GPA.priority_stream().forEach(System.out::println);
    // should reflect new priorities ...
  }
}