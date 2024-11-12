package avltree;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Stack;
import java.util.Optional;
import java.util.function.*;

public class BstSet<T extends Comparable<? super T>> {
  Tree<T> Empty = new Nil();
  Tree<T> root = Empty;
  int size = 0;
  Comparator<T> cmp = (x, y) -> x.compareTo(y);
  public BstSet() {}
  public BstSet(Comparator<T> cmp) {
    if (cmp != null) {
      this.cmp = cmp;
    }
  }
  public int size() {
    return size;
  }
  public int depth() {
    return root.depth();
  }
  public boolean contains(T x) {
	  if (x == null) {
      return false;
    } else {
      return root.contains(x);
    }
  }
  public boolean insert(T x) {
    if (x == null) {
      return false;
    }
	  int prevSize = size;
    root = root.insert(x);
    return (size > prevSize);
  }
  public Optional<T> min() {
    return root.min();
  }
  public Optional<T> max() {
    return root.max();
  }
  public void map_inorder(Consumer<? super T> cf) {
	  if (cf != null) {
      root.map_inorder(cf);
    }
  }
  public void ifPresent(Consumer<? super T> cf) {
    root.ifPresent(cf);
  }
  public <U> U match(Function<? super T,? extends U> fn, Supplier<? extends U> fe) { 
	  return root.match(fn, fe);
  }
  class Nil implements Tree<T> {
    public boolean is_empty() {
      return true;
    }
    public int depth() {
      return 0;
    }
    public boolean contains(T x) {
      return false;
    }
    public Tree<T> clone() {
      return Empty;
    }
    public Tree<T> insert(T x) {
      size++;
      return new Node(x, Empty, Empty);
    }
    public Optional<T> min() {
      return Optional.empty();
    }
    public Optional<T> max() {
      return Optional.empty();
    }
    public void map_inorder(Consumer<? super T> cf) {}
    public void ifPresent(Consumer<? super T> cf) {}
    public <U> U match(Function<? super T, ? extends U> fn, Supplier<? extends U> fe) {
      return fe.get();
    }
  }
  class Node implements Tree<T> {
    T item;
    Tree<T> left;
    Tree<T> right;
    public Node(T i, Tree<T> lf, Tree<T> rt) {
	    item = i;
      left = lf;
      right = rt;
    }
    public boolean is_empty() {
      return false;
    }
    public int depth() {
      return 1 + Math.max(left.depth(), right.depth());
    }
    public boolean contains(T x) {
      int c = cmp.compare(x, item);
      return ((c == 0) || ((c < 0) && left.contains(x)) || ((c > 0) && right.contains(x)));
      /* for earthlings, the above line is equivalent to:
        if (c == 0) {
          return true;
        } else if (c < 0) {
          return left.contains(x);
        } else {
          return right.contains(x);
        }
      */
    }
    public Tree<T> clone() {
      return new Node(item, left.clone(), right.clone());
    }
    public Tree<T> insert(T x) {
	    int c = cmp.compare(x, item);
      if (c < 0) {
        left = left.insert(x);
      } else if (c > 0) {
        right = right.insert(x);
      }
      adjust();
      return this;
    }
    public Optional<T> min() {
	    if (left.is_empty()) {
        return Optional.of(this.item);
      } else {
        return left.min();
      }
    }
    public Optional<T> max() {
	    if (right.is_empty()) {
        return Optional.of(this.item);
      } else {
        return right.max();
      }
    }
    public void map_inorder(Consumer<? super T> cf) {
      left.map_inorder(cf);
      cf.accept(this.item);
      right.map_inorder(cf);
    }
    public void ifPresent(Consumer<? super T> cf) {
      cf.accept(this.item);
    }
    public <U> U match(Function<? super T,? extends U> fn, Supplier<? extends U> fe) {
	    return fn.apply(this.item);
    }
    public void add(T x) {
	    Node current = this;
      boolean stop = false;
      while (!stop) {
        int c = x.compareTo(current.item);
	      if (c < 0) {
          if (current.left.is_empty()) {
            current.left = new Node(x, Empty, Empty);
            stop = true;
		      } else {
            current = (Node) current.left;
          }
	      } else if (c > 0) {
		      if (current.right.is_empty()) {
            current.right = new Node(x, Empty, Empty);
            stop = true;
          } else {
            current = (Node) current.right;
          }
	      } else {
          stop = true;
        }
	    }
    }
    void adjust() {}
    void LL() {
      Node lNode = (Node) left;
      Node tempNode = this;
      tempNode.left = lNode.right;
      this.item = lNode.item;
      this.left = lNode.left;
      this.right = tempNode;
    }
    void RR() {
      Node rNode = (Node) right;
      Node tempNode = this;
      tempNode.right = rNode.left;
      this.item = rNode.item;
      this.left = tempNode;
      this.right = rNode.right;
    }
  }
}