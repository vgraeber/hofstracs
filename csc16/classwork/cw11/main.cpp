#include <iostream>
#include <iomanip>
#include <vector>
using namespace std;

template <typename param>

void display(param &vec) {
  if ((typeid(param) == typeid(float)) || (typeid(param) == typeid(double))) {
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
