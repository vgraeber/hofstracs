import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.Optional;

public class CQ<T> implements Iterable<T> {
  protected T[] Q;
  protected int front = 0;
  int size = 0;
  public CQ(int n) {
    if (n < 1) {
      n = 16;
    }
    Q = makearray(n);
  }
  @SuppressWarnings("unchecked")
  T[] makearray(int n) {
    return (T[]) new Object[n];   
  }
  protected int aindex(int vindex) {
    return ((front + vindex) % Q.length);
  }
  protected int right(int ai) {
    return ((ai + 1) % Q.length);
  }
  protected int left(int ai) {
    return ((ai + (Q.length - 1)) % Q.length);
  }
  public T get(int i) {
    return Q[aindex(i)];
  }
  public T set(int i, T x) {
    if ((i < 0) || (i >= size) || (x == null)) {
      System.err.println("CQ::set called on invalid args");
      return null;
    }
    int ai = aindex(i);
    T answer = Q[ai];
    Q[ai] = x;
    return answer;
  }
  public Optional<T> try_get(int i) {
    if ((i < 0) || (i >= size) || (Q[i] == null)) {
      return Optional.empty();
    } else {
      return Optional.of(Q[i]);
    }
  }
  protected void resize() {
    T[] Q2 = makearray(Q.length * 2);
    for(int i = 0; i < size; i++) {
      Q2[i] = Q[aindex(i)];
    }
    Q = Q2;
    front = 0;
  }
  public boolean enqueue(T x) {
    if (x == null) {
      return false;
    }
    if (size >= Q.length) {
      resize();
    }
    size++;
    Q[aindex(size - 1)] = x;
    return true;
  }
  public T dequeue() {
    if (size < 1) {
      return null;
    }
    T answer = Q[aindex(size - 1)];
    Q[aindex(size - 1)] = null;
    size--;
    return answer;
  }
  public boolean push(T x) {
    if (x == null) {
      return false;
    }
    if (size >= Q.length) {
      resize();
    }
    front = left(front);
    Q[front] = x;
    size++;
    return true;
  }
  public T pop() {
    if (size < 1) {
      return null;
    }
    T answer = Q[front];
    Q[front] = null;
    front = right(front);
    size--;
    return answer;
  }
  public int size() {
    return size;
  }
  public int capacity() {
    return Q.length;
  }
  public Iterator<T> iterator() {
    return new CQiterator<T>(this);
  }
  public Stream<T> stream(boolean parallel) {
    return StreamSupport.stream(this.spliterator(),parallel);
  }
  public static void main0(String[] args) {
    CQ<Integer> q = new CQ<Integer>(1);
    for(var x : new Integer[]{2,4,6,8,10}) {
      q.enqueue(x);
    }
    for(var x : new Integer[]{1,3,5,7,9}) {
      q.push(x);
    }
    /*
    while (q.size() > 1) {
      System.out.println(q.pop());
      System.out.println(q.dequeue());            
    }
    */        
    // what's wrong with this loop????
    /*
    for(var x : q) {
      q.push(x);
    }

    for(var x : q) {
      System.out.println(x);
    }
    */
    /*
    var A = new java.util.Vector<Integer>();
    A.enqueue(2);
    A.enqueue(3);
    for(var x : A) {
      A.enqueue(x);
    }
    */
    //q.set(6, (q.get(6) * 10) + 1);
    q.stream(true)
      .map(x -> (x * x))
      .filter(x -> ((x % 2) == 1))
      .forEach(System.out :: println);
    // and don't change anything (no side effects, ideally not even print)
    var allpositive = 
    q.stream(false)
      .allMatch(x -> (x > 0));
    System.out.println("all positive: " + allpositive);
    q.stream(false)
      .filter(x -> ((x % 3) == 0))
      .findAny()
      .ifPresent(x -> System.out.println("found " + x));
  }
}
class CQiterator<T> implements Iterator<T> {
  CQ<T> q;
  int i = 0;
  int size;
  public CQiterator(CQ<T> q) {
    this.q=q;
    size=q.size();
  }
  public boolean hasNext() {
    //return ((size == q.size()) && (i < q.size()));
    if (size != q.size()) {
      throw new java.util.ConcurrentModificationException();
    }
    return (i < q.size());
  }
  public T next() {
    return q.get(i++);
  }
}