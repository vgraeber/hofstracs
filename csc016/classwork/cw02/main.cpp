#include <iostream>
#include <iomanip>
using namespace std;

int main() {
  float num =0.0;
  while (num <= 5.0) {
    cout << scientific << setprecision(15) << num << endl;
    num += .1;
  }
  return 0;
}
