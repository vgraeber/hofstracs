import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

interface BijectiveMap<TA, TB> {
  void set(TA x, TB y);
  Optional<TB> get(TA x);
  Optional<TA> reverse_get(TB x);
  Optional<TB> removeKey(TA x);
  Optional<TA> removeVal(TB y);
  int size();
  static <T> Optional<T> onBehalf(Supplier<? extends T> supp) {
    return Optional.ofNullable(supp.get());
  }
  static <T> Optional<T> tryOnBehalf(Supplier<? extends T> supp) {
    Optional<T> answer = Optional.empty();
    try {
      answer = onBehalf(supp);
    } catch (Exception e) {}
    return answer;
  }
}

class Bimap<TA, TB> implements BijectiveMap<TA, TB> {
  private HashMap<TA, TB> aMap;
  private HashMap<TB, TA> bMap;
  public Bimap() {
    aMap = new HashMap<TA, TB>();
    bMap = new HashMap<TB, TA>();
  }
  public int size() {
    return aMap.size();
  }
  public Optional<TB> get(TA x) {
    return Optional.ofNullable(aMap.get(x));
  }
  public Optional<TA> reverse_get(TB y) {
    return Optional.ofNullable(bMap.get(y));
  }
  public Optional<TB> removeKey(TA x) {
    bMap.remove(get(x).orElse(null));
    return Optional.ofNullable(aMap.remove(x));
  }
  public Optional<TA> removeVal(TB y) {
    aMap.remove(reverse_get(y).orElse(null));
    return Optional.ofNullable(bMap.remove(y));
  }
  public void set(TA x, TB y) {
    if ((x == null) || (y == null)) {
      System.out.println("null set");
    } else {
      removeKey(x);
      removeVal(y);
      aMap.put(x, y);
      bMap.put(y, x);
    }
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