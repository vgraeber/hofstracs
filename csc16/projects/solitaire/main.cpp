#include <iostream>
#include <string>
#include <algorithm>
#include <random>
using namespace std;

struct Deck {
  static const int numcards = 52;
  static const int numsuits = 4;
  static const int numranks = 13;
  string suits[numsuits] = {"S", "H", "C", "D"};
  string ranks[numranks] = {"A ", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", "J ", "Q ", "K "};
  string arr[numcards];
};

struct Header {
  static const int numcols = 7;
  static const int numsuits = 4;
  string newcards[3] = {"***", "---", "   "};
  string foundation[numsuits] = {"---", "---", "---", "---"};
  string colnums[numcols] = {" C1", " C2", " C3", " C4", " C5", " C6", " C7"};
  string div = "~~~~~~~~~~~~~~~~~~~~~~~~~~~";
};

struct Tableau {
  static const int numrows = 20;
  static const int numcols = 7;
  string arr[numrows][numcols];
};

Deck carddeck() {
  Deck cards;
  for (int s = 0; s < cards.numsuits; s++) {
    for (int r = 0; r < cards.numranks; r++) {
      int cnum = s * 13 + r;
      string card = cards.ranks[r] + cards.suits[s];
      cards.arr[cnum] = card;
    }
  }
  return cards;
}

Tableau cardtableau() {
  Tableau tableau;
  for (int r = 0; r < tableau.numrows; r++) {
    for (int c = 0; c < tableau.numcols; c++) {
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

string printrules() {
  cout << "Welcome to vgraeber's version of Klondike Solitaire, v1.0!" << endl;
  cout << "This game is meant to be played in a command-line interface, a.k.a. a terminal, using keyboard input." << endl;
  cout << "If you are not playing this game with these, please switch now, as playing in a different environment is untested and may have bugs." << endl << endl;
  cout << "Note: For both space and display reasons, cards will be displayed in a condensed format. All cards will take up 3 spaces." << endl << "The following is a quick guide to what this shorthand means." << endl << "'***' means a card is face-down" << endl << "'---' means that spot has no cards" << endl << "' 6D', '10S', 'J H', and 'Q C' mean '6 of Diamonds', '10 of Spades', 'Jack of Hearts', and 'Queen of Clubs', respectively. All of the playing cards follow this format." << endl << endl;
  cout << "How To Play:" << endl << "Type in the name of the card you would like to move, and if there are multiple options for its destination, they will pop up, 'stock' to flip a new card, or 'waste' to move a card from the waste to the tableau." << endl << "Keep going until you either win or can't go any further." << endl << "You may type 'exit' at any time to quit the game." << endl << "If you wish to see these instructions again, just type 'print rules'." << endl;
  cout << "Type 'continue' to show that you understand the rules and wish to continue." << endl;
  string uin;
  do {
    getline(cin, uin);
  } while ((uin != "continue") and (uin != "exit"));
  cout << endl;
  return uin;
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
  cout << endl << endl;
  for (int i = 0; i < 3; i++) {
    cout << header.newcards[i] << " ";
  }
  for (int i = 0; i < header.numsuits; i++) {
    cout << header.foundation[i] << " ";
  }
  cout << endl;
  for (int i = 0; i < header.numcols; i++) {
    cout << header.colnums[i] << " ";
  }
  cout << endl << header.div << endl;
}

void printtableau(Tableau tableau) {
  for (int r = 0; r < tableau.numrows; r++) {
    for (int c = 0; c < tableau.numcols; c++) {
      if (r == (tableau.numrows - 1)) {
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

Tableau initgame(Deck cards, Tableau tableau) {
  for (int r = 0; r < tableau.numcols; r++) {
    for (int c = r; c < tableau.numcols; c++) {
      int cardpos = (r * tableau.numcols) + c;
      tableau.arr[r][c] = cards.arr[cardpos];
    }
  }
  return tableau;
}

Deck remcard(Deck cards, int pos) {
  cards.arr[pos] = "---";
  return cards;
}

string validdestcards(string uin, Tableau tableau) {
  cout << ' ';
  return uin;
}

int main() {
  Deck cards = carddeck();
  Header header;
  Tableau tableau = cardtableau();
  cards = shuffledeck(cards);
  tableau = initgame(cards, tableau);
  for (int i = 0; i < pow(tableau.numcols, 2); i++) {
    cards = remcard(cards, i);
  }
  string uin = printrules();
  while (uin != "exit") {
    printgame(header, tableau);
    getline(cin, uin);
    if (uin == "exit") {
      cout << "this ran";
    } else if (uin == "stock") {
      cout << ' ';
    } else if (uin == "waste") {
      cout << ' ';
    } else if (uin == "print rules") {
      printrules();
    } else {
      string validdests = validdestcards(uin, tableau);
    }
  }
  return 0;
}
