#include <iostream>
#include <string>
#include <algorithm>
using namespace std;

string* carddeck() {
  string suits[4] = {"S", "H", "C", "D"};
  string rank[13] = {" A", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", " J", " Q", " K"};
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
  string tableau[20][7];
  for (int r = 0; r < 20; r++) {
    fill_n(tableau[r], 7, "   ");
  }
  random_shuffle(&deck[0], &deck[52]);
  int counter = 0;
  for (int r = 0; r < 7; r++) {
    for (int c = r; c < 7; c++) {
      tableau[r][c] = deck[counter];
      counter += 1;
    }
  }
  for (int r = 0; r < 20; r++) {
    for (int c = 0; c < 7; c++) {
      if (r == 19) {
        cout << tableau[r][c] << " ";
      } else if (tableau[r + 1][c] == "   ") {
        cout << tableau[r][c] << " ";
      } else {
        cout << " **" << " ";
      }
    }
    cout << endl;
  }
  return 0;
}
