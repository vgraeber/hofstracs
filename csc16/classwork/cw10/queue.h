#ifndef QUEUE_H
#define QUEUE_H

#include <iostream>

template <typename qele>

class Queue {
  class Node {
    qele data;
    Node *next;
    public:
      void setnext(Node *link) {
        next = link;
      }
      qele getdata() {
        return data;
      }
      Node *getnext() {
        return next;
      }
      Node(qele value, Node *link = 0) {
        data = value;
        next = link;
      }
  };
  Node *frontptr;
  Node *endptr;
  bool isempty() {
    return (frontptr == 0);
  }
  void queueerr() {
    std::cerr << "Queue is empty" << std::endl;
  }
  public:
    void enqueue(qele data) {
      if (isempty()) {
        frontptr = new Node(data);
        endptr = frontptr;
      } else {
        Node *newptr = new Node(data);
        endptr -> setnext(newptr);
        endptr = newptr;
      }
    }
    void dequeue() {
      if (isempty()) {
        queueerr();
      } else {
        Node *remnode = frontptr;
        frontptr = frontptr -> getnext();
        delete remnode;
      }
    }
    qele front() {
      if (isempty()) {
        queueerr();
        return *(new qele);
      } else {
        return (frontptr -> getdata());
      }
    }
    void display() {
      Node *currptr;
      for (currptr = frontptr; currptr != endptr; currptr = (currptr -> getnext())) {
        std::cout << currptr -> getdata() << ", ";
      }
      std::cout << currptr -> getdata() << std::endl;
    }
    Queue() {
      frontptr = 0;
      endptr = frontptr;
    }
    ~Queue() {
      Node *currptr = frontptr;
      Node *nextptr;
      while (currptr != endptr) {
        nextptr = currptr -> getnext();
        delete currptr;
        currptr = nextptr;
      }
      delete currptr;
    }
};

#endif
