abstract class NeedlemanWunsch {
  String seqA = ".";
  String seqB = ".";
  long[][] matrix;
  boolean[][][] traceback;
  long alignmentScore;
  String alignA = "";
  String alignB = "";
  String aligns = "";
  NeedlemanWunsch(String A, String B) {
    seqA += A;
    seqB += B;
    matrix = new long[seqB.length()][seqA.length()];
    traceback = new boolean[seqB.length()][seqA.length()][3];
    fillMatrix();
    doTraceback();
  }
  void fillMatrix() {
    matrix[0][0] = 0;
    int numRows = seqB.length();
    int numCols = seqA.length();
    for (int c = 1; c < numCols; c++) {
      traceback[0][c][2] = true;
      matrix[0][c] = matrix[0][c - 1] + penalty(true);
    }
    for (int r = 1; r < numRows; r++) {
      traceback[r][0][1] = true;
      matrix[r][0] = matrix[r - 1][0] + penalty(true);
    }
    for (int r = 1; r < (numRows - 1); r++) {
      for (int c = 1; c < (numCols - 1); c++) {
        long diag = matrix[r - 1][c - 1] + score(r, c);
        long up = matrix[r - 1][c] + penalty(false);
        long left = matrix[r][c - 1] + penalty(false);
        if ((diag >= up) && (diag >= left)) {
          traceback[r][c][0] = true;
        }
        if ((up >= diag) && (up >= left)) {
          traceback[r][c][1] = true;
        }
        if ((left >= diag) && (left >= up)) {
          traceback[r][c][2] = true;
        }
        matrix[r][c] = Math.max(diag, Math.max(up, left));
      }
    }
    for (int r = 1; r < (numRows - 1); r++) {
      long diag = matrix[r - 1][numCols - 2] + score(r, numCols - 1);
      long up = matrix[r - 1][numCols - 1] + penalty(true);
      long left = matrix[r][numCols - 2] + penalty(true);
      if ((diag >= up) && (diag >= left)) {
        traceback[r][numCols - 1][0] = true;
      }
      if ((up >= diag) && (up >= left)) {
        traceback[r][numCols - 1][1] = true;
      }
      if ((left >= diag) && (left >= up)) {
        traceback[r][numCols - 1][2] = true;
      }
      matrix[r][numCols - 1] = Math.max(diag, Math.max(up, left));
    }
    for (int c = 1; c < (numCols - 1); c++) {
      long diag = matrix[numRows - 2][c - 1] + score(numRows - 1, c);
      long up = matrix[numRows - 2][c] + penalty(true);
      long left = matrix[numRows - 1][c - 1] + penalty(true);
      if ((diag >= up) && (diag >= left)) {
        traceback[numRows - 1][c][0] = true;
      }
      if ((up >= diag) && (up >= left)) {
        traceback[numRows - 1][c][1] = true;
      }
      if ((left >= diag) && (left >= up)) {
        traceback[numRows - 1][c][2] = true;
      }
      matrix[numRows - 1][c] = Math.max(diag, Math.max(up, left));
    }
    long diag = matrix[numRows - 2][numCols - 2] + score(numRows - 1, numCols - 1);
    long up = matrix[numRows - 2][numCols - 1] + penalty(true);
    long left = matrix[numRows - 1][numCols - 2] + penalty(true);
    if ((diag >= up) && (diag >= left)) {
      traceback[numRows - 1][numCols - 1][0] = true;
    }
    if ((up >= diag) && (up >= left)) {
      traceback[numRows - 1][numCols - 1][1] = true;
    }
    if ((left >= diag) && (left >= up)) {
      traceback[numRows - 1][numCols - 1][2] = true;
    }
    matrix[numRows - 1][numCols - 1] = Math.max(diag, Math.max(up, left));
    alignmentScore = matrix[numRows - 1][numCols - 1];
  }
  void doTraceback() {
    int row = seqB.length() - 1;
    int col = seqA.length() - 1;
    while ((row > 0) || (col > 0)) {
      if (traceback[row][col][0]) {
        alignA = seqA.charAt(col) + alignA;
        alignB = seqB.charAt(row) + alignB;
        row--;
        col--;
      } else if (traceback[row][col][1]) {
        alignA = '-' + alignA;
        alignB = seqB.charAt(row) + alignB;
        row--;
      } else if (traceback[row][col][2]) {
        alignA = seqA.charAt(col) + alignA;
        alignB = '-' + alignB;
        col--;
      }
      if ((alignA.charAt(0) | 32) == (alignB.charAt(0) | 32)) {
        aligns = '|' + aligns;
      } else {
        aligns = ' ' + aligns;
      }
    }
  }
  public static void printmatrix(String A, String B, int[][] M) {
    if ((A == null) || (B == null) || (M == null) || (M[0] == null)) {
      return;
    }
    if ((A.length() != M[0].length) || (B.length() != M.length)) {
      return;
    }
    int rows = B.length();
    int cols = A.length();
    System.out.print("    ");
    for(int i = 0; i < cols; i++) {
      System.out.printf(" %2s ", A.charAt(i));
    }
    System.out.println();
    System.out.println();
    for(int i = 0; i < rows; i++) {
      System.out.print("   " + B.charAt(i));
      for(int k = 0; k < cols; k++) {
          System.out.printf(" %2d ", M[i][k]);
      }
      System.out.println();
      System.out.println();
    }
  }
  public void printAlignment() {
    if (matrix == null) {
      return;
    }
    System.out.println(alignA);
    System.out.println(aligns);
    System.out.println(alignB);
    System.out.println("Alignment score: " + alignmentScore);
  }
  abstract int score(int i, int k);
  abstract int penalty(boolean edge);
}