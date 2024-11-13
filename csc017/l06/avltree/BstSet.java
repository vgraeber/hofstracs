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
  public BstSet<T> clone() {
    BstSet<T> newBST = new BstSet<T>(cmp);
    newBST.root = this.root.clone();
    newBST.size = this.size;
    return newBST;
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
  public boolean is_bst() {
    if (root.is_empty()) {
      return true;
    } else {
      return root.is_bst(min().get(), max().get());
    }
  }
  public Optional<T> min() {
    return root.min();
  }
  public Optional<T> max() {
    return root.max();
  }
  public Node predecessor(T x) {
    return (Node) root.predecessor(x, root);
  }
  public Node sucessor(T x) {
    return (Node) root.sucessor(x, root);
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
    public Tree<T> sucessor(T x, Tree<T> ancestor) {
      return Empty;
    }
    public Tree<T> predecessor(T x, Tree<T> ancestor) {
      return Empty;
    }
    public boolean is_bst(T min, T max) {
      return true;
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
    public Tree<T> predecessor(T x, Tree<T> ancestor) {
      int c = cmp.compare(x, ((Node) ancestor).item);
      if (c < 0) {
        return left.predecessor(x, this);
      } else if (c > 0) {
        return right.predecessor(x, ancestor);
      }
      return ancestor;
    }
    public Tree<T> sucessor(T x, Tree<T> ancestor) {
      int c = cmp.compare(x, ((Node) ancestor).item);
      if (c < 0) {
        return left.sucessor(x, ancestor);
      } else if (c > 0) {
        return right.sucessor(x, this);
      }
      return ancestor;
    }
    public boolean is_bst(T min, T max) {
      boolean lGood = true;
      boolean rGood = true;
      if (!left.is_empty()) {
        Node lNode = (Node) left;
        if ((cmp.compare(lNode.item, min) < 0) || (cmp.compare(lNode.item, item) > 0)) {
          lGood = false;
        } else {
          lGood = lNode.is_bst(min, max);
        }
      }
      if (!right.is_empty()) {
        Node rNode = (Node) right;
        if ((cmp.compare(rNode.item, item) < 0) || (cmp.compare(rNode.item, max) > 0)) {
          rGood = false;
        } else {
          rGood = rNode.is_bst(min, max);
        }
      }
      return (lGood && rGood);
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
      T temp = this.item;
      this.item = lNode.item;
      lNode.item = temp;
      this.left = lNode.left;
      lNode.left = lNode.right;
      lNode.right = this.right;
      this.right = lNode;
    }
    void RR() {
      Node rNode = (Node) right;
      T temp = this.item;
      this.item = rNode.item;
      rNode.item = temp;
      this.right = rNode.right;
      rNode.right = rNode.left;
      rNode.left = this.left;
      this.left = rNode;
    }
  }
}