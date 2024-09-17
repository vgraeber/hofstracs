import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class maze extends mazebase {
  static int[] rowChange = {-1, 1, 0, 0};
  static int[] colChange = {0, 0, 1, -1};
  static ArrayList<int[]> solution = new ArrayList<int[]>();
  static int playerRow = 1;
  static int playerCol = 1;
  static int playerSolve = 0;
  public maze() {
    super();
  }
  public void shuffleDirs(int[] dirs) {
    for (int i = 0; i < dirs.length - 1; i++) {
      int r = i + (int)(Math.random() * (dirs.length - i));
      int temp = dirs[i];
      dirs[i] = dirs[r];
      dirs[r] = temp;
    }
  }
  public boolean inBorder(int row, int col) {
    if (((0 < row) && (row < mheight - 1)) && ((0 < col) && (col < mwidth - 1))) {
      return true;
    }
    return false;
  }
  @Override
  public void digout(int row, int col) {
    M[row][col] = 1;
    drawblock(row, col);
    int[] dirs = {0, 1, 2, 3};
    shuffleDirs(dirs);
    for (int dir : dirs) {
      int newRow = row + 2 * rowChange[dir];
      int newCol = col + 2 * colChange[dir];
      if (inBorder(newRow, newCol) && (M[newRow][newCol] == 0)) {
        int tempRow = row + rowChange[dir];
        int tempCol = col + colChange[dir];
        M[tempRow][tempCol] = 1;
        drawblock(tempRow, tempCol);
        digout(newRow, newCol);
      }
    }
  }
  public boolean inBounds(int row, int col) {
    if (((0 <= row) && (row < mheight)) && ((0 <= col) && (col < mwidth))) {
      return true;
    }
    return false;
  }
  public void countMaze() {
    int[] dirs = {0, 1, 2, 3};
    for (int row = 0; row < mheight; row++) {
      for (int col = 0; col < mwidth; col++) {
        int potDirs = 0;
        for (int dir : dirs) {
          int tempRow = row + rowChange[dir];
          int tempCol = col + colChange[dir];
          if (inBounds(tempRow, tempCol) && (M[tempRow][tempCol] >= 1)) {
            potDirs += 1;
          }
        }
        if ((M[row][col] >= 1) && (potDirs > 2)) {
          potDirs -= 1;
          M[row][col] = potDirs;
        }
        if (M[row][col] >= 1) {
          M[row][col] += 1;
          drawblock(row, col);
        }
      }
    }
  }
  public boolean solved(int currRow, int currCol, int endRow, int endCol) {
    if ((currRow == endRow) && (currCol == endCol)) {
      return true;
    }
    return false;
  }
  public boolean notPrevSpace(int row, int col) {
    if (solution.size() == 0) {
      return true;
    } else if ((row != solution.get(solution.size() - 1)[0]) || (col != solution.get(solution.size() - 1)[1])) {
      return true;
    }
    return false;
  }
  @Override
  public void solve() {
    int currRow = 1;
    int currCol = 1;
    int endRow = mheight - 2;
    int endCol = mwidth - 1;
    M[endRow][endCol] = 1;
    drawblock(endRow, endCol);
    countMaze();
    int[] dirs = {0, 1, 2, 3};
    while (!solved(currRow, currCol, endRow, endCol)) {
      boolean moved = true;
      while (moved && !solved(currRow, currCol, endRow, endCol)) {
        for (int dir : dirs) {
          int newRow = currRow + rowChange[dir];
          int newCol = currCol + colChange[dir];
          if ((M[newRow][newCol] > 1) && notPrevSpace(newRow, newCol)) {
            M[currRow][currCol] -= 1;
            int[] info = {currRow, currCol, M[currRow][currCol]};
            solution.add(info);
            currRow = newRow;
            currCol = newCol;
            break;
          } else if (dir == (dirs.length - 1)) {
            M[currRow][currCol] -= 1;
            moved =  false;
          }
        }
      }
      if (!solved(currRow, currCol, endRow, endCol)) {
        while (solution.get(solution.size() - 1)[2] == 1) {
          solution.removeLast();
        }
        currRow = solution.getLast()[0];
        currCol = solution.getLast()[1];
        solution.removeLast();
      }
    }
    int[] info = {currRow, currCol, M[currRow][currCol]};
    solution.add(info);
  }
  @Override
  public void trace() {
    for (int i = solution.size() - 1; i >= 0; i--) {
      int[] info = solution.get(i);
      M[info[0]][info[1]] = -1;
    }
  }
  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    //up, down, left, right
    int[] arrows  = {38, 40, 37, 39};
    int[] wsad = {87, 83, 65, 68};
    int[] dirs = {0, 1, 3, 2};
    int newRow = playerRow;
    int newCol = playerCol;
    if (!solved(newRow, newCol, solution.getLast()[0], solution.getLast()[1])) {
      for (int dir : dirs) {
        if (key == arrows[dir]) {
          newRow += rowChange[dirs[dir]];
          newCol += colChange[dirs[dir]];
        } else if (key == wsad[dir]) {
          newRow += rowChange[dirs[dir]];
          newCol += colChange[dirs[dir]];
        }
      }
      if (M[newRow][newCol] == 0) {
        drawMessage("WALL");
      } else {
        if (solved(newRow, newCol, solution.getLast()[0], solution.getLast()[1])) {
          drawMessage("CONGRATS!");
        } else if (M[newRow][newCol] > 0) {
          drawMessage("WRONG WAY");
        } else {
          int[] currInfo = solution.get(playerSolve);
          int[] newInfo = solution.get(playerSolve + 1);
          if ((newRow == currInfo[0]) && (newCol == currInfo[1])) {
            drawMessage("");
          } else if ((newRow != newInfo[0]) || (newCol != newInfo[1])) {
            drawMessage("BACKWARDS");
            playerSolve -= 1;
          } else {
            drawMessage("");
            playerSolve += 1;
          }
        }
        drawblock(playerRow, playerCol);
        playerRow = newRow;
        playerCol = newCol;
        drawdot(playerRow, playerCol);
      }
      nextframe();
      delay(250);
    }
  }
  @Override
  public void play() {
    drawdot(playerRow, playerCol);
    nextframe();
  }
  public static void main(String[] args) {
	  new maze();
  }
  @Override
  public void customize() {
    pencolor = Color.black;
    mheight = 21;
    mwidth = 31;
  }
}
