#include <iostream>
#include <vector>
#include <tuple>
#include <string>
#include <algorithm>
#include <random>
using namespace std;

class Deck {
  public:
    vector<string> cards = shuffledeck(filldeck());
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
};

class Game {
  public:
    inline static const string buffer = "  ";
    class Header {
      public:
        vector<vector<string>> header = makeheader();
        vector<vector<string>> makeheader() {
          header.resize(3);
          header[0] = {"***", "---", "   ", "---", "---", "---", "---"};
          header[1] = {" C1", " C2", " C3", " C4", " C5", " C6", " C7"};
          header[2] = {"~~~", "~~~", "~~~", "~~~", "~~~", "~~~", "~~~"};
          return header;
        }
        void printheader() {
          for (int i = 0; i < header.size(); i++) {
            for (int j = 0; j < header[i].size(); j++) {
              cout << header[i][j] << buffer;
            }
          }
          cout << endl;
        }
    };
    class Body {
      public:
        vector<vector<tuple<string, bool, bool>>> tableau = maketableau();
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
        int findemptyrowincol(int col, vector<vector<tuple<string, bool, bool>>> tableau) {
          for (int i = 0; i < tableau.size(); i++) {
            if (get<2>(tableau[i][col])) {
              if (get<0>(tableau[i][col]) == "---") {
                return 0;
              }
              return (i + 1);
            }
          }
        }
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
        void printbody() {
          for (int i = 0; i < tableau.size(); i++) {
            for (int j = 0; j < tableau[i].size(); j++) {
              if (get<1>(tableau[i][j])) {
                cout << get<0>(tableau[i][j]) << buffer;
              } else {
                cout << "***" << buffer;
              }
            }
          }
        }
    };
};

int main() {
  Game game;
  return 0;
}
