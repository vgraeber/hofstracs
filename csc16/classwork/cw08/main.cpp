#include "linkedlist.h"
#include <iostream>
using namespace std;

int main() {
  LinkedList list;
  const int num = 10;
  for (int i = 0; i < num; i++) {
    list.append(i);
  }
  list.display();
  for (int i = 1; i < num; i++) {
    list.insert(i, (i * 2));
  }
  list.display();
  for (int i = 0; i < num; i++) {
    list.remove(i);
  }
  list.display();
  return 0;
}
