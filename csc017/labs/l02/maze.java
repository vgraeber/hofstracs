public class maze extends mazebase {
  //0 = n, 1 = s, 2 = e, 3 = w
  static int[] dirs = {0, 1, 2, 3};
  static int[] rowChange = {-1, 1, 0, 0};
  static int[] colChange = {0, 0, 1, -1};
  /*public maze() {
    super();
  }*/
  public void shuffleDirs(int[] dirs) {
    for (int i = dirs.length - 1; i > 0; i--) {
      int j = (int)(Math.random() * (i + 1));
      int temp = dirs[j];
      dirs[j] = dirs[i];
      dirs[i] = temp;
    }
  }
  public boolean inBounds(int row, int col) {
    if (((0 < row) && (row < mwidth - 1)) && ((0 < col) && (col < mheight - 1))) {
      return true;
    }
    return false;
  }
  @Override
  public void digout(int row, int col) {
    M[row][col] = 1;
    drawblock(row, col);
    nextframe(40);
    shuffleDirs(dirs);
    int[] currDirs = dirs.clone();
    for (int i = 0; i < currDirs.length; i++) {
      int dir = currDirs[i];
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
  public static void main(String[] av) {
	  new maze();
  }
  // other hints:  override customize to change maze parameters:
  @Override
  public void customize() {
    mwidth = 41;
    mheight = 41;
  }
}