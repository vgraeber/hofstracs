#include <iostream>
using namespace std;

int main() {
  // Static Array
  int staticarr[10];
  staticarr[0] = 7;
  cout << staticarr[0] << endl;
  // Dynamic Array
  int size;
  cout << "Please enter an array size: ";
  cin >> size;
  int *dynarr;
  dynarr = new int [size];
  dynarr[0] = 7;
  cout << dynarr[0] << endl;
  delete [] dynarr;
  dynarr = new int [1000];
  delete [] dynarr;
}
