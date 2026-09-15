/*       CSC16/123/252 Assignment: Bubblesort    

This program compiles but crashes with a very bad memory error (rated R!)

The bubblesort procedure can sort an array of numbers, but can it sort
any type that defines the < operator?  The `roster` type contains a
pointer to a heap-allocated array, and < compares the sizes of these
arrays.  Can bubblesort sort an array of rosters?

1. (most important): Identify the cause of the error.  I'm not asking
   you what the program "should" look like, but to understand exactly
   what caused the error!  What kind of memory error (memory leak, 
   dangling pointer, etc) is it, and which line(s) caused it. Be as
   specific as possible (don't just say it's using raw pointers).

2. Modify the roster struct as follows:

   a. change the line  `string* students;`  to

      unique_ptr<string[]> students;

   b. change the line `students = new string[count];`  to
 
      students = make_unique<string[]>(count);

   c. remove the destructor

3. Did part 2 fix the problem?  If not, EXPLAIN WHY.  Then fix any
   new problems by modifying bubblesort.  Bubblesort must be able to
   sort an array of rosters as well as any type that defines the <
   operator.

       ***** You may not modify main in any way *****

   (except for temporary tracing couts)
*/

#include<iostream>
#include<memory>
using namespace std;

template<typename T>  // MY BUBBLESORT PROCEDURE CAN SORT ANYTHING!
void bubblesort(T A[], int length) {
  for(int i=0;i<length-1;i++) 
    for(int k=0;k<length-i-1;k++)
      {
        if (A[k+1] < A[k]) { //swap
          T temp = A[k];
          A[k] = A[k+1];
          A[k+1] = temp;
        }
      }
}//bubblesort

// but what about these?
struct roster { 
  string class_name;
  string* students;  // pointer to heap-allocated array of student names
  unsigned int count; // number of students;
  
  roster(string n, std::initializer_list<string> R) { //constructor
    class_name = n;
    count = R.size();
    students = new string[count];  // allocates array on heap
    int i =0;
    for(string n:R) students[i++] = n;
  }
  ~roster() { if (students) delete[] students; }  // destructor

  // rosters are ordered by the count of the number of students
  bool operator <(roster& other) {
    return count < other.count;
  }

  // print roster
  friend ostream& operator <<(ostream& out, roster& r) {
    int i;
    out << "roster for " << r.class_name << ": ";
    for(i=0;i+1<r.count;i++) out << r.students[i] << ", ";
    if (i<r.count) out << r.students[i]; // last student without trailing ,
    out << endl;
    return out;
  }
};  // modify this struct only as instructed

int main() {
  int A[] = {4,3,6,1};
  bubblesort(A,4);
  for(int x:A) cout << x << " ";  cout << endl;  // BUBBLESORT WORKS!

  roster classes[] = {
    roster("csc123",{"Bilbo","Frodo","Gandolf"}),
    roster("csc16",{"Nev Erstudy","Isa Taparty"})
  };

  bubblesort(classes,2);
  for(roster& r:classes) cout << r;
  return 0;
}// main

/* Expected output of corrected program:

1 3 4 6 
roster for csc16: Nev Erstudy, Isa Taparty
roster for csc123: Bilbo, Frodo, Gandolf
*/
