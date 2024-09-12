//import java.util.Stack;

public class maze extends mazebase {
  static int[] rowChange = {-1, 1, 0, 0};
  static int[] colChange = {0, 0, 1, -1};
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
  public boolean inBounds(int row, int col) {
    if (((0 < row) && (row < mheight - 1)) && ((0 < col) && (col < mwidth - 1))) {
      return true;
    }
    return false;
  }
  @Override
  public void digout(int row, int col) {
    M[row][col] = 1;
    drawblock(row, col);
    //0 = n, 1 = s, 2 = e, 3 = w
    int[] dirs = {0, 1, 2, 3};
    shuffleDirs(dirs);
    for (int i = 0; i < dirs.length; i++) {
      int dir = dirs[i];
      int newRow = row + 2 * rowChange[dir];
      int newCol = col + 2 * colChange[dir];
      if (inBounds(newRow, newCol) && (M[newRow][newCol] == 0)) {
        int tempRow = row + rowChange[dir];
        int tempCol = col + colChange[dir];
        M[tempRow][tempCol] = 1;
        drawblock(tempRow, tempCol);
        digout(newRow, newCol);
      }
    }
  }
  public void countMaze() {
    int[] dirs = {0, 1, 2, 3};
    for (int row = 1; row < mheight - 1; row++) {
      for (int col = 1; col < mwidth - 1; col++) {
        int potDirs = 0;
        for (int dir = 0; dir < dirs.length; dir++) {
          int tempRow = row + rowChange[dir];
          int tempCol = col + colChange[dir];
          if (M[tempRow][tempCol] >= 1) {
            potDirs += 1;
          }
        }
        if ((potDirs > 2) && (M[row][col] >= 1)) {
          potDirs -= 1;
          M[row][col] = potDirs;
          //System.out.printf("%s%02d%s%02d%s%d%n", "row: ", row, " col: ", col, " potDirs: ", potDirs);
          drawblock(row, col);
        }
        nextframe();
      }
    }
  }
  @Override
  public void solve() {
    int currRow = 1;
    int currCol = 1;
    int endRow = mheight - 2;
    int endCol = mwidth - 2;
    countMaze();
    drawdot(currRow, currCol);
    nextframe();
    /*
    while ((currRow != endRow) && (currCol != endCol)) {
      if (M[currRow][currCol] == 1) {
        boolean moved = false;
        int dir = 0;
        while (!moved) {
          int tempRow = currRow + rowChange[dir];
          int tempCol = currCol + colChange[dir];
          if (M[tempRow][tempCol] >= 1) {
            M[currRow][currCol] -= 1;
            currRow = tempRow;
            currCol = tempCol;
            moved = true;
          }
          dir += 1;
        }
      } else if (M[currRow][currCol] > 1) {
        boolean startDir = false;
        int dir = 0;
        int tempRow = 0;
        int tempCol = 0;
        while (!startDir) {
          tempRow = currRow + rowChange[dir];
          tempCol = currCol + colChange[dir];
          if (M[tempRow][tempCol] >= 1) {
            startDir = true;
          } else {
            dir += 1;
          }
        }
        int[] info = {currRow, currCol, dir};
        branches.push(info);
        M[currRow][currCol] -= 1;
        currRow = tempRow;
        currCol = tempCol;
      } else {
        int[] info = branches.pop();
        currRow = info[0];
        currCol = info[1];
        for (int newDir = info[2]; newDir < dirs.length; newDir++) {
          int tempRow = currRow + rowChange[newDir];
          int tempCol = currCol + colChange[newDir];
          if (M[tempRow][tempCol] >= 1) {
            M[currRow][currCol] -= 1;
            if (M[currRow][currCol] > 1) {
              info[2] = newDir;
              branches.push(info);
            }
            currRow = tempRow;
            currCol = tempCol;
          }
        }
      }
    }
    */
  }
  public static void main(String[] args) {
	  new maze();
  }
  @Override
  public void customize() {
    mheight = 41;
    mwidth = 41;
    //bh = 15;
    //bw = 15;
    showvalue = true;
  }
}