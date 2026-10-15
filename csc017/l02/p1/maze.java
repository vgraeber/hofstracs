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
    nextframe(40);
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
        nextframe(40);
        digout(newRow, newCol);
      }
    }
  }
  public static void main(String[] args) {
	  new maze();
  }
  @Override
  public void customize() {
  }
}