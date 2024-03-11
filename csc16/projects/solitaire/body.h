#ifndef BODY_H
#define BODY_H

#include <iostream>
#include <vector>
#include <tuple>
#include <string>

class Body {
  vector<vector<tuple<string, bool, bool>>> tableau;
  public:
    void maketableau() {
      tableau.resize(7);
      for (int i = 0; i < tableau.size(); i++) {
        tableau[i].resize(7);
        for (int j = 0; j < tableau[i].size(); j++) {
          tableau[i][j] = make_tuple("   ", true, false);
        }
      }
    }
    void fillstarttableau(vector<string> cards) {
      int cardind = 0;
      for (int i = 0; i < 7; i++) {
        for (int j = i; j < 7; j++) {
          get<0>(tableau[i][j]) = cards[cardind];
          get<1>(tableau[i][j]) = false;
          cardind += 1;
        }
      }
      for (int i = 0; i < 7; i++) {
        get<1>(tableau[i][i]) = true;
        get<2>(tableau[i][i]) = true;
      }
    }
    int findemptyrowincol(int col) {
      for (int i = 0; i < tableau.size(); i++) {
        if (get<2>(tableau[i][col])) {
          if (get<0>(tableau[i][col]) == "---") {
            return 0;
          }
          return (i + 1);
        }
      }
    }
    //checks to see if the last row can be removed
    bool checklastrow() {
      int row = tableau.size() - 1;
      for (int j = 0; j < tableau[row].size(); j++) {
        if (get<0>(tableau[row][j]) != "   ") {
          return false;
        }
      }
      return true;
    }
    void remlastrow() {
      int currrows = tableau.size();
      tableau.resize(currrows - 1);
    }
    void addrow() {
      int currrows = tableau.size();
      tableau.resize(currrows + 1);
      tableau[currrows].resize(7);
      for (int j = 0; j < tableau[currrows].size(); j++) {
        tableau[currrows][j] = make_tuple("   ", true, false);
      }
    }
    void addcard(int col, string card) {
      int row = findemptyrowincol(col);
      if (row == tableau.size()) {
        addrow();
      }
      tableau[row][col] = make_tuple(card, true, true);
      if (row != 0) {
        get<2>(tableau[row - 1][col]) = false;
      }
    }
    void remcard(string card) {
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
    }
    void printbody() {
      string buffer = "  ";
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
    string getcard(int row, int col) {
      return get<0>(tableau[row][col]);
    }
    vector<int> getvalcols(string card) {
      unordered_map<string, string> prevcardnums = {{"--", "None"}, {"A ", " 2"}, {" 2", " 3"}, {" 3", " 4"}, {" 4", " 5"}, {" 5", " 6"}, {" 6", " 7"}, {" 7", " 8"}, {" 8", " 9"}, {" 9", "10"}, {"10", "J "}, {"J ", "Q "}, {"Q ", "K "}, {"K ", "--"}};
      unordered_map<char, vector<string>> prevsuits = {{'S', {"H", "D"}}, {'H', {"S", "C"}}, {'C', {"H", "D"}}, {'D', {"S", "C"}}, {'-', {"-", "-"}}};
      string cardnum = card.substr(0, 2);
      char cardsuit = card[card.length() - 1];
      string prevcard1 = prevcardnums[cardnum] + prevsuits[cardsuit][0];
      string prevcard2 = prevcardnums[cardnum] + prevsuits[cardsuit][1];
      if (cardnum == "K ") {
        prevcard1 = "---";
        prevcard2 = "---";
      }
      vector<int> valcols;
      for (int i = 0; i < 7; i++) {
        int j = findemptyrowincol(i);
        if (j != 0) {
          j -= 1;
        }
        if ((get<0>(tableau[j][i]) == prevcard1) || (get<0>(tableau[j][i]) == prevcard2)) {
          valcols.push_back(i);
        }
      }
      return valcols;
    }
    vector<string> getvalstrings() {
      vector<string> valstrings;
      for (int i = 0; i < tableau.size(); i++) {
        for (int j = 0; j < tableau[i].size(); j++) {
          if (get<1>(tableau[i][j])) {
            valstrings.push_back(get<0>(tableau[i][j]));
          }
        }
      }
      return valstrings;
    }
    vector<int> findcardpos(string card) {
      for (int i = 0; i < tableau.size(); i++) {
        for (int j = 0; j < tableau[i].size(); j++) {
          if (get<0>(tableau[i][j]) == card) {
            vector<int> cardpos = {i, j};
            return cardpos;
          }
        }
      }
    }
    Body() {
      maketableau();
    }
};

#endif
