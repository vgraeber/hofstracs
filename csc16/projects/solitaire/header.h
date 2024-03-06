#include <iostream>
#include <vector>
#include <string>

class Header {
  vector<vector<string>> header;
  public:
    void makeheader() {
      header.resize(4);
      header[0] = {"***", "---", "   ", "--S", "--H", "--C", "--D"};
      header[1] = {"   ", "   ", "   ", "   ", "   ", "   ", "   "};
      header[2] = {" C1", " C2", " C3", " C4", " C5", " C6", " C7"};
      header[3] = {"~~~", "~~~", "~~~", "~~~", "~~~", "~~~", "~~~"};
    }
    int getsuitnum(string card) {
      char suit = card[card.length() - 1];
      int suitnum;
      (suit == 'S') ? (suitnum = 0) : (suit == 'H') ? (suitnum = 1) : (suit == 'C') ? (suitnum = 2) : (suitnum = 3);
      return suitnum;
    }
    void addtofound(string card) {
      int foundind = getsuitnum(card);
      header[0][3 + foundind] = card;
    }
    bool canaddtofound(string card) {
      unordered_map<string, string> nextcard = {{"--", "A "}, {"A ", " 2"}, {" 2", " 3"}, {" 3", " 4"}, {" 4", " 5"}, {" 5", " 6"}, {" 6", " 7"}, {" 7", " 8"}, {" 8", " 9"}, {" 9", "10"}, {"10", "J "}, {"J ", "Q "}, {"Q ", "K "}, {"K ", "--"}};
      for (int i = 0; i < 4; i++) {
        string cardnum = header[0][3 + i].substr(0 ,2);
        string reqcard = nextcard[cardnum] + header[0][3 + i][header[0][3 + i].length() - 1];
        if (card == reqcard) {
          return true;
        }
      }
      return false;
    }
    bool foundfull() {
      vector<string> endfound = {"K S", "K H", "K C", "K D"};
      for (int i = 0; i < 4; i++) {
        string currcard = header[0][3 + i];
        string reqcard = endfound[i];
        if (currcard != reqcard) {
          return false;
        }
      }
      return true;
    }
    void discard(string card) {
      header[0][0] = "***";
      header[0][1] = card;
    }
    void togglestock() {
      header[0][0] = "---";
    }
    string getcol(int col) {
      return header[2][col];
    }
    void printheader() {
      string buffer = "  ";
      cout << endl;
      for (int i = 0; i < header.size(); i++) {
        for (int j = 0; j < header[i].size(); j++) {
          cout << header[i][j] << buffer;
        }
        cout << endl;
      }
    }
    Header() {
      makeheader();
    }
};
