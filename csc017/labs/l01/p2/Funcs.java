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
    if (strArr.length == 0) {
      return null;
    }
    String smallest = strArr[0];
    for (int i = 0; i < strArr.length; i++) {
      if (smallest.compareTo(strArr[i]) > 0) {
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
  public static boolean duplicate(String[] strArr) {
    for (int i = 0; i < strArr.length; i++) {
      for (int j = i + 1; j < strArr.length; j++) {
        if (strArr[i].equals(strArr[j])) {
          return true;
        }
      }
    }
    return false;
  }
  public static void printQ1(String testStr, String[] inputStrs) {
    System.out.print("the string \"");
    System.out.print(testStr);
    System.out.print("\" is ");
    if (!inArr(testStr, inputStrs)) {
      System.out.print("not ");
    }
    System.out.print("in the array of strings ");
    for (int i = 0; i < inputStrs.length - 1; i++) {
      System.out.print("\"");
      System.out.print(inputStrs[i]);
      System.out.print("\", ");
    }
    System.out.print("and \"");
    System.out.print(inputStrs[inputStrs.length - 1]);
    System.out.println("\"");
  }
  public static void printQ2A(int[] inputInts) {
    System.out.print("the sum of the integers ");
    for (int i = 0; i < inputInts.length - 1; i++) {
      System.out.print(inputInts[i]);
      System.out.print(", ");
    }
    System.out.print("and ");
    System.out.print(inputInts[inputInts.length - 1]);
    System.out.print(" is ");
    System.out.println(sum(inputInts));
  }
  public static void printQ2B(String[] inputStrs) {
    System.out.print("the smallest string in the array of strings ");
    for (int i = 0; i < inputStrs.length - 1; i++) {
      System.out.print("\"");
      System.out.print(inputStrs[i]);
      System.out.print("\", ");
    }
    System.out.print("and \"");
    System.out.print(inputStrs[inputStrs.length - 1]);
    System.out.print("\" is \"");
    System.out.print(smallest(inputStrs));
    System.out.println("\"");
  }
  public static void printQ3A(String[] inputStrs) {
    System.out.print("the array of strings ");
    for (int i = 0; i < inputStrs.length - 1; i++) {
      System.out.print("\"");
      System.out.print(inputStrs[i]);
      System.out.print("\", ");
    }
    System.out.print("and \"");
    System.out.print(inputStrs[inputStrs.length - 1]);
    System.out.print("\" in reverse is ");
    for (int i = 0; i < inputStrs.length - 1; i++) {
      System.out.print("\"");
      System.out.print(reverse(inputStrs[i]));
      System.out.print("\", ");
    }
    System.out.print("and \"");
    System.out.print(reverse(inputStrs[inputStrs.length - 1]));
    System.out.println("\"");
  }
  public static void printQ3B(String[] inputStrs) {
    System.out.print("the palindromes in the array of strings ");
    for (int i = 0; i < inputStrs.length - 1; i++) {
      System.out.print("\"");
      System.out.print(inputStrs[i]);
      System.out.print("\", ");
    }
    System.out.print("and \"");
    System.out.print(inputStrs[inputStrs.length - 1]);
    System.out.print("\"");
    int palindromes = 0;
    for (int i = 0; i < inputStrs.length; i++) {
      if (palindrome(inputStrs[i])) {
        palindromes += 1;
      }
    }
    System.out.print(" are ");
    if (palindromes == 0) {
      System.out.println(" nonexistent");
    } else if (palindromes == 1) {
      for (int i = 0; i < inputStrs.length; i++) {
        if (palindrome(inputStrs[i])) {
          System.out.print("\"");
          System.out.print(inputStrs[i]);
          System.out.println("\"");
        }
      }
    } else {
      int i = 0;
      while (palindromes > 0) {
        if (palindrome(inputStrs[i])) {
          System.out.print("\"");
          System.out.print(inputStrs[i]);
          System.out.print("\"");
          palindromes -= 1;
          if (palindromes > 0) {
            System.out.print(", ");
          }
          if (palindromes == 1) {
            System.out.print("and ");
          }
        }
        i += 1;
      }
      System.out.println();
    }
  }
  public static void printQ4(String[] inputStrs) {
    System.out.print("the array of strings ");
    for (int i = 0; i < inputStrs.length - 1; i++) {
      System.out.print("\"");
      System.out.print(inputStrs[i]);
      System.out.print("\", ");
    }
    System.out.print("and \"");
    System.out.print(inputStrs[inputStrs.length - 1]);
    System.out.print("\"");
    if (duplicate(inputStrs)) {
      System.out.println(" contains duplicates");
    } else {
      System.out.println(" does not contain duplicates");
    }
  }
  public static void main(String[] args) {
    String[] inputStrs = {"a", "abba", "dancing", "queen", "queen", "bohemian", "raphsody"};
    int[] inputInts = {1, 4, 7, 5, 5, 8, 8};
    String testStr = "music";
    printQ1(testStr, inputStrs);
    printQ2A(inputInts);
    printQ2B(inputStrs);
    printQ3A(inputStrs);
    printQ3B(inputStrs);
    printQ4(inputStrs);
  }
}