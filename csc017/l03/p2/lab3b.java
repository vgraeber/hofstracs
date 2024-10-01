import java.util.Comparator;
import java.util.stream.*;
import java.util.Iterator;

interface OrderedQueue<T> {
  boolean is_sorted();
  int search(T x);
  int insert_sorted(T x);
  T remove_at(int i, boolean order);
  default void sort() {
    if (!is_sorted()) {
      System.err.println("OrderedQueue::sort is not implemented");
    }
  }
}

class OrderedCQ<T extends Comparable<? super T>> extends CQ<T> implements OrderedQueue<T> {
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
  public Iterator<T> iterator() {
    locked = true;
    return new ProtectedCQiterator<T>(this);
  }
  @Override
  public boolean push(T x) {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    if (x == null) {
      return false;
    }
    super.push(x);
    if (sorted && (size > 1) && (cmp.compare(Q[aindex(0)], Q[right(aindex(0))]) > 0)) {
      sorted = false;
    }
    return true;
  }
  @Override
  public T pop() {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    return super.pop();
  }
  @Override
  public boolean enqueue(T x) {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    if (x == null) {
      return false;
    }
    super.enqueue(x);
    if (sorted && (size > 1) && (cmp.compare(Q[aindex(size - 1)], Q[left(aindex(size - 1))]) < 0)) {
      sorted = false;
    }
    return true;
  }
  @Override
  public T dequeue() {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    return super.dequeue();
  }
  @Override
  public T set(int i, T x) {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    if ((i < 0) || (i >= size) || (x == null)) {
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
        if (cmp.compare(x, Q[aindex(mid)]) == 0) {
          answer = mid;
        } else if (cmp.compare(x, Q[aindex(mid)]) < 0) {
          max = mid;
        } else {
          min = mid + 1;
        }
      }
      return answer;
    } else {
      for (int i = 0; i < (size - 1); i++) {
        int actInd = aindex(i);
        if (Q[actInd] == x) {
          return i;
        }
      }
      return -1;
    }
  }
  public int insert_sorted(T x) {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    if (!is_sorted()) {
      return -1;
    }
    int min = 0;
    int max = size;
    while (min < max) {
      int mid = (min + max) / 2;
      if (cmp.compare(x, Q[aindex(mid)]) == 0) {
        max = mid;
        min = mid + 1;
      } else if (cmp.compare(x, Q[aindex(mid)]) < 0) {
        max = mid;
      } else {
        min = mid + 1;
      }
    }
    if (max < (size - min)) {
      push(Q[aindex(0)]);
      for (int i = 1; i < max; i++) {
        set(i, Q[aindex(i + 1)]);
      }
      set(max, x);
      return max;
    } else {
      enqueue(Q[aindex(size - 1)]);
      for (int i = size - 2; i > min; i--) {
        set(i, Q[aindex(i - 1)]);
      }
      set(min, x);
      return min;
    }
  }
  public T remove_at(int i, boolean order) {
    if (locked) {
      throw new RuntimeException("queue locked");
    }
    if ((i < 0) || (i >= size)) {
      System.err.println("CQ::remove called on invalid args");
      return null;
    }
    T answer = Q[aindex(i)];
    if (!order) {
      set(i, Q[aindex(0)]);
      pop();
    } else {
      for (int j = i; j < (size - 1); j++) {
        set(j, Q[aindex(j + 1)]);
      }
      dequeue();
    }
    return answer;
  }
  public int hoare(int min, int max) {
    T pivot = Q[aindex((min + max) / 2)];
    int i = min;
    int j = max;
    do {
      while (cmp.compare(Q[aindex(i)], pivot) < 0) {
        i += 1;
      }
      while (cmp.compare(Q[aindex(j)], pivot) > 0) {
        j -= 1;
      }
      T low = Q[aindex(i)];
      T high = Q[aindex(j)];
      if (i < j) {
        T temp = low;
        set(i, high);
        set(j, temp);
      }
    } while (i < j);
    return j;
  }
  public void qSort(int min, int max) {
    if (min < max) {
      int mid = hoare(min, max);
      qSort(min, mid);
      qSort(mid + 1, max);
    }
  }
  public void sort() {
    if (!sorted) {
      qSort(0, size - 1);
    }
  }
  public boolean sortCheck() {
    for (int i = 0; i < size - 1; i++) {
      if (cmp.compare(Q[aindex(i)], Q[aindex(i + 1)]) > 0) {
        return false;
      }
    }
    return true;
  }
  public T get(int i) {
    return Q[aindex(i)];
  }
  public static void main(String[] args) {
    OrderedCQ queueue = new OrderedCQ(8);
    queueue.push(666);
    queueue.push(888);
    queueue.enqueue(999);
    queueue.enqueue(333);
    queueue.push(7);
    queueue.push(9);
    queueue.enqueue(3);
    queueue.enqueue(2);
    queueue.push(4);
    queueue.push(444);
    queueue.sort();
    Iterator iter = queueue.iterator();
    while (iter.hasNext()) {
      System.out.printf("%d%s", iter.next(), ", ");
    }
    System.out.printf("sorted: %s%n", queueue.sortCheck());
  }
}

class ProtectedCQiterator<T> implements Iterator<T> {
  OrderedCQ<? super T> q;
  int i = 0;
  int size;
  public ProtectedCQiterator(OrderedCQ<? super T> q) {
    this.q = q;
    size = q.size();
  }
  public boolean hasNext() {
    if (!(i < q.size())) {
      q.locked = false;
    }
    return (i < q.size());
  }
  public T next() {
    return (T) q.get(i++);
  }
}