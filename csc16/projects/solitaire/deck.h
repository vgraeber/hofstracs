#ifndef DECK_H
#define DECK_H

#include <vector>
#include <string>
#include <algorithm>
#include <random>
using namespace std;

class Deck {
    vector<string> cards;
    int nextcardind;
    string dispcard;
  public:
    //called at the start to create the deck of cards
    void filldeck() {
      string suits[4] = {"S", "H", "C", "D"};
      string ranks[13] = {"A ", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9", "10", "J ", "Q ", "K "};
      for (int s = 0; s < 4; s++) {
        for (int r = 0; r < 13; r++) {
          string card = ranks[r] + suits[s];
          cards.push_back(card);
        }
      }
    }
    //called upon to randomize the order of the cards
    void shuffledeck() {
      auto rd = random_device {};
      auto rng = default_random_engine {rd()};
      shuffle(cards.begin(), cards.end(), rng);
    }
    //removes the cards put onto the tableau at the start
    void remstartcards() {
      cards.erase(cards.begin() + 0, cards.begin() + 28);
    }
    //called upon whenever a card is moved from the discard to the tableau
    void remdiscard() {
      if (dispcard != "---") {
        nextcardind -= 1;
        cards.erase(cards.begin() + nextcardind);
        if (nextcardind == 0) {
          dispcard = "---";
        } else {
          dispcard = cards[nextcardind - 1];
        }
      }
    }
    //movea a card from the stock to the discard
    void flipstock() {
      if (nextcardind == cards.size()) {
        dispcard = "---";
        nextcardind = 0;
      } else {
        dispcard = cards[nextcardind];
        nextcardind += 1;
      }
    }
    //returns true if the user has fliped through the whole stock
    bool endstock() {
      return (nextcardind == cards.size());
    }
    //returns the card currently on the discard
    string getdispcard() {
      return dispcard;
    }
    //returns all of the cards (only called upon at the start to fill the tableau)
    vector<string> getcards() {
      return cards;
    }
    Deck() {
      filldeck();
      shuffledeck();
      dispcard = "---";
      nextcardind = 0;
    }
};

#endif
