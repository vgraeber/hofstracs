#include <iostream>
#include <string>
using namespace std;

class Time {
  protected: // protected means values visible in time class and in subclasses
    int minutes;
    int seconds;
  public:
    Time(int m, int s) {
      int extra_minutes = s / 60;
      seconds = s % 60;
      minutes = m + extra_minutes;
    } //constructor
    Time() { // alternate constructor (also allowed in java)
      minutes = seconds = 0;
    } //alternate, default constructor.

    virtual int total_seconds() { return minutes * 60 + seconds; }

    // convert Time object to printable string form:
    // std::to_string is available in C++ 2011 or later

    virtual string toString() {
      return to_string(minutes) + " min " + to_string(seconds) + " sec";
    } // toString

    // increment this Time object by one second destructively (changes existing
    // object)
    virtual void tick() {
      seconds++;
      minutes += seconds / 60;
      seconds = seconds % 60;
    } //tick

  // compare this Time value with other Time value, return 0 if times are
  // equal, negative value if this time is less than other time and
  // positive value if this time is greater than other time.  For example,
  // 1 min 30 seconds is less than 2 minutes and 10 seconds
  virtual int compareTo(Time* other) {
    int total1 = this -> total_seconds();
    int total2 = other -> total_seconds();
    return total1 - total2;
  } // compareTo

  // add a pair of Time values non-destructively (produces a new object,
  // does not change any existing object).  Take pointer to other Time object
  // and returns pointer to newly heap-allocated Time object
  virtual Time* add(Time* other) {
    int other_seconds = other -> total_seconds();
    int s = seconds + other_seconds;
    int m = minutes + s / 60;
    s = s % 60;
    return new Time(m, s);
  } //add
}; //Time class

// overload "<<" on cout so printing will auto-invoke toString method
ostream& operator <<(ostream& outstream, Time* t) {
  outstream << t -> toString();
  return outstream;
} // this is not necessary in Java because toString overrides Object.toString


void main2(); // method defined after main (see Part 2)

int main(int argc, char* argv[]) {
  Time* t1 = new Time(2, 15);
  t1 -> tick();
  Time* t2 = new Time(1, 44);
  Time* t3 = t1 -> add(t2);
  cout << "t1: " << t1 << endl;
  cout << "t2: " << t2 << endl;
  cout << "sum Time: " << t3 << endl;
  cout << "compare t1 and t2: " << t1 -> compareTo(t2) << endl;
  cout << "compare t1 with equivalent value: " << t1 -> compareTo(new Time(0, 136)) << endl;
  main2();  // tests Part2
  return 0;
} //main

/*
PART 2.  Modify the Java program by adding an "hour" value to the Time class
and change all methods accordingly, and append main to demonstrate. This is
option 1 for Part 2.

Part 2 Alternative (optional 2).  Implement the "hour" modification using
*inheritance*.  That is, do not change any existing code.  Rather, define
a subclass of Time that extends Time and override some methods.

  class HTime extends Time { .. }

In C++ this can be done with:
*/

class HTime : public Time { // public inheritance is default in Java
  protected:
    int hours;
  public:
    HTime(int h, int m, int s):Time(m, s) {
      int extra_hours = minutes / 60;
      minutes = minutes % 60;
      hours = h + extra_hours;
    } // subclass constructor.
    // in Java, to call the superclass constructor, call 'super(m,s);' inside
    // the body of the constructor.

    int total_seconds() override {
      return hours * 3600 + minutes * 60 + seconds;
    } // new version of total_seconds

    string toString() override {
      return to_string(hours) + " hrs " + Time::toString();
    } // toString
    // in Java, to call the superclass version of method, call super.toString()

    void tick() override {
      this -> Time::tick();
      hours += minutes / 60;
      minutes = minutes % 60;
    } //tick

    // note the return type is declared Time*, NOT HTime*
    Time* add(Time* other) override {
      int other_seconds = other -> total_seconds();
      int s = seconds + other_seconds;
      int m = minutes + s / 60;
      int h = hours + m / 60;
      s = s % 60;
      m = m % 60;
      return new HTime(h, m, s);
    } //sum
};// HTime subclass of Time

void main2() {
  Time* t1 = new Time(3,50);
  Time* t2 = new HTime(1,2,30);
  cout << t1 -> add(t2) << endl;
  Time* times[4];
  times[0] = new Time(2, 30);
  times[1] = new HTime(1, 30, 25);
  times[2] = new Time(5, 45);
  times[3] = new HTime(2, 0, 0);
  Time* sum = new HTime(0, 0, 0);
  for(auto t:times) sum = sum -> add(t);
  cout << "total time: " << sum << endl;
  cout << "can still compare HTimes: " << sum -> compareTo(t2) << endl;
  cout << (new HTime(1, 0, 0)) -> compareTo(new Time(60, 0)) << endl;
  delete t1;
  delete t2;  // manual memory deallocation with raw pointers
  for (int i = 0;i < 4;i++) delete times[i];
  // no delete[] times because the array's allocated on stack
} //main2
