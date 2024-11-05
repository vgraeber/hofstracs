import avltree.*;

public class lab6 {
  public static void main(String[] args) {
    BstSet<Integer> t = new BstSet<Integer>();
    for(var x : new Integer[]{8, 4, 12, 15, 7, 3, 9, 6, 2}) {
      t.insert(x);
    }
    t.map_inorder(x -> System.out.print(x + " "));
    System.out.println("\nsize = " + t.size());
  }
}