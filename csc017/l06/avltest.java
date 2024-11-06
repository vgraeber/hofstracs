import avltree.*;

public class avltest {
  /*
  public static void main(String[] args) {
    BstSet<Integer> binSearchTree = new BstSet<Integer>();
    for(var x : new Integer[]{8, 4, 12, 15, 7, 3, 9, 6, 2}) {
      binSearchTree.insert(x);
    }
    binSearchTree.map_inorder(x -> System.out.print(x + " "));
    System.out.println("\nsize = " + binSearchTree.size());
    BstGraph window = new BstGraph(800, 600);
    window.draw(binSearchTree);
  }
  */
  public static void main(String[] args) {
    BstGraph W = new BstGraph(1024, 768);
    BstSet<Integer> tree = new BstSet<Integer>();
    for(int i = 0; i < 64; i++) {
      tree.insert((int) (Math.random() * 100));
    }
    W.draw(tree);
    try {
      Thread.sleep(5000);
    } catch(Exception e) {}
    /*
    for(int x = 1; x < 100; x += 2) {
      tree.remove(x);
    }// delete all odd numbers
    */
    W.draw(tree);
    // System.out.println(tree); // should be sorted
  }
}