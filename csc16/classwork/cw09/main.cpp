#include "stack.h"
#include <iostream>
#include <vector>
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
      return n2 % n1;
    default: 
      cerr << "unsupported operator" << endl;
      return -1;
  }
}

void handleopp(char opp, Stack &name) {
  int n1 = name.top();
  name.pop();
  int n2 = name.top();
  name.pop();
  int res = evalexpr(opp, n1, n2);
  name.push(res);
}

vector<int> getspacelocs(vector<string> infixuin) {
  vector<int> spacelocs;
  for (int i = 0; i < infixuin.size(); i++) {
    if (infixuin[i] == " ") {
      spacelocs.push_back(i);
    }
  }
  if (spacelocs.size() < 3) {
    spacelocs.insert(spacelocs.begin(), 0);
  }
  return spacelocs;
}

void vecprint(vector<string> infixuin) {
  for (int i = 0; i < infixuin.size(); i++) {
    cout << infixuin[i];
  }
  cout << endl;
}

int main() {
  Stack name;
  cout << "Enter a postfix expresssion: " << endl;
  string uin;
  getline(cin, uin, '\n');
  stringstream strstream(uin);
  vector<string> infixuin;
  while (!strstream.eof()) {
    int ch = strstream.peek();
    if (isspace(ch)) {
      strstream.ignore(1);
      if (infixuin[infixuin.size() - 1] != " ") {
        infixuin.push_back(" ");
      }
    } else if (isdigit(ch)) {
      int num;
      strstream >> num;
      name.push(num);
      infixuin.push_back(to_string(num));
    } else if (isgraph(ch)) {
      char opp;
      strstream >> opp;
      handleopp(opp, name);
      vector<int> locs = getspacelocs(infixuin);
      int spacelocs[3];
      for (int i = 2; i >= 0; i--) {
        spacelocs[i] = locs[locs.size() - (3 - i)];
      }
      if (infixuin[infixuin.size() - 1] == " ") {
        infixuin[infixuin.size() - 1] = ")";
      }
      string oppp = "";
      oppp.assign(1, opp);
      infixuin.insert(infixuin.begin() + spacelocs[1], oppp);
      infixuin.insert(infixuin.begin() + spacelocs[1], " ");
      infixuin.insert(infixuin.begin() + spacelocs[0], "(");
    }
  }
  cout << "This expression is equivalent to: ";
  vecprint(infixuin);
  cout << "The evaluated expresssion is: " << name.top() << endl;
  return 0;
}
