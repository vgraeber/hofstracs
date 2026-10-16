#ifndef TREENODE_H
#define TREENODE_H

template<typename T>
class TreeNode
{
public:
  // inline implementation
  TreeNode(T data,TreeNode<T> *left, TreeNode<T> *right)
  {
    this->data  = data;
    this->left  = left;
    this->right = right;
  }

  // data
  T data;
  TreeNode<T> *left;
  TreeNode<T> *right;
};

/*
// non inline implementation:
template<typename T>
TreeNode<T>::TreeNode(T data,TreeNode<T> *left, TreeNode<T> *right)
{
    this->data  = data;
    this->left  = left;
    this->right = right;
}
*/

#endif //TREENODE_H

