import java.util.Comparator;
import java.util.stream.*;

interface OrderedQueue<T> {
  boolean is_sorted(); // determines if queue is sorted in increasing order
  int search(T x); // search for x, returns virtual index or -1 if not found
  int insert_sorted(T x); // insert new x into a sorted queue
  T remove_at(int i, boolean order);  // removes ith value (see below)
  default void sort() {
    if (!is_sorted()) {
      System.err.println("OrderedQueue::sort is not implemented");
    }
  } // (OPTIONAL) sorts queue if it's not sorted
}

class OrderedCQ<T extends Comparable<? super T>> extends CQ<T> implements OrderedQueue {
  protected boolean sorted = true;
  boolean locked = false;
  protected Comparator<T> cmp = (x,y) -> x.compareTo(y);
  public OrderedCQ(int arrSize) {
    if (arrSize < 1) {
      arrSize = 16;
    }
    Q = makearray(arrSize);
    front = size = 0;
  }
  @SuppressWarnings("unchecked")
  @Override
  T[] makearray(int arrSize) {
    return (T[]) new Comparable[arrSize];
  }
  @Override
  public boolean push(T x) {
    if ((x == null) || locked) {
      return false;
    }
    super.push(x);
    if (sorted && (size > 1) && (cmp.compare(Q[aindex(0)], Q[right(aindex(0))]) > 0)) {
      sorted = false;
    }
    return true;
  }
  @Override
  public boolean enqueue(T x) {
    if ((x == null) || locked) {
      return false;
    }
    super.enqueue(x);
    if (sorted && (size > 1) && (cmp.compare(Q[aindex(size - 1)], Q[left(aindex(size - 1))]) < 0)) {
      sorted = false;
    }
    return true;
  }
  @Override
  public T set(int i, T x) {
    if ((i < 0) || (i >= size) || (x == null) || locked) {
      System.err.println("CQ::set called on invalid args");
      return null;
    }
    T answer = super.set(i, x);
    if (sorted && (size > 1) && (cmp.compare(Q[aindex(i)], Q[left(aindex(i))]) < 0) && (cmp.compare(Q[aindex(i)], Q[right(aindex(i))]) > 0)) {
      sorted = false;
    }
    return answer;
  }
  public boolean is_sorted() {
    return sorted;
  }
  public int search(T x) {
    if (is_sorted()) {
      int min = 0;
      int max = size;
      int answer = -1;
      while ((min < max) && (answer < 0)) {
        int mid = (min + max) / 2;
        if (x == Q[aindex(mid)]) {
          answer = mid;
        } else if (x < Q[aindex(mid)]) {
          max = mid;
        } else {
          min = mid + 1;
        }
      }
      return answer;
    }
    for (int i = 0; i < (size - 1); i++) {
      int actInd = aindex(i);
      if (Q[actInd] == x) {
        return i;
      }
    }
    return -1;
  }
  public int insert_sorted(T x) {
    if (!is_sorted() || locked) {
      return -1;
    }
    int min = 0;
    int max = size;//if size is 8 and q looks like [0, 1, 2, 3, 4, 5, 6, 7] and you want to insert 5.5
    while (min < max) {
      int mid = (min + max) / 2;//mid1 is 4 mid2 is 6 mid3 is 5
      if (x == Q[aindex(mid)]) {
        max = mid;
        min = mid + 1;
      } else if (x < Q[aindex(mid)]) {
        max = mid;//max becomes 6
      } else {
        min = mid + 1;//min becomes 5 and then 6
      }
    }
    if (max < (size - min)) {
      Q.push(Q[aindex(0)]);
      for (int i = 1; i < max; i++) {
        Q.set(i, Q[aindex(i + 1)]);
      }
      Q.set(max, x);
    } else {
      Q.enqueue(Q[aindex(size - 1)]);//q looks like [0, 1, 2, 3, 4, 5, 6, 7, 7] and size is 9
      for (int i = size - 2; i > min; i--) {//i starts at 7 and ends before 6
        Q.set(i, Q[aindex(i - 1)]);//q looks like [0, 1, 2, 3, 4, 5, 6, 6, 7]
      }
      Q.set(min, x);//q looks like [0, 1, 2, 3, 4, 5, 5.5, 6, 7]
    }
  }
  public T remove_at(int i, boolean order) {
    if ((i < 0) || (i >= size) || locked) {
      System.err.println("CQ::remove called on invalid args");
      return null;
    }
    T answer = Q[aindex(i)];
    if (!order) {
      Q.set(i, Q[aindex(0)]);
      Q.pop();
    } else {
      for (int j = i; j < (size - 1); j++) {
        Q.set(j, Q[aindex(j + 1)]);
      }
      Q.dequeue();
    }
    return answer;
  }
}
/*
5. The Iterator implementation of the superclass CQ does not adequately 
   protect against concurrent modification.  One should not be able to
   modify the queue in any way while iterating over it.  Checking the
   size is not enough.  It's not enough to defend against using the set
   method, nor against something like:

    for(var x:queue) {
      queue.add(x);
	    queue.pop();
    }
 
   This won't change the size of the queue between iterations but it still
   distorts the queue and makes the iteration invalid.  You need to prevent
   this from happening.  The idea is to insert a boolean flag `lock` into the
   class (your subclass).  Then all methods that modify the queue must
   check this flag, and if it is locked, then an exception should be thrown.

6. public void sort(): (OPTIONAL)

  This function sorts the Queue (destructively, in place).  This
problem is optional.  However, if you do implement it you must use an
algorithm with average-case time complexity of at most O(n log n). You
need to adopt the algorithm to the circular queue setting.  Of course,
if the boolean sorted flag is already true, sort() should do nothing.  
If you choose not to do this option, the interface does contain a default
dummy implementation just so your program will still compile.

---
To test that your program is working correctly, you also need a function
(not in the interface) that brute-forces a check of if the queue is sorted
using a O(n) loop

Be sure to test your implementions with appropriate test code.
*/