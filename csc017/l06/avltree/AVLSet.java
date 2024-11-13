package avltree;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Stack;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

public class AVLSet<T extends Comparable<? super T>> extends BstSet<T> implements Iterable<T> {
  public AVLSet() {
    super();
    Empty = new AVLNil();
    root = Empty;
  }
  public AVLSet(Comparator<T> cmp) {
    super(cmp);
    Empty = new AVLNil();
    root = Empty;
  }
  public boolean remove(T x) {
    if (x == null) {
      return false;
    }
    int prevSize = size;
    root = root.remove(x);
    return (size < prevSize);
  }
  public void visit_preorder(BiConsumer<Tree<T>, T> bc) {
    if (bc == null) {
      return;
    }
    var nv = new NodeVisitor<T>(Empty,bc);
    root.visit_preorder(nv);
  }
  public Stream<T> stream() {
    return root.stream();
  }
  public Iterator<T> iterator() {
    return root.stream().iterator();
  }
  public boolean search(T x) {
    if (x == null) {
      return false;
    }
    Tree<T> current = root;
    while (!current.is_empty()) {
	    var current_node = (AVLNode) current;
	    int c = cmp.compare(x, current_node.item);
	    if (c == 0) {
        return true;
      } else if (c < 0) {
        current = current_node.left;
      } else {
        current = current_node.right;
      }
	  }
	  return false;
  }
  class AVLNil extends Nil {
    @Override
    public Tree<T> insert(T x) {
      size++;
      System.out.println(x);
      return new AVLNode(x, Empty, Empty);
    }
    @Override
    public String toString() {
      return "";
    }
  }
  class AVLNode extends Node {
    int height;
    @Override
    public int depth() {
      return height;
    }
    int set_height() {
	    int lDepth = left.depth();
      int rDepth = right.depth();
      height = 1 + Math.max(lDepth, rDepth);
      return (rDepth - lDepth);
    }
    public AVLNode(T i, Tree<T> lf, Tree<T> rt) {
      super(i, lf, rt);
      set_height();
    }
    @Override
    public String toString() {
      return (item + "");
    }
    @Override
    public Tree<T> clone() {
      return new AVLNode(item, left.clone(), right.clone());
    }
    public void visit_preorder(NodeVisitor<T> nv) {
      nv.accept(item);
      left.visit_preorder(nv.with_parent(this));
      right.visit_preorder(nv.with_parent(this));
    }
    public Stream<T> stream() {
      return Stream.generate(() -> left.stream()).limit(1).map(ls -> Stream.concat(ls, Stream.of(this.item))).flatMap(ms -> Stream.concat(ms, right.stream()));
    }
    public Tree<T> remove(T x) {
      int c = x.compareTo(item);
      if (c < 0) {
        left = left.remove(x);
      } else if (c > 0) {
        right = right.remove(x);
      } else {
        size--;
        if (left.is_empty()) {
          return right;
        } else {
          left = ((AVLNode) left).delete_max(this);
        }
      }
      adjust();
      return this;
    }
    Tree<T> delete_max(Node to_modify) {
      /*
      switch (right) {    // requires recent jdk
        case AVLNode rn :
          right = rn.delete_max(to_modify);
          adjust();
          return this;
        case AVLNil _ :
          to_modify.item = this.item;
          return left;
        default :
          throw new Error("This should never happen!");
      }
      */
      if (right.is_empty()) {
        to_modify.item = this.item;
        return left;
      } else {
        right = ((AVLNode) right).delete_max(to_modify);
        adjust();
        return this;
      }
    }
    @Override
    void adjust() {
      set_height();
      int bal = right.depth() - left.depth();
      if (bal < -1) {
        AVLNode lNode = (AVLNode) left;
        if (lNode.left.depth() < lNode.right.depth()) {
          lNode.RR();
        }
        LL();
      } else if (bal > 1) {
        AVLNode rNode = (AVLNode) right;
        if (rNode.left.depth() > rNode.right.depth()) {
          rNode.LL();
        }
        RR();
      }
    }
    @Override
    void LL() {
      AVLNode lNode = (AVLNode) left;
      AVLNode tempNode = this;
      tempNode.left = lNode.right;
      tempNode.set_height();
      this.item = lNode.item;
      this.left = lNode.left;
      this.right = tempNode;
      this.set_height();
    }
    @Override
    void RR() {
      AVLNode rNode = (AVLNode) right;
      AVLNode tempNode = this;
      tempNode.right = rNode.left;
      tempNode.set_height();
      this.item = rNode.item;
      this.left = tempNode;
      this.right = rNode.right;
      this.set_height();
    }
  }
}
class NodeVisitor<T> implements Consumer<T> {
  Tree<T> parent;
  final BiConsumer<Tree<T>, T> visitor;
  public NodeVisitor(Tree<T> p, BiConsumer<Tree<T>, T> visitor) {
    parent = p;
    this.visitor = visitor;
  }
  public NodeVisitor<T> with_parent(Tree<T> p) {
    parent = p;
    return this;
  }
  public void accept(T item) {
    visitor.accept(parent,item);
  }
}