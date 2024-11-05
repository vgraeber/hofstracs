## CSC 17 Lab on Binary Search Trees (Part I)

The first part of the your assignment on binary search trees, due next week, asks you to add a few additional procedures to the basic implementation that's provided.
This implementation is similar to the code we wrote in class, but it has been rearranged as follows:

   1. The program has been organized as a Java "package."
      Inside the folder [https://cs.hofstra.edu/~cscccl/csc17/avltree](https://cs.hofstra.edu/~cscccl/csc17/avltree) you will find a [Tree.java](https://cs.hofstra.edu/~cscccl/csc17/avltree/Tree.java) and a [BstSet.java](https://cs.hofstra.edu/~cscccl/csc17/avltree/BstSet.java).
      Both of these files begin with the heading
      ```
      package avltree;
      ```
      The name of the package must be the same as the name of the folder that contains it, just as the name of a `.java` file must be consistent with the name of the public class or interface that it contains.

   2. The Tree.java file contains just the Tree interface.
      The BstSet.java file contains the implementation of the `Nil` and `Node` classes, but they have been rearranged as *inner* classes of a wrapper class called `BstSet`.
      This arrangement offer several advantages.

      -  Instead of an O(n) `size()` function, we can maintain a `int size` variable within the wrapper class which is updated when we add a new node in the `insert` function of the `Nil` class.
         This allows us to write a O(1) `size()` function inside the wrapper class.
      -  We can define a single instance of the `Nil` class, `Empty` to be used by the entire tree.
      -  We can define a single `Comparator` object `cmp` inside the `BstSet` class.
         Without this wrapper we would have to place the comparator inside each instance of `Node`, or pass in the comparator as an
         extra argument to every function that requires it. 
      -  There is no need to define `Node<T>` and `Nil<T>`, just `Node` and `Nil` because they already come under the scope of `BstSet<T>`.
         However, the `Tree<T>` interface remains public and outside of the wrapper class.

      The wrapper class defines a single `root` initialized to `Empty`.
      It then defines shell procedures such as `insert`, `contains`, `map_inorder` that invoke the corresponding procedure on the root.
      (Some of the return types of these procedures for the wrapper are different, however).
      These procedures perform once-only tasks such as checking if an argument is null, so they don't have be done by the recursive procedures in the `Node` class.

### Your Assignment

   You are to add the following procedures to the program.
   For each procedure, you need to add it to the `Tree` interface and implement it in both the `Nil` and the `Node` class.

   1. `Optional<T> max();` This procedure should return the largest value in a subtree, if it exists.
      Given that we defined `Optional<T> min()`, this one should be straightforward, designed to give you a warmup and boost your confidence for harder problems to come.
      Go ahead and pretend you came up with the recursive solution all by yourself! 

   2. `Tree<T> clone();` This procedure should return a clone of the tree that it's called on.
      That is, produce a cloned tree with copies all the nodes.
      However, the clone of `Nil` should always be `Empty`.
      Think recursively.

   The following procedures are harder and I will discuss them further in class.
   You can do problems 6 and 7 before trying 3, 4 and 5.
    
   3. `Tree<T> successor(T x, Tree<T> ancestor);` This procedure should find the node containing the next larger value compared to x, if it exists.
      If x doesn't exist or x is the largest value, it should return `Empty`.
     
   4. `Tree<T> predecessor(T x, Tree<T> ancestor);` This should be symmetrical to `successor`.

   5. `boolean is_bst(... you determine arguments ... );` Given a tree, verify that it's a binary search tree.
      That is, it has the binary search tree property that, for every node, every value in left subtree is less than `item` and every value in the right subtree is greater than `item`.
      You will have to determine what, if any arguments will be required by this procedure.

   The following procedures should be placed inside the `Node` class only; do not add them to the interface.

   6. `void LL()` This procedure performs a "rotation" on a node that's assumed to have a non-empty subtree (left subtree is also a node):

      ```
           x                 y
         /   \             /   \
        y     R   --->   LL     x
       / \                     / \
      LL  LR                 LR   R
      ```

      Here, the x, and y are values (items) stored at the nodes and the capital letters represent subtrees (pointers left and right).
      Think of an LL rotation as a clockwise rotation centered on the vertex containing x.
      Very importantly, we are guaranteed to preserve the binary-search tree property after rotation.
      **Your procedure must run in O(1), constant time**
      Do not use any loops or make recursive calls inside the function.

      Here's one way to implement it, but it's not efficient:
      ```
      void LL() {
        Node lnode = (Node)left;
        T x = this.item;
        this.item = lnode.item;
        this.left = lnode.left;
        this.right = new Node(x, lnode.right, this.right);
      }
      ```
      This is not efficient because it uses new memory for the new Node created.
      It will also require a previous node to be garbage collected.
      It is possible to write this procedure that does not create any new nodes, and only use the existing nodes.
      You must rearrange the information that they contain and change what points to them.

   7. `void RR()` A "RR" rotation rotates in the opposite  direction (from the second diagram to the first above). 
      Implement this in the same way.
      
      ```
         x                 y
       /   \             /   \
      L     y   --->    x     RR
           / \         / \
          RL  RR      L   RL
      ```