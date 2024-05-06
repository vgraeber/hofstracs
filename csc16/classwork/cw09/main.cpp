#include "stack.h"
#include <iostream>
#include <iomanip>
#include <sstream>
#include <cstdlib>
using namespace std;

float evalexpr(char opp, float n1, float n2) {
  switch(opp) {
    case '+':
      return (n1 + n2);
    case '-':
      return (n1 - n2);
    case '*':
      return (n1 * n2);
    case '/':
      return (n1 / n2);
    case '%':
      return (n1 - (n2 * (int)(n1 / n2)));
    default:
      cerr << "unsupported operator" << endl;
  }
}

void handleopp(stringstream &strstream, Stack<float> &postfix, Stack<string> &infix) {
  char opp;
  strstream >> opp;
  float n2 = postfix.top();
  postfix.pop();
  float n1 = postfix.top();
  postfix.pop();
  float res = evalexpr(opp, n1, n2);
  postfix.push(res);
  stringstream num1;
  num1 << fixed << setprecision(2) << n1;
  string expr = "(" + num1.str() + " " + opp + " ";
  if (infix.top() == "") {
    stringstream num2;
    num2 << fixed << setprecision(2) << n2;
    expr += num2.str();
  } else {
    expr += infix.top();
    infix.pop();
  }
  expr += ")";
  infix.push(expr);
}

int main() {
  Stack<float> postfix;
  Stack<string> infix;
  infix.push("");
  cout << "Enter a space-separated postfix expresssion: " << endl;
  string uin;
  getline(cin, uin, '\n');
  stringstream strstream(uin);
  cout << uin << endl;
  while (!strstream.eof()) {
    int ch = strstream.peek();
    if (isspace(ch)) {
      strstream.ignore(1);
    } else if (isdigit(ch) || (ch == '.')) {
      float num;
      strstream >> num;
      postfix.push(num);
    } else if (isgraph(ch)) {
      handleopp(strstream, postfix, infix);
    }
  }
  cout << "The expression in infix (rounded to 2 decimal places) is: ";
  infix.display();
  cout << "The evaluated expresssion is: " << postfix.top() << endl;
  return 0;
}
