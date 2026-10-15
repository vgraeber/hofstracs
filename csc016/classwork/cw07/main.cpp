#include <iostream>
using namespace std;

class testClass {
  private:
    string name;
    string func;
  public:
    testClass(string newname, string funcname) {
      name = newname;
      func = funcname;
      cout << "Default constructor called for " << name << " in " << func << endl;
    }
    ~testClass() {
      cout << "Destructor called for " << name << " in " << func << endl;
    }
};

void subFunc() {
  testClass test2("test2", "subFunc");
  cout << "In sub function" << endl;
  testClass test3("test3", "subFunc");
}

int main() {
  testClass test("test1", "main");
  cout << "In main function" << endl;
  subFunc();
  return 0;
}
