package avltree;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class BstGraph extends JFrame {
  public int xBound, yBound;
  public Graphics display;
  public Tree<?> currentTree;
  public int branchHeight = 50;
  public int yOffset = 100;
  public int xOffset = 8;
  public BstGraph(int x, int y) {
    xBound = x;
    yBound = y;
    this.setBounds(0, 0, xBound + 4, yBound);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    display = this.getGraphics();
    display.setColor(Color.black);
    display.fillRect(0, 0, x, y);
    display.setColor(Color.red);
    try {
      Thread.sleep(700);
    } catch(Exception e) {}
  }
  public void paint(Graphics g) {
    drawtree(currentTree);
  }
  // currLevel is level, leftBound, rightBound are the bounds (position of left and right child)
  public void drawtree(Tree<?> treee) {
    if (treee == null) {
      return;
    }
    currentTree = treee;
    int treeDepth = treee.depth();
    display.setColor(Color.white);
    display.fillRect(0, 0, xBound, yBound);
    if (treeDepth < 1) {
      return;
    }
    branchHeight = (yBound - yOffset) / treeDepth;
    draw(treee, 0, 0, xBound - xOffset);
  }
  public void draw(BstSet<?> treee) {
	  drawtree(treee.root);
  }
  public void draw(Tree<?> Nd, int currLevel, int leftBound, int rightBound) {
	  if (Nd.is_empty()) {
      return;
    }
	  var nodee = (BstSet<?>.Node) Nd;
	  /*
    try {
      Thread.sleep(10);
    } catch(Exception e) {}
    */
    display.setColor(Color.green);
    display.fillOval(((leftBound + rightBound) / 2) - 10, yOffset + (currLevel * branchHeight), 20, 20);
    display.setColor(Color.red);
    display.drawString(nodee.item + "", ((leftBound + rightBound) / 2) - 5, yOffset + 15 + (currLevel * branchHeight));
    display.setColor(Color.blue);
    if (!nodee.left.is_empty()) {
      display.drawLine((leftBound + rightBound) / 2, yOffset + 20 + (currLevel * branchHeight), (((3 * leftBound) + rightBound) / 4), yOffset + ((currLevel * branchHeight) + branchHeight));
      draw(nodee.left, currLevel + 1, leftBound, (leftBound + rightBound) / 2);
    }
    if (!nodee.right.is_empty()) {
      display.drawLine((leftBound + rightBound) / 2, yOffset + 20 + (currLevel * branchHeight), (((3 * rightBound) + leftBound) / 4), yOffset + ((currLevel * branchHeight) + branchHeight));
      draw(nodee.right, currLevel + 1, (leftBound + rightBound) / 2, rightBound);
    }
  }
  public void delay(int ms) {
	  try {
      Thread.sleep(ms);
    } catch (Exception e) {}
  }
  public void display_string(String s, int x, int y) {
	  display.drawString(s, x, y);
  }
  public void close() {
	  dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}