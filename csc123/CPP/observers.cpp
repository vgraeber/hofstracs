/*
CSC123/252 Assignment: Finding and Correcting Another Memory Error 

This program, designed to appear more like a real-world program, contains a memory error (segmentation fault).  

1.  Pinpoint the error.
    Explain precisely which lines of code caused the error and what kind of error it is.
    This is the MAIN part of the assignment.  

2.  Correct the error.
    Once you understand what's the cause of the error, only few more lines of code is required to correct it.
    Your code must stay efficient (no cloning!).
    And you may NOT change main.

3.  After identifying and fixing the error, can you give a smaller example of the same type of error, but with just a few lines of code?

HINTS: 
  1.  Run the program several times.
  2.  There is an equivalent version of this program in Java:
      https://cs.hofstra.edu/~cscccl/csc123/observers_problem.java

WARNING: this assignment has nothing to do with stock investments, it's about correct memory usage.
  Do not give me some AI-generated garbage where the stocks and investors interact in a different way.  
  You must understand what's causing the error here and write code that addresses ONLY that error.
  Changing main will result in a grade of -10000.
*/

#include<iostream>
#include<vector>
#include<memory>
#include<unordered_map>
#include<cstdlib>
#include<ctime>                        
using namespace std;/* requires -std=c++17 */

/*
The Observer Design Pattern is an object oriented design that's proven to be effective in industry.
It requires all the elements of object-oriented programming including inheritance, dynamic memory allocation and dynamic typing.
There are two hierarchies of objects: observers and observed (subjects).
Each observed object contains a set of pointers to observers.
The observed objects must implement an "attach" and a "detach" function to add/remove observers.
It must also implement a "notify" function, which notifies all attached observers of some change in the state of the observed object.
Each observer can observe (attached to) multiple subjects.
When an observer is notified, it runs a function "update".
There are usually different types of observers, each with its own update function, and will react differently.

In our example, the observed objects are stocks and the observers are investors.
A struct (class) `stock` is defined.
Each stock has a symbol and a price history represented by a stack (vector) of floating point values: the top of the stack contains the most recent price.
When the price changes, a new price value is pushed on the stack and *all investors are notified*.
For investors, there is a superclass `investor` subclasses bullinvestor, bearinvestor and panickyinvestor, each with their own "update" function that react to notifications from stocks.
Each update function represents a different investment strategy.

An investor can invest in multiple stocks.
It keeps a "portfolio" of all stocks that it owns.
When its investment strategy calls for it, it may choose to detach itself from a stock (dump all shares), after which it will no longer receive any notifications/updates.
Thus there must be pointers to multiple stocks inside each investor object.

A stock object must also maintain pointers to multiple investors (in order to notify them).
Thus there may be multiple pointers pointing to an investor and multiple pointers pointing to a stock object.
These pointers are definitely not *unique* and they form cycles.
This calls for a combination of std::shared_ptr and std::weak_ptr.
In this implementation, the pointers from investors to stocks are shared pointers, while the pointers from stocks to investors are weak pointers.
This means that a stock is never deallocated while it still has investors.

The situation is a bit more compilicated when an investor (observer) object "quits".
The weak pointers from stocks to investors won't prevent the investor objects from being deallocated.
However, the weak pointer will still exist after the investor it points to is gone: it's now a nullptr.
We have to make sure that the investor is "detached" from the stock before it's deleted.
This is done by writing a *destructor* for investor objects.

Another point to note is that the "set of investors" maintained by stock objects are hashmaps, namely std::unordered_map, that maps the names of investors to weak pointers to investor objects.
Consult C++ documentation if you don't understand the code related to unordered_map.
The map allows O(1) average time insert, delete and lookup.
Similarly, the "portfolio" maintained by each investor is a hashmap from a symbol for a stock to a pair consisting of a shared pointer to a stock object and an integer representing the number of shares of the stock owned by the investor.

In this program I also use pairs.
An std::pair is created with std::make_pair(a, b).
a is accessed with .first and b with .second.
When we iterate over the portfolio map in an investor, we are iterating over items that are nested pairs of the form (string, (shared_ptr, int)), so, for example, item.second.first refers to the shared pointer to a stock.
*/

struct investor;// for forward reference (observer objects)
struct stock {// (observed objects)
  // functions that must be defined externally due to forward reference
  void attach(weak_ptr<investor>);// called from observer
  void detach(string);// called from observer (investor)
  void notify();// calls the update function of all observers (investors)

  // stock-related elements
  string symbol;
  vector<float> prices;// stack of closing prices, top price is latest
  unordered_map<string, weak_ptr<investor>> investors;
  // using hashmap for quick lookup in case need to remove

  stock(string s, float p) {// constructor takes symbol and initial price
    symbol = s;
    prices.push_back(p);
  }// constructor

  // functions pertaining to stock
  float current_price() {
    return prices[prices.size() - 1];
  }// current price

  // price change: + for increase, - for decrease, e.g. price_change(-0.5)
  void price_change(float dp) {
    float cp = current_price();
    float newprice = cp + dp;
    prices.push_back(newprice);
    notify();// notifies all investors of price change
  }// price_change  -- notifies

  // average change in price over past number of days
  float trend(int days) {
    if (days < 2 || prices.size() < 2) return 0.0;
    float sumdiff = 0.0;// sum of differences
    for(int i = prices.size() - 1; i > 0 && i > prices.size() - days; i--) {
      sumdiff += prices[i] - prices[i - 1];
    }
    return sumdiff / days;// nonexistent days will return zero
  }// trend
};// struct stock

// observer superclass
struct investor : std::enable_shared_from_this<investor> {
  string name;
  // maps stock symbols to pointer, number of shares
  unordered_map<string, pair<shared_ptr<stock>, int>> portfolio; 
  investor(string n) { name = n; }// constructor

  ~investor() {
    // need to detach from all observed subjects before being deleted.
    for(auto& item:portfolio) {
      item.second.first -> detach(name);// item is a nested pair (string, (shared_ptr, int))
    }// for each item in portfolio
  }// destructor (rare example of destructor paired with smart pointers)
  
  void invest(weak_ptr<stock> s, int shares) {
    shared_ptr<stock> slock = s.lock();//upgrade weak_ptr to shared_ptr
    slock -> attach(shared_from_this());// creates shared_ptr from this ptr
    portfolio[slock -> symbol] = make_pair(slock, shares);
  }// attach to observed stock
  
  void dump(stock& s) {// dump all shares of a stock, detaches as observer
    portfolio.erase(s.symbol);
    s.detach(name);
  }

  void buy(string ssym, int shares) {
    auto entry = portfolio[ssym];
    portfolio[ssym] = make_pair(entry.first, entry.second + shares);
  }
  void sell(string ssym, int shares) {
    buy(ssym, -shares);
  }  
	    
  virtual void update(stock& observed) = 0;//pure virtual (abstract) function
};// struct investor - observer superclass


// Implementation of observer pattern functions for stock:
void stock::attach(weak_ptr<investor> observer) {
  investors[observer.lock() -> name] = observer;
}

void stock::detach(string investor_name) {
  investors.erase(investor_name);//remove entry from hashmap
}

void stock::notify() {
  // for (auto& entry:investors) entry.second.lock() -> update(*this);//c++14
  //for(auto& [name, wptr] : investors) wptr.lock() -> update(*this);//c++17
  for(auto i = investors.begin(); i != investors.end();) {
    auto m = i++;
    m -> second.lock() -> update(*this);//c++17
  }
}

// Implmementation of specific investors (concrete observers)

struct bullinvestor: investor {// default public inheritance for structs
  bullinvestor(string n):investor(n) {}
  void update(stock& s) override {
    buy(s.symbol, 100);// buy more regardless! be bullish!
    cout << name << " is buying more " << s.symbol << endl;
  }// bull investment strategy
};// bullinvestor

struct bearinvestor: investor {
  bearinvestor(string n):investor(n) {}
  void update(stock& s) override {
    float diff = s.trend(3);
    if (diff > .5) buy(s.symbol, 10);
    else if (diff > 3.0) sell(s.symbol, 10);// sell before bubble bursts
    cout << name << " now owns " << portfolio[s.symbol].second << " shares of " << s.symbol << endl;
  }// bear investment strategy
};// bearinvestor

struct panickyinvestor: investor {
  panickyinvestor(string n):investor(n) {}
  void update(stock& s) override {
    float diff = s.trend(3);
    if (diff < -0.1) {
      dump(s);
      cout << name << " dumped all shares of " << s.symbol << endl;
    }
  }// panicky investment strategy
};// panickyinvestor


int main()
{ srand(time(nullptr));
  shared_ptr<stock> Stocks[] = {
    make_shared<stock>("INTC",28.5),   // intel
    make_shared<stock>("AAPL",141.48), // apple
    make_shared<stock>("MSFT",242.4),  // microsoft
    make_shared<stock>("TSLA",181.9),  // tesla
    make_shared<stock>("GOOGL", 96.16), // google
    make_shared<stock>("DANOY",10.38), //leading manufacturer of farm feed
  };

  shared_ptr<investor> bull = make_shared<bullinvestor>("big bull");
  shared_ptr<investor> bear = make_shared<bearinvestor>("little bear");
  shared_ptr<investor> bbear = make_shared<bearinvestor>("big bear");  
  shared_ptr<investor> panic = make_shared<panickyinvestor>("panicky");

  for(auto s:Stocks) bull->invest(s,100); // bull buys 100 shares of each
  for(int i=0;i<5;i++) bear->invest(Stocks[i],50); //bears buy 50 shares each
  for(int i=0;i<5;i++) bbear->invest(Stocks[i],100);
  panic->invest(Stocks[3],20);  panic->invest(Stocks[4],30);

  // simulate stock price changes
  for(auto& s:Stocks) s->price_change((rand()%400)/100.0 - 2.0); // +/- 2
  for(auto& s:Stocks) s->price_change((rand()%400)/100.0 - 2.0); // +/- 2
  for(auto& s:Stocks) s->price_change((rand()%400)/100.0 - 2.0); // +/- 2
  for(auto& s:Stocks) s->price_change((rand()%400)/100.0 - 2.0); // +/- 2  
  // everything else is triggered automatically

  return 0;
}//main
/*
1.  The error was a concurrent memory modification.
    When "notify()" was called, if the panickyinvestor called "dump()", it would erase the investor from the unordered_map "investors" that was currently being iterated through, causing a segmentation fault.
3.  unordered_map<int, int> m = {{1, 1}, {1, 2}, {1, 3}};
    for (auto i : m) 
      m.erase(i);
*/