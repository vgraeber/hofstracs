class scheme1 extends NeedlemanWunsch {
  public scheme1(String A, String B) {
    super(A, B);
  }
  int score(int r, int c) {
    if (seqB.charAt(r) == seqA.charAt(c)) {
      return 1;
    } else {
      return 0;
    }
  }
  int penalty(boolean edge) {
    return 0;
  }
}
class scheme2 extends NeedlemanWunsch {
  public scheme2(String A, String B) {
    super(A, B);
  }
  int score(int r, int c) {
    if (seqB.charAt(r) == seqA.charAt(c)) {
      return 1;
    } else {
      return -1;
    }
  }
  int penalty(boolean edge) {
    return -1;
  }
}
class scheme3 extends NeedlemanWunsch {
  public scheme3(String A, String B) {
    super(A, B);
  }
  int score(int r, int c) {
    if ((seqB.charAt(r) | 32) == (seqA.charAt(c) | 32)) {
      return 3;
    } else {
      return -1;
    }
  }
  int penalty(boolean edge) {
    if (edge) {
      return 0;
    } else {
      return -2;
    }
  }
}