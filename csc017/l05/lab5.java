import java.util.Optional;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.HashMap;

record pair(int x, int y) {
  @Override
  public String toString() {
    return ("(" + x + ", " + y + ")");
  }
}

class FixedHeap<T extends Comparable<? super T>> {
  int size = 0;
  Comparator<T> cmp = (a, b) -> a.compareTo(b);
  T[] Heap;
  public FixedHeap(int size, boolean maxheap) {
    if (!maxheap) {
      cmp = (a, b) -> b.compareTo(a);
    }
    Heap = (T[]) new T[size];
  }
  public int size() {
    return size;
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
    T temp = Heap[ind];
    Heap[ind] = Heap[swapInd];
    Heap[swapInd] = temp;
  }
  int swapUp(int ind) {
    if ((0 <= ind) && (ind < size)) {
      int parentInd = parent(ind);
      while ((ind > 0) && (cmp.compare(Heap[ind], Heap[parentInd]) > 0)) {
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
      if ((leftInd < size) && (cmp.compare(Heap[ind], Heap[leftInd]) < 0)) {
        swapind = leftInd;
      }
      if ((rightInd < size) && (cmp.compare(Heap[leftInd], Heap[rightInd]) < 0) &&(cmp.compare(Heap[ind], Heap[rightInd]) < 0)) {
        swapind = rightInd;
      }
      if (swapind > 0) {
        swap(ind, swapind);
      }
    }
    return ind;
  }
  public boolean push(T val) {
    if ((val == null) || (size == Heap.length)) {
      System.out.println("invalid push");
      return false;
    }
    Heap[size] = val;
    size++;
    swapUp(size - 1);
    return true;
  }
  public Optional<T> popAndPush(T val) {
    if ((val == null) || (size < 1)) {
      System.out.println("invalid popAndPush");
      return Optional.empty();
    } else {
      T answer = Heap[0];
      Heap[0] = val;
      swapDown(0);
      return Optional.of(answer);
    }
  }
  public Optional<T> peek() {
    if (size < 1) {
      return Optional.empty();
    } else {
      return Optional.of(Heap[0]);
    }
  }
}

public class lab5 {
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
  public static <T extends Comparable<? super T>> Optional<T> Kthsmallest(T[] arr, int n) {
    if ((arr == null) || (arr.length < n)) {
      return Optional.empty();
    }
    nHeap = FixedHeap(arr.legnth, true);
    for (int i = 0; i < n; i++) {
      nHeap.push(arr[i]);
    }
    
  }
  /* Problem 3 (harder):
    Find and print the median number in a running stream of numbers.
    If the number of numbers is even, the median is average of the
    two middle values. The naive solution inserts the numbers into a
    sorted queue, but is horribly inefficient ...
  */
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
  public static void main(String[] args) {
	  median(Stream.generate(() -> Math.random()*1000).limit(100));
	  System.out.println(findpair(new int[]{3,1,8,4,6}, 5));
	  System.out.println(findpair(new int[]{3,1,8,4,6}, 15));
	  System.out.println(Kthsmallest(new Integer[]{5,4,9,7,1,2,8}, 3));
  }
}
