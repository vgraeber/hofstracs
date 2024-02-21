#include <iostream>
#include <string>
using namespace std;

string* carddeck() {
  string suits[4] = {"S", "H", "C", "D"};
  string rank[13] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
  string* deck = new string[52];
  for (int s = 0; s < 4; s++) {
    for (int r = 0; r < 13; r++) {
      int dnum = s * 13 + r;
      string card = rank[r] + suits[s];
      deck[dnum] = card;
    }
  }
  return deck;
}

string fullname(string card) {
  string suit;
  char cardsuit = card[card.length() - 1];
  card.pop_back();
  (cardsuit == 'S') ? (suit = "Spades") : (cardsuit == 'H') ? (suit = "Hearts") : (cardsuit == 'C') ? (suit = "Clubs") : (suit = "Diamonds");
  string name = card + " of " + suit;
  return name;
}

int main() {
  string* deck = carddeck();
  return 0;
}
