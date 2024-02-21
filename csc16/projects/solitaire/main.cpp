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
  string rank[13] = {"A ", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", "J ", "Q ", "K "};
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
  Header header = {"***", "---", "   ", "---", "---", "---", "---"};
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

Deck shuffledeck(Deck cards) {
  random_device rd;
  mt19937 g(rd());
  shuffle(begin(cards.arr), end(cards.arr), g);
  return cards;
}

void printrules() {
  cout << "Welcome to vgraeber's version of Klondike Solitaire, v1.0!" << endl;
  cout << "This game is meant to be played in a command-line interface, a.k.a. a terminal, using keyboard input." << endl;
  cout << "If you are not playing this game with these, please switch now, as playing in a different environment is untested and may have bugs." << endl << endl;
  cout << "Note: For both space and display reasons, cards will be displayed in a condensed format. All cards will take up 3 spaces." << endl << "The following is a quick guide to what this shorthand means." << endl << "'***' means a card is face-down" << endl << "'---' means that spot has no cards" << endl << "' 6D', '10S', 'J H', and 'Q C' mean '6 of Diamonds', '10 of Spades', 'Jack of Hearts', and 'Queen of Clubs', respectively. These are examples of the cards." << endl << endl;
  cout << "How To Play:" << endl << "Type either in the name of the card you would like to move and where, or stock to flip a new card." << endl << "Keep going until you either win or can't go any further." << "You may type 'exit' at any time to quit the game." << endl << "If you wish to see these instructions again, just type 'print rules'." << endl << endl;
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

void printgame(Header header, Tableau tableau) {
  printheader(header);
  printtableau(tableau);
}

int main() {
  Deck cards = carddeck();
  Header header = solitaireheader();
  Tableau tableau = cardtableau();
  cards = shuffledeck(cards);
  int counter = 0;
  for (int r = 0; r < 7; r++) {
    for (int c = r; c < 7; c++) {
      tableau.arr[r][c] = cards.arr[counter];
      counter += 1;
    }
  }
  printrules();
  string userinput;
  do {
    printgame(header, tableau);
    getline(cin, userinput);
  } while (userinput != "exit");
  return 0;
}
