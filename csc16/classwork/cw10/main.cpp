#include "queue.h"
#include <iostream>
using namespace std;

int main() {
  Queue<int> name;
  cout << "Testing the queue" << endl;
  cout << "Enqueueing: " << endl;
  for (int i = 0; i < 10; i++) {
    name.enqueue(i);
  }
  name.display();
  cout << "Dequeueing: " << endl;
  for (int i = 0; i < 5; i++) {
    name.dequeue();
  }
  name.display();
  cout << "Requeueing: " << endl;
  for (int i = 0; i < 5; i++) {
    name.enqueue(i);
  }
  name.display();
  return 0;
}
