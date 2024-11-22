import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.TreeSet;
import java.util.PriorityQueue;

record RWord(String word, int rank) implements Comparable<RWord> {
  public int compareTo(RWord other) {
    if (this.rank == other.rank()) {
      return this.word.compareTo(other.word());
    } else {
      return (this.rank - other.rank());
    }
  }
  public String toString() {
    return (word + " : " + rank);
  }
}
public class wordfinder extends JFrame implements KeyListener {
  public int XDIM, YDIM;
  public Graphics display;
  public int yoff = 40;
  public int xoff = 40;
  int lower = YDIM - 40;
  StringTrie<Integer> rwords = new StringTrie<Integer>();
  PriorityQueue<RWord> ranked = new PriorityQueue<RWord>();
  public wordfinder(int x, int y) {
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
      Scanner scin2 = new Scanner(new File("hfwords.txt"));
      while (scin2.hasNextInt()) {
        int rank = scin2.nextInt();
        String word = scin2.next();
        rwords.set(word, rank);
      }
      //System.out.println(rwords.size());
    } catch (Exception e) {
      System.err.println("hfwords.txt not found");
    }
  }
  void newframe() {
    display.setColor(Color.white);
    display.fillRect(0, 0, XDIM, YDIM);
    display.setColor(Color.blue);
    display.drawString("Enter: " + rwords.current_key(), xoff + 10, YDIM - 60);
  }
  void next(char nextchar) {
    rwords.continue_search(nextchar);
    newframe();
    rwords.current_stream(5).limit(20).forEach(sv -> {
      //System.out.println("adding " + sv);
      ranked.add(new RWord(sv.key(), sv.val()));
    });
    int ypos = yoff + 20;
    int dcount = 0;
    while ((ranked.size() > 0) && (dcount++ < 10)) {
      var rw = ranked.poll();
      display.drawString(rw.word()/* + " : " + rw.rank()*/, xoff + 12, ypos + yoff);
      ypos += 30;
    }
	  ranked.clear();
  }
  public void paint(Graphics g) {
    newframe();
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  public void keyPressed(KeyEvent e) {
	  int key = e.getKeyCode();
    System.out.println("key: " + key);
    if ((key >= 65) && (key <= 90)) {
      next((char) (key + 32));
    } else if (key == 8) {
      String cs = rwords.current_key();
      if (rwords.key_length(cs) > 1) {
        ranked.clear();
        char newlast = rwords.kctAt(cs, rwords.key_length(cs) - 2);
        String cs2 = cs.substring(0, rwords.key_length(cs) - 2);
        rwords.begin_continuation(cs2);
        //System.out.println(rwords.can_continue() + ": " + cs2);
        next(newlast);
      } else if (rwords.key_length(cs) > 0) {
        rwords.reset_continuation();
        newframe();
        ranked.clear();
      }
    } else if (key == 10) {
      rwords.reset_continuation();
      newframe();
      ranked.clear();
    }
  }
  public static void main(String[] args) {
    wordfinder wf = new wordfinder(400, 500);
    wf.newframe();
    wf.requestFocus();
  }
}