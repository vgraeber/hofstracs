public class Funcs {
  public static boolean inArr(String str, String[] strArr) {
    for (int i = 0; i < strArr.length; i++) {
      if (strArr[i].equals(str)) {
        return true;
      }
    }
    return false;
  }
  public static int sum(int[] intArr) {
    int sum = 0;
    for (int i = 0; i < intArr.length; i++) {
      sum += intArr[i];
    }
    return sum;
  }
  public static String smallest(String[] strArr) {
    String smallest = null;
    for (int i = 0; i < strArr.length; i++) {
      if (smallest.compareTo(strArr[i]) < 0) {
        smallest = strArr[i];
      }
    }
    return smallest;
  }
  public static String reverse(String str) {
    String rStr = "";
    for (int i = 0; i < str.length(); i++) {
      rStr = str.charAt(i) + rStr;
    }
    return rStr;
  }
  public static boolean palindrome(String str) {
    String rStr = reverse(str);
    if (str.equals(rStr)) {
      return true;
    }
    return false;
  }
  public static boolean duplicates(String[] strArr) {
    for (int i = 0; i < strArr.length; i++) {
      for (int j = i = 1; j < strArr.length; j++) {
        if (strArr[i].equals(strArr[j])) {
          return true;
        }
      }
    }
    return false;
  }
  public static void main(String[] args) {
    //demo funcs here
  }
}