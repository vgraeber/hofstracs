public class maze extends mazebase {
  //0 = n, 1 = s, 2 = e, 3 = w
  static int[] dirs = {0, 1, 2, 3};
  static int[] rowChange = {-1, 1, 0, 0};
  static int[] colChange = {0, 0, 1, -1};

  public maze() {
    super();
  }
  public void shuffleDirs(int[] dirs) {
    for (int i = dirs.length - 1; i > 0; i--) {
      int j = (int)(Math.random() * (i + 1));
      int temp = dirs[j];
      dirs[j] = dirs[i];
      dirs[i] = temp;
    }
  }
  public boolean inBounds(int row, int col) {
    if (((0 < row) && (row < mwidth)) && ((0 < col) && (col < mheight))) {
      return true;
    }
    return false;
  }
  @Override
  public void digout(int row, int col) {
    // The following is a skeleton program that demonstrates the mechanics
    // needed for the completion of the program.
    // We always dig out two spaces at a time: we look two spaces ahead
    // in the direction we're trying to dig out, and if that space has
    // not already been dug out, we dig out that space as well as the
    // intermediate space.  This makes sure that there's always a wall
    // separating adjacent corridors.
    M[row][col] = 1;  // digout maze at coordinate y,x
    drawblock(row, col);  // change graphical display to reflect space dug out
    nextframe(40); // show next animation frame after 40ms delay
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
	  new maze(); // constructor of superclass will initiate everything
  }
  // other hints:  override customize to change maze parameters:
  @Override
  public void customize() {
	// ... can change mwidth, mheight, bw,bh, colors here
  }
}