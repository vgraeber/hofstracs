import avltree.*;
import java.util.Scanner;

public class avltest {
  public static void main(String[] args) {
    AVLSet<Integer> binSearchTree = new AVLSet<Integer>();
    int n = 50;
    if (args.length > 0) {
      n = Integer.parseInt(args[1]);
    }
    var tree = new AVLSet<Integer>();
    while (n-- > 0) {
      tree.insert(n * 4);
    }
    tree.map_inorder(x -> System.out.print(x + " "));
    System.out.println();
    BstGraph W = new BstGraph(1024, 768);
    W.draw(tree);
    Scanner scin = new Scanner(System.in);
    String req = "";
    do {
      System.out.print("add n or del n or quit: ");
      req = scin.next();
      if (req.equals("add")) {
        tree.insert(scin.nextInt());
      } else if (req.equals("del")) {
        tree.remove(scin.nextInt());
      }
      W.draw(tree);
      W.display_string("size " + tree.size() + ", height " + tree.depth(), 10, 740);
    } while (req.equals("add") || req.equals("del"));
    W.close();
  }
}