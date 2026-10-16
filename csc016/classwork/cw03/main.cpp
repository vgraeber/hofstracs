#include <iostream>
using namespace std;

int main() {
  string name = "James";
  int i = 0;
  while (name[i] != 0) {
    cout << "index i is: " << i << " and the value at i is: " << name[i] << " and the ascii code is: " << (int)name[i] << endl;
    i += 1;
  }
  cout << "index i is: " << i << " and the value at i is: " << name[i] << " and the ascii code is: " << (int)name[i] << endl;
  return 0;
}
