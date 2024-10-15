import java.util.HashMap;
import java.util.Comparator;
import java.util.stream.Stream;

/* CSC 17 Lab: Implementing Variations on Hash Tables and Heaps

======================================================================

For this assignment, you're going to apply existing implementations of
hashmaps to implement new kinds of data data structures. I suggest
you make use of java.util.HashMap so you won't be confused by the
implementation details.

java.util.HashMap.  You must define new data types that contains these
maps underneath.  I will give you a simple example:

Suppose I wanted to construct an "association set", also called
"hashset".  Unlike a hashmap, a hashset just consists of a set of
keys. It is a "set" because the keys are in no particular order and
each key is unique.  We can implement a hashset using a hashmap by
mapping keys to booleans: a key maps to true if it exists in the set,
and false (or null) if it doesn't exist in the set.  It is important
that we ENCAPSULATE this new data structure so that users don't have
to worry about how it works underneath.  That is, users should only
see the interface:
*/
interface FiniteSet<T> {// finite set of values of type T
  int size();// cardinality of set
  boolean insert(T x);//add x to set returns false if x already in the set
  boolean contains(T x);// determines if x is in the set
  boolean remove(T x);//removes x from set, returns false if nothing removed
}
/*
This interface can be called an "Abstract Data Type" or ADT.  We can
choose to implement it anyway we like.
*/
class AssocSet<T> implements FiniteSet<T> { 
  private HashMap<T, Boolean> Boolmap;
  public AssocSet() {
    Boolmap = new HashMap<T, Boolean>();
  }
  public int size() {
    return Boolmap.size();
  }
  public boolean insert(T x) {
    return Boolmap.put(x, true);
  }
  public boolean contains(T x) {
    return (Boolmap.get(x) != null);
  }
  public boolean remove(T x) {
    return Boolmap.remove(x);
  }
}

/*
Java has a java.util.HashSet class, but it doesn't have the following...
==              PART I              ==
A *bijective map* or "Bimap" is a hash map that associates a pair of
values, but one in which either value can be used as the "key" to
lookup the other.  For example, suppose we want to create an
association between letter grades and their numerical values:

Bimap<String,Double> Gradepoint = new Bimap<String,Double>(16);
Gradepoint.set("A-", 3.7);  // map between grades and numerical values
Gradepoint.set("B+", 3.3);

Optional<Double> gp = Gradepoint.get("A-"); // returns Optional with 3.7
Optional<String> gr = Gradepoint.reverse_get(3.7);// Optional with "A-"
Optional<String> gr = Gradepoint.reverse_get(3.6);// empty Optional

This structure assumes that each value uniquely identifies the pair.
That is, "A-" cannot be mapped to a value other than 3.7, AND vice
versa (but the association can change).  In other words, there needs to
be a one-to-one correspondence between keys and values.  If multiple
keys can map to the same value, you should not be using this data
structure. For example, if we are mapping student names to their GPAs then
it is not a bimap because two students can have the same GPA.

A bimap must be based on a "bijective" function between keys and values.

A bimap can be implemented with A PAIR OF ONE-DIRECTIONAL maps. 
In the case of the Gradepoint example, you need a map from Strings to
Doubles AND one from Doubles to Strings.  You also need to make sure
that the two maps are consistent.  If you change "A-" to map to 3.8
for example, in the other map you must also delete the other map's
entry for 3.7 and insert a key-value pair mapping 3.8 to "A-".  These two
maps should be used internally in a new datatype class that you will
define.

To implement this data structure correctly, which you might actually use
someday, you must always observe the following:
  
  THERE CANNOT BE CONFLICTING KEY-VALUE PAIRS IN ANY HASHMAP.

For a bimap, this means a key cannot be found in two places and a value
also must not be found in two places (see the example in the main below).
Your implementation MUST also preserve time_complexity characteristics
of the basic operations (each must take average-case O(1) time).

Your class must implement the following interface:
*/
interface Twowaymap<TA, TB> {//bijective map between types TA and TB
  void set(TA x, TB y);// insert or change pair, x,y must be non-null.
  TB get(TA x);// gets corresponding TB value given TA value x, returns null if no association is present
  TA reverse_get(TB x);// get TA value given TB value x
  TB removeKey(TA x);// remove pair associated with TA x as key, returns TB value if present
  TA removeVal(TB y);// remove pair associated with TB y as key, returns TA value if present
  int size();// number of pairs in map. (equal to number of keys)
}
/*
You can choose to implement this interface anyway you want.  Place it
in a new file.

Here's a test function that works with my program, and you can use it
to test yours.  This main assumes that your bimap class is called 'Bimap':

  public static void main(String[] args) {// bijective map between letter grades and grade point values
	  Bimap<String,Double> GP = new Bimap<String,Double>();
	  String[] Grades = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
	  Double[] Points = {4.0, 3.7, 3.3, 3.0, 2.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.0};
	  for(int i = 0; i < Grades.length; i++) {
      GP.set(Grades[i], Points[i]);
    }
	  for(String g : Grades) {
      System.out.println(GP.get(g));
    }
	  for(Double p : Points) {
      System.out.println(GP.reverse_get(p));	
    }
    GP.set("A-", 3.75);// CHANGE value of A- from 3.7 to 3.75
    GP.set("B+", 3.25);// change another key/value
    GP.removeVal(1.3);// remove the D+ grade
    System.out.println(GP.get("A-"));// should print 3.75
    System.out.println(GP.reverse_get(3.75));// should print A-
    System.out.println(GP.get("D+"));// should print null
    System.out.println(GP.reverse_get(1.3));// null
	  /// THIS ONE IS THE MOST IMPORTANT, EASY TO GET WRONG: ****
	  System.out.println("3.7: " + GP.reverse_get(3.7)); 
    // This MUST PRINT null
    // because you already changed A- to correspond to 3.75, there
    // should be no value associated with 3.7.
    // Also be sure the check this one:  ****
	  GP.set("A+", 4.0);// this will also erase value for "A". why?
	  System.out.println(GP.size());// should print 10
	  // Pay special attention to cases marked with **** above
  }
You can place this main in your public calss
public class Bimap<TA,TB> 
{ ...
}
*/



/*
==========================   PART 2   ==========================

Now for the main event: you have to implement a hybrid data structure
that combines the advantages of a priority heap with that of a hashmap.

The one glaring problem with binary heaps is that Search remains O(n),
whereas for a hash table, it's (average-case) O(1).  The theoretical
worst-case is O(n) but such cases are rarely encountered in practice.

We want to associate each value in a heap with a key that can be hashed
to look up the location of the value in the heap tree (its array index).
This will make search average-case O(1). We can then also write functions
to modify the priority of objects, or to remove objects, in (average case) 
O(log n) time.

Entries in the heap come in the form of "key-value pairs":
*/
record KVPair<KT, VT>(KT key, VT val) {
  @Override
	public String toString() { return key + ":" + val; }
}
/*
The keys of type KT are to be hashed, while the values of type VT are to be Comparable (see skeleton below).
The idea is to keep a hashmap, not from keys directly to the values but from keys to the *indices* of where in heap the values are located.  
The trick is to keep this information consistent when values are swapped up and down the tree.
Each time a swap operation is made, you must also change
the association between the keys and the indices of each pair of values.
Since the basic hashing operations are practically O(1), this is an acceptable overhead.

You will need to study my implementation of the Heap data structure and adapt the code accordingly.

You will complete the implementation of the following class, with dummy procedures currently implemented (please don't leave any dummies).
*/

class HashedHeap<KT, VT extends Comparable<? super VT>> {
  KVPair<KT, VT>[] Entries;// the heap tree (I call it "H").
  int size = 0;
  public int size() {
    return size;
  }
  Comparator<KVPair<KT, VT>> cmp = (a,b) -> a.val().compareTo(b.val());// comparator is set to behave for a maxheap by default.
  HashMap<KT, Integer> keymap;// maps keys to indices in Entries array
  @SuppressWarnings("unchecked")
  KVPair<KT, VT>[] makearray(int cap) {
    return (KVPair<KT, VT>[]) new KVPair[cap];	
  }
  // write reasonable constructor(s) that allow both max and min heaps:
  public HashedHeap(boolean maxheap) {
    // this is just a skeleton. You'll have to fill in the details
  }
  // write a resize function that double capacity when needed.
  // Complete the following, which are currently dummies:
  private void push(KT key, VT val) {
    if ((key == null) || (val == null)) {
      return;
    }
    /*
    This function should push a new KVPair<KT, VT>(key, val) into the heap, and record in the HashMap called keymap where in the Entries array it's located.  When this information changes for any entry due to swapping, the keymap must be updated.
    */
  }// must run in amortized, average-case O(1) time, worst-case O(log n)
  // This function is private: see the public version "set" below:
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
  public VT get(KT key) {
    // Find and return the value associated with the key, return null if not found.  This dummy can't find anything but you can.
    return null;
  }// must run in (average case) O(1) time.
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
  }//main
}//HashedHeap