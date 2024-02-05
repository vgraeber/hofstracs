#include <iostream>
#include <algorithm>
using namespace std;

string toBin(int origNum) {
  string binN = "";
  while (origNum != 0) {
    binN += to_string(origNum % 2);
    origNum = origNum / 2;
  }
  reverse(binN.begin(), binN.end());
  binN.insert(binN.begin(), '0');
  return binN;
}

string toTwosComp(string binN) {
  for (int i = 0; i < binN.length(); i++) {
    if (binN[i] == '1') {
      binN[i] = '0';
    } else {
      binN[i] = '1';
    }
  }
  bool added = 0;
  int i = binN.length() - 1;
  while (not added) {
    if (binN[i] == '1') {
      binN[i] = '0';
      i -= 1;
    } else {
      binN[i] = '1';
      added = 1;
    }
  }
  return binN;
}

int main() {
  int origNum;
  cin >> origNum;
  string binN = toBin(abs(origNum));
  if (origNum < 0) {
    binN = toTwosComp(binN);
  }
  cout << "Original Number: " << origNum << endl;
  cout << "Original Number in Binary: " << binN << endl;
  return 0;
}
