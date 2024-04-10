#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include "link.h"
#include <iostream>
using namespace std;

class LinkedList {
    Link *first;
  public:
    bool isempty() {
      return (first == 0);
    }
    void prepend(dtype data) {
      Link *newlink = new Link(data, first);
      this -> first = newlink;
    }
    void poserr() {
      cerr << "invalid pos" << endl;
    }
    void insert(dtype data, int pos) {
      if (pos < 0) {
        poserr();
      } else if (isempty()) {
        prepend(data);
      } else {
        Link *temp = first;
        for (int i = 0; i < (pos - 1); i++) {
          temp = temp -> getnext();
          if (temp == 0) {
            poserr();
          }
        }
        Link *newlink = new Link(data, (temp -> getnext()));
        temp -> setnext(newlink);
      }
    }
    void append(dtype data) {
      if (isempty()) {
        prepend(data);
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
      if ((pos < 0) or isempty()) {
        poserr();
      } else if (pos == 0) {
        Link *temp = first;
        first = temp -> getnext();
        delete temp;
      } else {
        Link *temp = first;
        for (int i = 0; i < (pos - 1); i ++) {
          temp = temp -> getnext();
          if (temp == 0) {
            poserr();
          }
        }
        Link *remlink = temp -> getnext();
        temp -> setnext(remlink -> getnext());
        delete remlink;
      }
    }
    void display() {
      Link *temp = first;
      while (temp != 0) {
        cout << (temp -> getdata()) << ' ';
        temp = temp -> getnext();
      }
      cout << endl;
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
    }
};

#endif
