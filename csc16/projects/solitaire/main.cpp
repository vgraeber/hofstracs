#include <iostream>
#include <ncurses.h>

void startgame() {
  initscr();
  refresh();
  int cardwidth = 6;
  int cardlength = 5;
  int wdiff = cardwidth + 1;
  int ldiff = cardlength + 1;
  WINDOW * stock = newwin(cardlength, cardwidth, 1, 1);
  box(stock, 0, 0);
  wrefresh(stock);
  WINDOW * waste = newwin(cardlength, cardwidth, 1, (1 + wdiff));
  box(waste, 0, 0);
  wrefresh(waste);
  WINDOW * spades = newwin(cardlength, cardwidth, 1, (1 + (3 * wdiff)));
  box(spades, 0, 0);
  wrefresh(spades);
  WINDOW * hearts = newwin(cardlength, cardwidth, 1, (1 + (4 * wdiff)));
  box(hearts, 0, 0);
  wrefresh(hearts);
  WINDOW * clubs = newwin(cardlength, cardwidth, 1, (1 + (5 * wdiff)));
  box(clubs, 0, 0);
  wrefresh(clubs);
  WINDOW * diamonds = newwin(cardlength, cardwidth, 1, (1 + (6 * wdiff)));
  box(diamonds, 0, 0);
  wrefresh(diamonds);
  WINDOW * stack0 = newwin(cardlength, cardwidth, (1 + ldiff), 1);
  box(stack0, 0, 0);
  wrefresh(stack0);
  WINDOW * stack1 = newwin((cardlength + 1), cardwidth, (1 + ldiff), (1 + wdiff));
  box(stack1, 0, 0);
  mvwhline(stack1, (ldiff + 2), (wdiff + 1), 0, cardwidth);
  wrefresh(stack1);
  WINDOW * stack2 = newwin((cardlength + 2), cardwidth, (1 + ldiff), (1 + (2 * wdiff)));
  box(stack2, 0, 0);
  for (int i = 2; i < 4; i++) {
    mvwhline(stack2, (ldiff + i), (1 + (2 * wdiff)), 0, cardwidth);
  }
  wrefresh(stack2);
  WINDOW * stack3 = newwin((cardlength + 3), cardwidth, (1 + ldiff), (1 + (3 * wdiff)));
  box(stack3, 0, 0);
  for (int i = 2; i < 5; i++) {
    mvwhline(stack3, (ldiff + i), (1 + (3 * wdiff)), 0, cardwidth);
  }
  wrefresh(stack3);
  WINDOW * stack4 = newwin((cardlength + 4), cardwidth, (1 + ldiff), (1 + (4 * wdiff)));
  box(stack4, 0, 0);
  for (int i = 2; i < 6; i++) {
    mvwhline(stack4, (ldiff + i), (1 + (4 * wdiff)), 0, cardwidth);
  }
  wrefresh(stack4);
  WINDOW * stack5 = newwin((cardlength + 5), cardwidth, (1 + ldiff), (1 + (5 * wdiff)));
  box(stack5, 0, 0);
  for (int i = 2; i < 7; i++) {
    mvwhline(stack5, (ldiff + i), (1 + (5 * wdiff)), 0, cardwidth);
  }
  wrefresh(stack5);
  WINDOW * stack6 = newwin((cardlength + 6), cardwidth, (1 + ldiff), (1 + (6 * wdiff)));
  box(stack6, 0, 0);
  for (int i = 2; i < 8; i++) {
    mvwhline(stack6, (ldiff + i), (1 + (6 * wdiff)), 0, cardwidth);
  }
  wrefresh(stack6);
}

void endgame() {
  endwin();
}

void userinput() {
  int userinput = getch();
}

int main() {
  startgame();
  getch();
  endgame();
  return 0;
}
