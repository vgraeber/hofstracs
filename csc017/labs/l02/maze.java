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
  @Override
  public void solve() {
    int[] currPos = {1, 1};
    int[] endPos = {mheight - 2, mwidth - 2};
    digout(endPos[0], endPos[1]);
    int[] dirs = {0, 1, 2, 3};
    drawdot(currPos[0], currPos[1]);
    for (int row = 1; row < mheight - 1; row++) {
      for (int col = 1; col < mwidth - 1; col++) {
        int potDirs = 0;
        for (int dir = 0; dir < dirs.length; dir++) {
          int tempRow = row + rowChange[dir];
          int tempCol = col + colChange[dir];
          if (M[tempRow][tempCol] == 1) {
            potDirs += 1;
          }
        }
        if ((potDirs > 2) && (M[row][col] == 1)) {
          potDirs -= 1;
          M[row][col] = potDirs;
          drawblock(row, col);
        }
        nextframe();
      }
    }
  }
  public static void main(String[] args) {
	  new maze();
  }
  @Override
  public void customize() {
    mheight = 51;
    mwidth = 51;
    bh = 15;
    bw = 15;
    showvalue = true;
  }
}