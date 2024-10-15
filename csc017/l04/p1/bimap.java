import java.util.HashMap;

interface Twowaymap<TA, TB> {
  void set(TA x, TB y);
  TB get(TA x);
  TA reverse_get(TB x);
  TB removeKey(TA x);
  TA removeVal(TB y);
  int size();
}

class Bimap<TA, TB> implements Twowaymap<TA, TB> {
  private HashMap<TA, TB> aMap;
  private HashMap<TB, TA> bMap;
  public Bimap() {
    aMap = new HashMap<TA, TB>();
    bMap = new HashMap<TB, TA>();
  }
  public int size() {
    return aMap.size();
  }
  public TB get(TA x) {
    return aMap.get(x);
  }
  public TA reverse_get(TB y) {
    return bMap.get(y);
  }
  public TB removeKey(TA x) {
    bMap.remove(get(x));
    return aMap.remove(x);
  }
  public TA removeVal(TB y) {
    aMap.remove(reverse_get(y));
    return bMap.remove(y);
  }
  public void set(TA x, TB y) {
    if ((x == null) || (y == null)) {
      throw new RuntimeException("null set");
    }
    removeKey(x);
    removeVal(y);
    aMap.put(x, y);
    bMap.put(y, x);
  }
  public static void main(String[] args) {
	  Bimap<String, Double> GP = new Bimap<String, Double>();
	  String[] Grades = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
	  Double[] Points = {4.0, 3.7, 3.3, 3.0, 2.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.0};
	  for(int i = 0; i < Grades.length; i++) {
      GP.set(Grades[i], Points[i]);
    }
	  for(String g : Grades) {
      System.out.println(GP.get(g));
    }
	  for(Double p : Points) {
      System.out.println(GP.reverse_get(p));	
    }
    GP.set("A-", 3.75);
    GP.set("B+", 3.25);
    GP.removeVal(1.3);
    System.out.println(GP.get("A-"));
    System.out.println(GP.reverse_get(3.75));
    System.out.println(GP.get("D+"));
    System.out.println(GP.reverse_get(1.3));
	  System.out.println("3.7: " + GP.reverse_get(3.7));
    GP.set("A+", 4.0);
	  System.out.println(GP.size());
  }
}