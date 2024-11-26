import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.TreeSet;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

record RPhrase(List<String> phrase, int rank) implements Comparable<RPhrase> {
  public int compareTo(RPhrase other) {
    if (this.rank == other.rank()) {
      return this.phrase.toString().compareTo(other.phrase().toString());
    } else {
      return (this.rank - other.rank());
    }
  }
  public String toString() {
    String retstring = "";
    for (String w : phrase) {
        retstring += w + " ";
      }
    retstring += ": " + rank;
    return retstring;
  }
}
public class phraselearner extends JFrame implements KeyListener {
  public int XDIM, YDIM;
  public Graphics display;
  public int yoff = 40;
  public int xoff = 40;
  int lower = YDIM - 40;
  boolean caps = false;
  boolean shift = false;
  PhraseTrie<Integer> rphrases = new PhraseTrie<Integer>();
  PriorityQueue<RPhrase> ranked = new PriorityQueue<RPhrase>();
  public phraselearner(int x, int y) {
    XDIM = x;
    YDIM = y;
    this.setBounds(0, 0, XDIM + 4, YDIM);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    display = this.getGraphics();
    display.setColor(Color.white);
    display.fillRect(0, 0, x, y);
    display.setColor(Color.blue);
    display.setFont(new Font("Serif", Font.BOLD, 24));
    display.drawString("Enter: ", xoff + 20, lower);
	  try {
      Thread.sleep(500);
    } catch(Exception e) {}
    this.addKeyListener(this);
    try {
      Scanner scin2 = new Scanner(new File("phrases.txt"));
      scin2.useDelimiter("\n");
      while (scin2.hasNext()) {
        int rank = 1;
        String words = scin2.next();
        List<String> phrase = new ArrayList<String>(Arrays.asList(words.split(" ")));
        rphrases.set(phrase, rank);
      }
      System.out.println(rphrases.size());
    } catch (Exception e) {
      System.err.println("phrases.txt not found");
    }
  }
  void newframe() {
    display.setColor(Color.white);
    display.fillRect(0, 0, XDIM, YDIM);
    display.setColor(Color.blue);
    display.drawString("Enter: " + rphrases.toStr(rphrases.current_key()), xoff + 10, YDIM - 60);
  }
  String getLastKCT() {
    List<String> currphrase = rphrases.current_key();
    int ind = rphrases.key_length(currphrase) - 1;
    if (ind < 0) {
      return "";
    }
    return rphrases.kctAt(currphrase, ind);
  }
  void remLastKCT() {
    List<String> currphrase = rphrases.current_key();
    if (rphrases.key_length(currphrase) > 0) {
      currphrase.remove(rphrases.key_length(currphrase) - 1);
    }
  }
  void next(char nextchar) {
    String lastword = getLastKCT();
    remLastKCT();
    rphrases.continue_search(lastword + nextchar);
    newframe();
    rphrases.current_stream(5).limit(20).forEach(sv -> {
      System.out.println("adding " + sv);
      ranked.add(new RPhrase(sv.key(), sv.val()));
    });
    int ypos = yoff + 20;
    int dcount = 0;
    while ((ranked.size() > 0) && (dcount++ < 10)) {
      var rw = ranked.poll();
      display.drawString(rw.phrase().toString()/* + " : " + rw.rank()*/, xoff + 10, ypos + yoff);
      ypos += 30;
    }
	  ranked.clear();
  }
  public void paint(Graphics g) {
    newframe();
  }
  void handleBackspace(int n) {
    List<String> currphrase = rphrases.current_key();
    String newlastword = getLastKCT();
    char newlastletter = newlastword.charAt(newlastword.length() - n);
    newlastword = newlastword.substring(0, newlastword.length() - n);
    currphrase.set(rphrases.key_length(currphrase) - 1, newlastword);
    rphrases.begin_continuation(currphrase);
    next(newlastletter);
  }
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == 16) {
      shift = false;
    }
  }
  public void keyTyped(KeyEvent e) {}
  public void keyPressed(KeyEvent e) {
	  int key = e.getKeyCode();
    //System.out.println("key " + key);
    if (key == 16) {
      shift = true;
    } else if (key == 20) {
      caps = !caps;
    } else if ((65 <= key) && (key <= 90)) {
      int diff = 32;
      if (shift ^ caps) {
        diff = 0;
      }
      next((char) (key + diff));
    } else if ((48 <= key) && (key <= 57)) {
      if (!shift) {
        next((char) (key));
      } else {
        char[] alts = {')', '!', '@', '#', '$', '%', '^', '&', '*', '('};
        next (alts[key - 48]);
      }
    } else if ((44 <= key) && (key <= 47)) {
      if (!shift) {
        next((char) (key));
      } else {
        char[] alts = {'<', '_', '>', '?'};
        next (alts[key - 44]);
      }
    } else if ((key == 59) || (key == 61)) {
      if (!shift) {
        next((char) (key));
      } else {
        char[] alts = {':', '+'};
        next (alts[key / 60]);
      }
    } else if ((91 <= key) && (key <= 93)) {
      if (!shift) {
        next((char) (key));
      } else {
        char[] alts = {'{', '|', '}'};
        next (alts[key - 91]);
      }
    } else if ((key == 192) || (key == 222)) {
      if (!shift) {
        char[] vals = {'`', '\''};
        next(vals[key / 200]);
      } else {
        char[] alts = {'~', '"'};
        next (alts[key / 200]);
      }
    } else if (key == 32) {
      rphrases.current_key().add("");
    } else if (key == 8) {
      if (rphrases.key_length(rphrases.current_key()) > 0) {
        ranked.clear();
        if (getLastKCT().length() > 1) {
          handleBackspace(2);
        } else if (rphrases.key_length(rphrases.current_key()) > 1) {
          remLastKCT();
          handleBackspace(1);
        } else {
          rphrases.reset_continuation();
          newframe();
          ranked.clear();
        }
        //System.out.println(rphrases.can_continue() + ": " + currphrase);
      } else {
        rphrases.reset_continuation();
        newframe();
        ranked.clear();
      }
    } else if (key == 10) {
      rphrases.reset_continuation();
      newframe();
      ranked.clear();
    }
  }
  public static void main(String[] args) {
    phraselearner pl = new phraselearner(800, 500);
    pl.newframe();
    pl.requestFocus();
  }
}