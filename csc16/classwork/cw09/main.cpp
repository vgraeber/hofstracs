#include "stack.h"
#include <iostream>
#include <sstream>
#include <cstdlib>
using namespace std;

int evalexpr(char opp, int n1, int n2) {
  switch(opp) {
    case '+':
      return n1 + n2;
    case '-':
      return n1 - n2;
    case '*':
      return n1 * n2;
    case '/':
      return n1 / n2;
    case '%':
      return n1 % n2;
    default:
      cerr << "unsupported operator" << endl;
      return -1;
  }
}

void handleopp(stringstream &strstream, Stack &name) {
  char opp;
  strstream >> opp;
  int n2 = name.top();
  name.pop();
  int n1 = name.top();
  name.pop();
  int res = evalexpr(opp, n1, n2);
  name.push(res);
}

int main() {
  Stack name;
  cout << "Enter a space-separated postfix expresssion: " << endl;
  string uin;
  getline(cin, uin, '\n');
  stringstream strstream(uin);
  while (!strstream.eof()) {
    int ch = strstream.peek();
    if (isspace(ch)) {
      strstream.ignore(1);
    } else if (isdigit(ch)) {
      int num;
      strstream >> num;
      name.push(num);
    } else if (isgraph(ch)) {
      handleopp(strstream, name);
    }
  }
  cout << "The evaluated expresssion is: " << name.top() << endl;
  return 0;
}
