#include <iostream>
#include <iomanip>
#include <vector>
using namespace std;

template <typename vectype>

void display(vector<vectype> &vec) {
  if ((typeid(vectype) == typeid(float)) || (typeid(vectype) == typeid(double))) {
    cout << fixed << setprecision(2);
  }
  for (int i = 0; i < (vec.size() - 1); i++) {
    cout << vec[i] << ", ";
  }
  cout << vec[vec.size() - 1] << endl;
}

int main() {
  vector<int> vec;
  for (int i = 0; i < 3; i++) {
    vec.push_back(i + 1);
  }
  display(vec);
  return 0;
}
