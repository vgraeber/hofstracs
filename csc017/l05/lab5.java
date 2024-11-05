import java.util.Optional;
import java.util.HashMap;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.ArrayList;

record pair(int x, int y) {
  @Override
  public String toString() {
    return ("(" + x + ", " + y + ")");
  }
}

class Heap<T extends Comparable<? super T>> {
  int size = 0;
  Comparator<T> cmp = (a, b) -> a.compareTo(b);
  T[] heapEntries;
  T[] makeArr(int arrSize) {
    return (T[]) new Comparable[arrSize];
  }
  public Heap(boolean maxheap) {
    if (!maxheap) {
      cmp = (a, b) -> b.compareTo(a);
    }
    heapEntries = makeArr(16);
  }
  public Heap(int startSize, boolean maxheap) {
    if (!maxheap) {
      cmp = (a, b) -> b.compareTo(a);
    }
    heapEntries = makeArr(startSize);
  }
  public boolean setCmp(Comparator<T> newCmp) {
    if (newCmp == null) {
      return false;
    }
    cmp = newCmp;
    return true;
  }
  public int size() {
    return size;
  }
  public T[] getArr() {
    return heapEntries;
  }
  public int resize(int percentage) {
    int newArrSize = (heapEntries.length * percentage) / 100;
    if ((newArrSize < size) || (percentage < 0)) {
      return heapEntries.length;
    }
    T[] newHeapEntries = makeArr(newArrSize);
    System.arraycopy(heapEntries, 0, newHeapEntries, 0, size);
    heapEntries = newHeapEntries;
    return newArrSize;
  }
  int left(int ind) {
    return ((2 * ind) + 1);
  }
  int right(int ind) {
    return ((2 * ind) + 2);
  }
  int parent(int ind) {
    return ((ind - 1) / 2);
  }
  void swap(int ind, int swapInd) {
    T temp = heapEntries[ind];
    heapEntries[ind] = heapEntries[swapInd];
    heapEntries[swapInd] = temp;
  }
  int swapUp(int ind) {
    if ((0 <= ind) && (ind < size)) {
      int parentInd = parent(ind);
      while ((ind > 0) && (cmp.compare(heapEntries[ind], heapEntries[parentInd]) > 0)) {
        swap(ind, parentInd);
        ind = parentInd;
        parentInd = parent(ind);
      }
    }
    return ind;
  }
  int swapDown(int ind) {
    int swapind = 0;
    while (swapind >= 0) {
      swapind = -1;
      int leftInd = left(ind);
      int rightInd = right(ind);
      if ((leftInd < size) && (cmp.compare(heapEntries[ind], heapEntries[leftInd]) < 0)) {
        swapind = leftInd;
      }
      if ((rightInd < size) && (cmp.compare(heapEntries[leftInd], heapEntries[rightInd]) < 0) &&(cmp.compare(heapEntries[ind], heapEntries[rightInd]) < 0)) {
        swapind = rightInd;
      }
      if (swapind > 0) {
        swap(ind, swapind);
      }
    }
    return ind;
  }
  public boolean push(T val) {
    if (val == null) {
      System.out.println("invalid push");
      return false;
    } else if (size == heapEntries.length) {
      resize(200);
    }
    heapEntries[size] = val;
    size++;
    swapUp(size - 1);
    return true;
  }
  public Optional<T> pop() {
    if (size < 1) {
      return Optional.empty();
    }
    T answer = heapEntries[0];
    heapEntries[0] = heapEntries[size - 1];
    heapEntries[size - 1] = null;
    size--;
    swapDown(0);
    return Optional.of(answer);
  }
  public Optional<T> popAndPush(T val) {
    if ((val == null) || (size < 1)) {
      System.out.println("invalid popAndPush");
      return Optional.empty();
    } else {
      T answer = heapEntries[0];
      heapEntries[0] = val;
      swapDown(0);
      return Optional.of(answer);
    }
  }
  public Optional<T> peek() {
    if (size < 1) {
      return Optional.empty();
    } else {
      return Optional.of(heapEntries[0]);
    }
  }
  public boolean inBounds(T val) {
    if ((val == null) || (size < 1)) {
      return false;
    }
    return (cmp.compare(heapEntries[0], val) < 0);
  }
}

public class lab5 {
  /* Problem 0:
    A permutation of length n can be represented by an array of length n that contains the number 0, ..., n-1.
    For example, {1, 0, 3, 2} is a permutation of length 4.
    {1, 4, 2, 2} is not a permutation of length 4.
    Write a function to determine if an array represents a permutation of its length.
    The naive solution is O(n*n).
    A better solution might use a hashset, but the best solution would not require any special data structure.
    Find a solution that's worst-case O(n).
  */
  /* Naive Solution:
  public static boolean is_permutation(int[] A) {
    if (A == null) {
      return false;
    }
    boolean answer = true;
    for (int k = 0; answer && k < A.length; k++) {
	    answer = false;
	    for(int i = 0; !answer && i < A.length; i++) {
		    if (k == A[i]) {
          answer = true;
        }
	    }
  	}
	  return answer;
  }
  */
  public static boolean is_permutation(int[] arr) {
    if (arr == null) {
      return false;
    }
    boolean[] permCheck = new boolean[arr.length];
    for (int i = 0; i < arr.length; i++) {
      if (!permCheck[arr[i]]) {
        permCheck[arr[i]] = true;
      }
    }
    for (int i = 0; i < permCheck.length; i++) {
      if (!permCheck[i]) {
        return false;
      }
    }
    return true;
  }
  /* Problem 1: 
    Given an array of Integers and an Integer M, determine if there are two distinct integers in the array that adds up to M.
    For example, if the array is {3, 1, 8, 4, 6} and M is 5, then the answer is yes, because 1 + 4 = 5.
    But if M = 2 the answer is no.
    The following solution works but is not efficient: it's O(n * n).
    Your task is to find a solution that takes O(n) time, at least on theoretical average.
    You may use up to O(n) extra memory (n refers to the length of the array).
    Your function must take the same type of arguments and return same type.
  */
  /* Naive Solution:
  public static Optional<pair> findpair(int[] A, int M) {
  	for(int i = 0; i < (A.length - 1); i++) {
	    for(int k = i + 1; k < A.length; k++) {
        if ((A[i] + A[k]) == M) {
          return Optional.of(new pair(i, k));
        }
	    }
    }
    return Optional.empty();
  }
  */
  public static Optional<pair> findpair(int[] arr, int n) {
  	HashMap<Integer, Integer> nums = new HashMap<Integer, Integer>(arr.length);
    for (int i = 0; i < arr.length; i++) {
      nums.put(arr[i], i);
    }
    for (int i = 0; i < arr.length; i++) {
      int oNum = n - arr[i];
      if (nums.get(oNum) != null) {
        return Optional.of(new pair(i, nums.get(oNum)));
      }
    }
    return Optional.empty();
  }
  /* Problem 2:
    Given an array of Comparable values, find the Kth smallest value.
    For example, for an array of Integers {5, 4, 9, 7, 1, 2, 8}, the 3rd smallest value is 4.
    The following solution uses the built-in Arrays::sort function, which implements a version of mergesort and takes worst-case O((n)log(n)) time.
    Your task is to find a solution that runs in O((n)log(K)) time.
    Since K < n, it will be a better solution.
  */
  /* Naive Solution:
  public static <T extends Comparable<? super T>> Optional<T> Kthsmallest(T[] A, int K) {
    if ((A == null) || (A.length < K)) {
      return Optional.empty();
    }
    Arrays.sort(A);
    return Optional.of(A[K - 1]);
  }
  */
  public static <T extends Comparable<? super T>> Optional<T> Kthsmallest(T[] arr, int k) {
    if ((arr == null) || (arr.length < k)) {
      return Optional.empty();
    }
    Heap kHeap = new Heap(arr.length, true);
    int counter = 0;
    while (kHeap.size() < k) {
      kHeap.push(arr[counter]);
      counter++;
    }
    for (int i = counter; i < arr.length; i++) {
      if (kHeap.inBounds(arr[i])) {
        kHeap.popAndPush(arr[i]);
      }
    }
    return kHeap.peek();
  }
  /* Problem 3 (harder):
    Find and print the median number in a running stream of numbers.
    If the number of numbers is even, the median is average of the two middle values.
    The naive solution inserts the numbers into a sorted queue, but is horribly inefficient...
    Hint: the better solution is to use two heaps, a maxheap and a minheap.
  */
  /* Naive Solution:
  public static void median(Stream<Double> numbers) {
    var sq = new ArrayList<Double>();
    numbers.forEach(n -> {
      int i = 0;
      double median = 0;
      while ((i < sq.size()) && (n > sq.get(i))) {
        i++;
      }
      sq.add(i, n);
      if ((sq.size() % 2) == 1) {
        median = sq.get(sq.size() / 2);
      } else {
        median = (sq.get(sq.size() / 2) + (sq.get((sq.size() / 2) - 1))) / 2;
      }
      System.out.println("running median: " + median);
    });
  }
  */
  public static double getVal(Optional<Double> wrapper) {
    if (wrapper.isPresent()) {
      return wrapper.get();
    } else {
      return 0;
    }
  }
  public static void median(Stream<Double> numbers) {
    Heap maxHeap = new Heap(true);
    Heap minHeap = new Heap(false);
    numbers.forEach(n -> {
      double median = 0;
      if (maxHeap.size() == 0) {
        maxHeap.push(n);
        median = getVal(maxHeap.peek());
      } else if (minHeap.size() == 0) {
        if (n < getVal(maxHeap.peek())) {
          double temp = getVal(maxHeap.popAndPush(n));
          minHeap.push(temp);
        } else {
          minHeap.push(n);
        }
        median = (getVal(maxHeap.peek()) + getVal(minHeap.peek())) / 2;
      } else {
        if (n < getVal(maxHeap.peek())) {
          if (maxHeap.size() > minHeap.size()) {
            double temp = getVal(maxHeap.popAndPush(n));
            minHeap.push(temp);
          } else {
            maxHeap.push(n);
          }
        } else if (n > getVal(minHeap.peek())) {
          if (minHeap.size() > maxHeap.size()) {
            double temp = getVal(minHeap.popAndPush(n));
            maxHeap.push(temp);
          } else {
            minHeap.push(n);
          }
        } else {
          maxHeap.push(n);
        }
        if (maxHeap.size() > minHeap.size()) {
          median = getVal(maxHeap.peek());
        } else if (minHeap.size() > maxHeap.size()) {
          median = getVal(minHeap.peek());
        } else {
          median = (getVal(maxHeap.peek()) + getVal(minHeap.peek())) / 2;
        }
      }
      System.out.println("running median: " + median);
    });
  }
  public static void main(String[] args) {
	  median(Stream.generate(() -> (Math.random() * 1000)).limit(100));
	  System.out.println(findpair(new int[]{3, 1, 8, 4, 6}, 5));
	  System.out.println(findpair(new int[]{3, 1, 8, 4, 6}, 15));
	  System.out.println(Kthsmallest(new Integer[]{5, 4, 9, 7, 1, 2, 8}, 3));
  }
}
