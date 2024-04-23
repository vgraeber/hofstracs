#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include "link.h"
#include <iostream>
using namespace std;

class LinkedList {
    Link *first;
    bool isempty() {
      return (first == 0);
    }
    void poserr(int reason) {
      string reasons[3] = {"list does not exist", "less than 0", "greater than length of list"};
      cerr << "invalid pos: " << reasons[reason] << std::endl;
    }
    void startlist(dtype data) {
      Link *newlink = new Link(data, first);
      this -> first = newlink;
    }
  public:
    void insert(dtype data, int pos) {
      if (pos < 0) {
        poserr(1);
      } else if (isempty()) {
        startlist(data);
      } else {
        Link *temp = first;
        for (int i = 0; i < (pos - 1); i++) {
          temp = temp -> getnext();
          if (temp == 0) {
            poserr(2);
          }
        }
        Link *newlink = new Link(data, (temp -> getnext()));
        temp -> setnext(newlink);
      }
    }
    void append(dtype data) {
      if (isempty()) {
        startlist(data);
      } else {
        Link *temp = first;
        while ((temp -> getnext()) != 0) {
          temp = temp -> getnext();
        }
        Link *newlink = new Link(data, 0);
        temp -> setnext(newlink);
      }
    }
    void remove(int pos) {
      if (isempty()) {
        poserr(0);
      } else if (pos < 0) {
        poserr(1);
      } else if (pos == 0) {
        Link *temp = first;
        first = temp -> getnext();
        delete temp;
      } else {
        Link *temp = first;
        for (int i = 0; i < (pos - 1); i ++) {
          temp = temp -> getnext();
          if (temp == 0) {
            poserr(2);
          }
        }
        Link *remlink = temp -> getnext();
        if (remlink == 0) {
          poserr(2);
        } else {
          temp -> setnext(remlink -> getnext());
          delete remlink;
        }
      }
    }
    void display() {
      Link *temp = first;
      while (temp != 0) {
        std::cout << (temp -> getdata()) << ' ';
        temp = temp -> getnext();
      }
      std::cout << std::endl;
    }
    LinkedList() {
      first = 0;
    }
    ~LinkedList() {
      Link *temp = first;
      while ((temp -> getnext()) != 0) {
        Link *remlink = temp;
        temp = temp -> getnext();
        delete remlink;
      }
      delete temp;
      delete first;
      first = 0;
    }
};

#endif
