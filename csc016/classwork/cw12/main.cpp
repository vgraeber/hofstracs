#include <iostream>
#include <string>
using namespace std;

template <typename param>

string lowercase(string uin) {
   for (int i = 0; i < uin.length(); i++) {
      uin[i] = (char)tolower(uin[i]);
   }
   return uin;
}

int checkorder(param p1, param p2, param p3, param p4) {
   string typecheck;
   if ((typeid(param) == typeid(float)) || (typeid(param) == typeid(double)) || (typeid(param) == typeid(int))) {
      typecheck = "number";
   } else if ((typeid(param) == typeid(string)) || (typeid(param) == typeid(char))) {
      typecheck = "letter";
   } else {
      return 0;
   }
   if (typecheck == "number") {
      if ((p1 < p2) && (p2 < p3) && (p3 < p4)) {
         return -1;
      } else if ((p1 > p2) && (p2 > p3) && (p3 > p4)) {
         return 1;
      } else {
         return 0;
      }
   } else if (typecheck == "letter") {
      //p1 = lowercase(p1);
      //p2 = lowercase(p2);
      //p3 = lowercase(p3);
      //p4 = lowercase(p4);
      if ((p1 < p2) && (p2 < p3) && (p3 < p4)) {
         return -1;
      } else if ((p1 > p2) && (p2 > p3) && (p3 > p4)) {
         return 1;
      } else {
         return 0;
      }
   } else {
      cout << "Something went wrong" << endl;
      return 0;
   }
}

int main() {
   string s1, s2, s3, s4;
   cin >> s1 >> s2 >> s3 >> s4;
   cout << "Order: " << checkorder(s1, s2, s3, s4) << endl;
   double d1, d2, d3, d4;
   cin >> d1 >> d2 >> d3 >> d4;
   cout << "Order: " << checkorder(d1, d2, d3, d4) << endl;
   return 0;
}
