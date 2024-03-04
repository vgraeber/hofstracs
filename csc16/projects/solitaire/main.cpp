#include <iostream>
#include <vector>
#include <tuple>
#include <string>
#include <algorithm>
#include <random>
using namespace std;

//this is the buffer between columns - DO NOT GO BELOW 2 SPACES
string buffer = "  ";

class Deck {
  public:
    vector<string> filldeck() {
      string suits[4] = {"S", "H", "C", "D"};
      string ranks[13] = {"A ", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", "J ", "Q ", "K "};
      vector<string> cards;
      for (int s = 0; s < 4; s++) {
        for (int r = 0; r < 13; r++) {
          string card = ranks[r] + suits[s];
          cards.push_back(card);
        }
      }
      return cards;
    }
    vector<string> shuffledeck(vector<string> deck) {
      auto rd = random_device {};
      auto rng = default_random_engine {rd()};
      shuffle(deck.begin(), deck.end(), rng);
      return deck;
    }
    vector<string> remcard(int cardind, vector<string> deck) {
      deck.erase(deck.begin() + cardind);
      return deck;
    }
};

class Header {
  public:
    vector<vector<string>> makeheader() {
      vector<vector<string>> header;
      header.resize(4);
      header[0] = {"***", "---", "   ", "--S", "--H", "--C", "--D"};
      header[1] = {"   ", "   ", "   ", "   ", "   ", "   ", "   "};
      header[2] = {" C1", " C2", " C3", " C4", " C5", " C6", " C7"};
      header[3] = {"~~~", "~~~", "~~~", "~~~", "~~~", "~~~", "~~~"};
      return header;
    }
    int getsuitnum(string card) {
      char suit = card[card.length() - 1];
      int suitnum;
      (suit == 'S') ? (suitnum = 0) : (suit == 'H') ? (suitnum = 1) : (suit == 'C') ? (suitnum = 2) : (suitnum = 3);
      return suitnum;
    }
    vector<vector<string>> addtofoundation(string card, vector<vector<string>> header) {
      int foundind = getsuitnum(card);
      header[0][3 + foundind] = card;
      return header;
    }
    bool canaddtofoundation(string card, vector<vector<string>> header) {
      unordered_map<string, string> nextcard = {{"--", "A "}, {"A ", " 2"}, {" 2", " 3"}, {" 3", " 4"}, {" 4", " 5"}, {" 5", " 6"}, {" 6", " 7"}, {" 7", " 8"}, {" 8", " 9"}, {" 9", "10"}, {"10", "J "}, {"J ", "Q "}, {"Q ", "K "}, {" K", "None"}};
      for (int i = 0; i < 4; i++) {
        string cardnum = header[0][3 + i].substr(0 ,2);
        string reqcard = nextcard[cardnum] + header[0][3 + i][header[0][3 + i].length() - 1];
        if (card == reqcard) {
          return true;
        }
      }
      return false;
    }
    void printheader(vector<vector<string>> header) {
      cout << endl;
      for (int i = 0; i < header.size(); i++) {
        for (int j = 0; j < header[i].size(); j++) {
          cout << header[i][j] << buffer;
        }
        cout << endl;
      }
    }
    vector<vector<string>> remfromwaste(vector<string> cards, int cardind, vector<vector<string>> header) {
      if (cardind == 1) {
        header[0][1] = "---";
      } else {
        header[0][1] = cards[cardind - 2];
      }
      return header;
    }
    vector<vector<string>> addtowaste(vector<string> cards, int cardind, vector<vector<string>> header) {
      if (cardind == cards.size()) {
        header[0][0] = "***";
        header[0][1] = "---";
        return header;
      }
      header[0][1] = cards[cardind];
      if (cardind == (cards.size() - 1)) {
        header[0][0] = "---";
      }
      return header;
    }
};

class Body {
  public:
    vector<vector<tuple<string, bool, bool>>> maketableau() {
      vector<vector<tuple<string, bool, bool>>> tableau;
      tableau.resize(7);
      for (int i = 0; i < tableau.size(); i++) {
        tableau[i].resize(7);
        for (int j = 0; j < tableau[i].size(); j++) {
          tableau[i][j] = make_tuple("   ", true, false);
        }
      }
      return tableau;
    }
    vector<vector<tuple<string, bool, bool>>> fillstarttableau(vector<string> cards, vector<vector<tuple<string, bool, bool>>> tableau) {
      for (int i = 0; i < 7; i++) {
        for (int j = i; j < 7; j++) {
          int cardind = (i * 7) + j;
          get<0>(tableau[i][j]) = cards[cardind];
          get<1>(tableau[i][j]) = false;
        }
      }
      for (int i = 0; i < 7; i++) {
        get<1>(tableau[i][i]) = true;
        get<2>(tableau[i][i]) = true;
      }
      return tableau;
    }
    int findemptyrowincol(int col, vector<vector<tuple<string, bool, bool>>> tableau) {
      for (int i = 0; i < tableau.size(); i++) {
        if (get<2>(tableau[i][col])) {
          if (get<0>(tableau[i][col]) == "---") {
            return 0;
          }
          return (i + 1);
        }
      }
      return -1;
    }
    //checks to see if the last row can be removed
    bool checklastrow(vector<vector<tuple<string, bool, bool>>> tableau) {
      int row = tableau.size() - 1;
      for (int j = 0; j < tableau[row].size(); j++) {
        if (get<0>(tableau[row][j]) != "   ") {
          return false;
        }
      }
      return true;
    }
    vector<vector<tuple<string, bool, bool>>> remrow(vector<vector<tuple<string, bool, bool>>> tableau) {
      int currrows = tableau.size();
      tableau.resize(currrows - 1);
      return tableau;
    }
    vector<vector<tuple<string, bool, bool>>> addrow(vector<vector<tuple<string, bool, bool>>> tableau) {
      int currrows = tableau.size();
      tableau.resize(currrows + 1);
      tableau[currrows].resize(7);
      for (int j = 0; j < tableau[currrows].size(); j++) {
        tableau[currrows][j] = make_tuple("   ", true, false);
      }
      return tableau;
    }
    vector<vector<tuple<string, bool, bool>>> addcard(int row, int col, string card, vector<vector<tuple<string, bool, bool>>> tableau) {
      tableau[row][col] = make_tuple(card, true, true);
      get<2>(tableau[row - 1][col]) = false;
      return tableau;
    }
    vector<vector<tuple<string, bool, bool>>> remcard(string card, vector<vector<tuple<string, bool, bool>>> tableau) {
      for (int i = 0; i < tableau.size(); i++) {
        for (int j = 0; j < tableau[i].size(); j++) {
          if (get<0>(tableau[i][j]) == card) {
            tableau[i][j] = make_tuple("   ", true, false);
            if (i != 0) {
              get<1>(tableau[i - 1][j]) = true;
              get<2>(tableau[i - 1][j]) = true;
            } else {
              get<0>(tableau[i][j]) = "---";
              get<2>(tableau[i][j]) = true;
            }
          }
        }
      }
      return tableau;
    }
    void printbody(vector<vector<tuple<string, bool, bool>>> tableau) {
      for (int i = 0; i < tableau.size(); i++) {
        for (int j = 0; j < tableau[i].size(); j++) {
          if (get<1>(tableau[i][j])) {
            cout << get<0>(tableau[i][j]) << buffer;
          } else {
            cout << "***" << buffer;
          }
        }
        cout << endl;
      }
      cout << endl;
    }
};

//class objects for our game :) - KEEP GLOBAL, MUCH EASIER THIS WAY
Deck deck;
Header head;
Body body;

//the things the game will be based off of - NEED TO BE GLOBAL, WORKAROUNDS ARE TOO DIFFICULT BC WE NEED THESE PRETTY MUCH EVERYWHERE
vector<string> cards = deck.shuffledeck(deck.filldeck());
vector<vector<string>> header = head.makeheader();
vector<vector<tuple<string, bool, bool>>> tableau = body.maketableau();

void dispgame() {
  head.printheader(header);
  body.printbody(tableau);
}

void checktableau() {
  vector<int> colcheck = {0, 1, 2, 3, 4, 5, 6};
  while (colcheck.size() > 0) {
    int i = colcheck[0];
    int j = body.findemptyrowincol(i, tableau) - 1;
    if (j == -1) {
      j = 0;
    }
    if (head.canaddtofoundation(get<0>(tableau[j][i]), header)) {
      header = head.addtofoundation(get<0>(tableau[j][i]), header);
      tableau = body.remcard(get<0>(tableau[j][i]), tableau);
      colcheck.push_back(i);
      dispgame();
    }
    colcheck.erase(colcheck.begin());
  }
}

void checkheader(int cardind) {
  if (head.canaddtofoundation(header[0][1], header)) {
    header = head.addtofoundation(header[0][1], header);
    header = head.remfromwaste(cards, cardind, header);
    cards = deck.remcard(cardind, cards);
    dispgame();
  }
}

int flipstock(int cardind) {
  header = head.addtowaste(cards, cardind, header);
  cardind += 1;
  if (cardind == (cards.size() + 1)) {
    cardind = 0;
  }
  dispgame();
  return cardind;
}

int main() {
  int cardind = 0;
  tableau = body.fillstarttableau(cards, tableau);
  dispgame();
  string uin = "";
  while (uin != "exit") {
    checktableau();
    checkheader(cardind);
    getline(cin, uin);
    if (uin == "stock") {
      cardind = flipstock(cardind);
    } else if (uin == "waste") {
      
    } else if (uin != "exit") {
      
    }
  }
  return 0;
}
