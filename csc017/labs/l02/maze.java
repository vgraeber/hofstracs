import java.util.ArrayList;
import java.util.Arrays;

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
          nextframe();
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
  public boolean notPrevSpace(int row, int col, ArrayList<int[]> solution) {
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
    int endCol = mwidth - 2;
    countMaze();
    int[] dirs = {0, 1, 2, 3};
    int[][] Maze = new int[M.length][];
    for (int i = 0; i < M.length; i++) {
      Maze[i] = Arrays.copyOf(M[i], M.length);
    }
    ArrayList<int[]> solution = new ArrayList<int[]>();
    while (!solved(currRow, currCol, endRow, endCol)) {
      boolean moved = true;
      while (!solved(currRow, currCol, endRow, endCol) && moved) {
        for (int dir = 0; dir < dirs.length; dir++) {
          int newRow = currRow + rowChange[dir];
          int newCol = currCol + colChange[dir];
          if ((Maze[newRow][newCol] > 0) && notPrevSpace(newRow, newCol, solution)) {
            Maze[currRow][currCol] -= 1;
            int[] info = {currRow, currCol, Maze[currRow][currCol]};
            solution.add(info);
            drawdot(currRow, currCol);
            nextframe(40);
            currRow = newRow;
            currCol = newCol;
            break;
          } else if (dir == (dirs.length - 1)) {
            Maze[currRow][currCol] -= 1;
            moved =  false;
            drawdot(currRow, currCol);
            nextframe(80);
            drawblock(currRow, currCol);
            nextframe(40);
          }
        }
      }
      if (!solved(currRow, currCol, endRow, endCol)) {
        while (solution.get(solution.size() - 1)[2] == 0) {
          drawblock(solution.get(solution.size() - 1)[0], solution.get(solution.size() - 1)[1]);
          nextframe(40);
          solution.remove(solution.size() - 1);
        }
        currRow = solution.get(solution.size() - 1)[0];
        currCol = solution.get(solution.size() - 1)[1];
        solution.remove(solution.size() - 1);
      }
    }
    drawdot(currRow, currCol);
    nextframe();
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