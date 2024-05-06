#ifndef STACK_H
#define STACK_H

#include <iostream>
#include <iomanip>

template <typename stackele = int>

class Stack {
  class Node {
    stackele data;
    Node *next;
    public:
      void setnext(Node *link) {
        next = link;
      }
      stackele getdata() {
        return data;
      }
      Node *getnext() {
        return next;
      }
      Node(stackele value, Node *link = 0) {
        data = value;
        next = link;
      }
  };
  Node *topptr;
  bool isempty() {
    return (topptr == 0);
  }
  void stackerr() {
    std::cerr << "Stack is empty" << std::endl;
  }
  public:
    void push(stackele data) {
      topptr = new Node(data, topptr);
    }
    void pop() {
      if (isempty()) {
        stackerr();
      } else {
        Node *remnode = topptr;
        topptr = topptr -> getnext();
        delete remnode;
      }
    }
    stackele top() {
      if (isempty()) {
        stackerr();
        return *(new stackele);
      } else {
        return (topptr -> getdata());
      }
    }
    void display() {
      Node *currptr;
      if ((typeid(stackele) == typeid(float)) || (typeid(stackele) == typeid(double))) {
        std::cout << std::fixed << std::setprecision(2);
      }
      for (currptr = topptr; currptr != 0; currptr = (currptr -> getnext())) {
        std::cout << currptr -> getdata();
      }
      std::cout << std::endl;
    }
    Stack &operator=(Stack &orig) {
      topptr = 0;
      if ((!orig.isempty()) && (this != &orig)) {
        this -> ~Stack();
        topptr = new Node(orig.top());
        Node *lastptr = topptr;
        Node *origptr = orig.topptr -> getnext();
        while (origptr != 0) {
          lastptr -> setnext(new Node(origptr -> getdata()));
          lastptr = lastptr -> getnext();
          origptr = origptr -> getnext();
        }
      }
      return *this;
    }
    Stack() {
      topptr = 0;
    }
    Stack(Stack &orig) {
      topptr = 0;
      if (!orig.isempty()) {
        topptr = new Node(orig.top());
        Node *lastptr = topptr;
        Node *origptr = orig.topptr -> getnext();
        while (origptr != 0) {
          lastptr -> setnext(new Node(origptr -> getdata()));
          lastptr = lastptr -> getnext();
          origptr = origptr -> getnext();
        }
      }
    }
    ~Stack() {
      Node *currptr = topptr;
      Node *nextptr;
      while (currptr != 0) {
        nextptr = currptr -> getnext();
        delete currptr;
        currptr = nextptr;
      }
    }
};

#endif
