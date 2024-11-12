package avltree;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

public interface Tree<T> {
  boolean is_empty();
  int depth();
  boolean contains(T x);
  Tree<T> insert(T x);
  default Tree<T> remove(T s) {
    return this;
  }
  void map_inorder(Consumer<? super T> cf);
  void ifPresent(Consumer<? super T> cf);
  <U> U match(Function<? super T, ? extends U> fn, Supplier<? extends U> fe);
  default void visit_preorder(NodeVisitor<T> nv) {}
  default Stream<T> stream() {
    return Stream.empty();
  }
  Optional<T> min();
  Optional<T> max();
  Tree<T> clone();
  //Tree<T> sucessor(T x, Tree<T> ancestor);
  //Tree<T> predecessor(T x, Tree<T> ancestor);
  //boolean is_bst(... you determine arguments ... );
}