#ifndef BINARYTREE_H
#define BINARYTREE_H

#include "TreeNode.h"

template<typename T>
class BinaryTree
{
public:
  BinaryTree();
  void insert(T data);
  void display(TreeNode<T> *root);
  void display();
private:
  TreeNode<T> *root;
};

template<typename T>
BinaryTree<T>::BinaryTree()
{
  this->root = 0;
}

template<typename T>
void BinaryTree<T>::insert(T data)
{
  // if tree is empty
  if(root==0)
  {
    this->root = new TreeNode<T>(data,0,0);
    //this->root->data  = data;
    //this->root->left  = 0;
    //this->root->right = 0;
    return;
  }
  TreeNode<T> *temp = root;
  TreeNode<T> *prev;
  // find location to insert element
  while(temp!=0)
  {
    // save previous
    prev = temp;
    // if data is smaller
    if(data<temp->data)
      temp = temp->left;
    // if data is bigger
    else
    if(data>temp->data)
      temp = temp->right;
    else
    {
       cerr<<"element exists"<<endl;
       return;
    }
  }
  // create a new node
  TreeNode<T> *newNode =  new TreeNode<T>(data,0,0);
  //newNode->data  = data;
  //newNode->left  = 0;
  //newNode->right = 0;
  // check where to insert the new element
  if(data<prev->data)
    prev->left = newNode;
  else
    prev->right = newNode;

}

template<typename T>
void BinaryTree<T>::display(TreeNode<T> *root)
{
   if(root==0)
     return;
   cout<<root->data<<endl;
   cout<<"L"<<endl;
   display(root->left);
   cout<<"R"<<endl;
   display(root->right);
}

template<typename T>
void BinaryTree<T>::display()
{
   display(this->root);
   cout<<endl;
}

/* Question: how would you implement the destructor to free memory ????
BinaryTree<T>::~BinaryTree()
{
   // traverse tree and delete elements
}
*/


#endif //BINARYTREE_H

