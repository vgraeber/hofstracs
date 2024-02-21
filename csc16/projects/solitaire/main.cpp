#include <iostream>
#include <string>
#include <algorithm>
#include <random>
using namespace std;

struct Deck {
  string arr[52];
};

struct Header {
  string arr[7];
};

struct Tableau {
  string arr[20][7];
};

Deck carddeck() {
  string suits[4] = {"S", "H", "C", "D"};
  string rank[13] = {" A", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", " J", " Q", " K"};
  Deck cards;
  for (int s = 0; s < 4; s++) {
    for (int r = 0; r < 13; r++) {
      int cnum = s * 13 + r;
      string card = rank[r] + suits[s];
      cards.arr[cnum] = card;
    }
  }
  return cards;
}

Header solitaireheader() {
  Header header = {"***", "---", "   ", " 0S", " 0H", " 0C", " 0D"};
  return header;
}

Tableau cardtableau() {
  Tableau tableau;
  for (int r = 0; r < 20; r++) {
    for (int c = 0; c < 7; c++) {
      tableau.arr[r][c] = "   ";
    }
  }
  return tableau;
}

string fullname(string card) {
  string suit;
  char cardsuit = card[card.length() - 1];
  card.pop_back();
  (cardsuit == 'S') ? (suit = "Spades") : (cardsuit == 'H') ? (suit = "Hearts") : (cardsuit == 'C') ? (suit = "Clubs") : (suit = "Diamonds");
  string name = card + " of " + suit;
  return name;
}

void printheader(Header header) {
  for (int i = 0; i < 7; i++) {
    cout << header.arr[i] << " ";
  }
  cout << endl << endl;
}

void printtableau(Tableau tableau) {
  for (int r = 0; r < 20; r++) {
    for (int c = 0; c < 7; c++) {
      if (r == 19) {
        cout << tableau.arr[r][c] << " ";
      } else if (tableau.arr[r + 1][c] == "   ") {
        cout << tableau.arr[r][c] << " ";
      } else {
        cout << "***" << " ";
      }
    }
    cout << endl;
  }
}

int main() {
  Deck cards = carddeck();
  Header header = solitaireheader();
  Tableau tableau = cardtableau();
  random_device rd;
  mt19937 g(rd());
  shuffle(begin(cards.arr), end(cards.arr), g);
  int counter = 0;
  for (int r = 0; r < 7; r++) {
    for (int c = r; c < 7; c++) {
      tableau.arr[r][c] = cards.arr[counter];
      counter += 1;
    }
  }
  printheader(header);
  printtableau(tableau);
  return 0;
}
