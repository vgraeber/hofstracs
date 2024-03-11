#include "deck.h"
#include "header.h"
#include "body.h"
#include <iostream>
#include <vector>
#include <string>
using namespace std;

//class objects for our game :) - KEEP GLOBAL, NEEDED
Deck deck;
Header head;
Body body;

void disprules() {
  cout << "Welcome to vgraeber's version of Klondike Solitaire!" << endl;
  cout << "Please note that in order to play this, you must be capable of giving keyboard input." << endl;
  cout << "Here is a visual guide to the game with the areas some common terms refer to boxed in:" << endl << endl
       << "stock ┌-discard     foundation" << endl
       << "┌-┴-┐┌┴--┐     ┌--------┴---------┐" << endl
       << "|***||---|     |--S  --H  --C  --D|" << endl
       << "└---┘└---┘     └------------------┘" << endl
       << "  C1   C2   C3   C4   C5   C6   C7" << endl
       << " ~~~  ~~~  ~~~  ~~~  ~~~  ~~~  ~~~" << endl
       << "┌---------------------------------┐" << endl
       << "|***  ***  ***  ***  ***  ***  ***|" << endl
       << "|     ***  ***  ***  ***  ***  ***|" << endl
       << "|          ***  ***  ***  ***  ***|" << endl
       << "|               ***  ***  ***  ***├-tableau" << endl
       << "|                    ***  ***  ***|" << endl
       << "|                         ***  ***|" << endl
       << "|                              ***|" << endl
       << "└---------------------------------┘" << endl << endl;
  cout << "Here are the commands you can use:" << endl;
  cout << "1. 'stock' - flips a card from the stock to the discard" << endl;
  cout << "2. 'discard' - moves a card from the discard to the tableau" << endl;
  cout << "3. 'help' - displays this text again" << endl;
  cout << "4. 'exit' - quits the game" << endl;
  cout << "5. '2 H', '5 C', '7 D', etc. - moves the specified card around on the tableau" << endl;
  cout << "Note that all spacing is significant, and that if you do not format your commands correctly, they will not work." << endl;
  cout << "We hope you enjoy!" << endl;
}

void dispgame() {
  head.printheader();
  body.printbody();
}

void checktableau() {
  vector<int> colcheck = {0, 1, 2, 3, 4, 5, 6};
  while (colcheck.size() > 0) {
    int i = colcheck[0];
    int j = body.findemptyrowincol(i) - 1;
    if (j == -1) {
      j = 0;
    }
    if (head.canaddtofound(body.getcard(j, i))) {
      head.addtofound(body.getcard(j, i));
      body.remcard(body.getcard(j, i));
      colcheck.push_back(i);
      dispgame();
    }
    colcheck.erase(colcheck.begin());
  }
  while (body.checklastrow()) {
    body.remlastrow();
  }
}

void checkheader() {
  while (head.canaddtofound(deck.getdispcard())) {
    head.addtofound(deck.getdispcard());
    deck.remdiscard();
    head.discard(deck.getdispcard());
    dispgame();
  }
}

void flipstock() {
  deck.flipstock();
  head.discard(deck.getdispcard());
  if (deck.endstock()) {
    head.togglestock();
  }
  dispgame();
}

void movefromdiscard(int col) {
  body.addcard(col, deck.getdispcard());
  deck.remdiscard();
  head.discard(deck.getdispcard());
  dispgame();
}

void moveintableau(string card, int col) {
  vector<int> cardpos = body.findcardpos(card);
  int end = body.findemptyrowincol(cardpos[1]);
  vector<string> cards;
  for (int i = cardpos[0]; i < end; i++) {
    cards.push_back(body.getcard(i, cardpos[1]));
  }
  for (int i = cards.size(); i > 0; i--) {
    body.remcard(cards[i - 1]);
  }
  for (int i = 0; i < cards.size(); i++) {
    body.addcard(col, cards[i]);
  }
  dispgame();
}

void invalid() {
  cout << "Sorry, that's not possible." << endl;
  dispgame();
}

void movecard(string card, vector<int> valcols, bool waste) {
  if (valcols.empty()) {
    invalid();
  } else if (valcols.size() == 1) {
    (waste) ? movefromdiscard(valcols[0]) : moveintableau(card, valcols[0]);
  } else {
    cout << "Which column?" << endl;
    vector<string> valstrcols;
    string uin = "";
    for (int i = 0; i < valcols.size(); i++) {
      cout << "'" << head.getcol(valcols[i]) << "'";
      valstrcols.push_back(head.getcol(valcols[i]));
      if (i != (valcols.size() - 1)){
        cout << ", ";
      }
    }
    cout << endl;
    getline(cin, uin);
    if (find(valstrcols.begin(), valstrcols.end(), uin) != valstrcols.end()) {
      int col = int(uin[uin.length() - 1]);
      (waste) ? movefromdiscard(col) : moveintableau(card, col);
    } else if (find(valcols.begin(), valcols.end(), (stoi(uin) - 1)) != valcols.end()) {
      (waste) ? movefromdiscard(stoi(uin) - 1) : moveintableau(card, stoi(uin) - 1);
    } else {
      invalid();
    }
  }
}

int main() {
  body.fillstarttableau(deck.getcards());
  deck.remstartcards();
  disprules();
  dispgame();
  string uin = "";
  while (uin != "exit") {
    checkheader();
    checktableau();
    getline(cin, uin);
    transform(uin.begin(), uin.end(), uin.begin(), ::tolower);
    if (uin == "stock") {
      flipstock();
    } else if (uin == "discard") {
      vector<int> valcols = body.getvalcols(deck.getdispcard());
      movecard(deck.getdispcard(), valcols, true);
    } else if (uin == "help") {
      disprules();
    } else if (uin != "exit") {
      transform(uin.begin(), uin.end(), uin.begin(), ::toupper);
      vector<string> valstrings = body.getvalstrings();
      if (find(valstrings.begin(), valstrings.end(), uin) != valstrings.end()) {
        string card = uin;
        vector<int> valcols = body.getvalcols(uin);
        movecard(card, valcols, false);
      } else {
        invalid();
      }
    }
    cout << endl;
  }
  return 0;
}
