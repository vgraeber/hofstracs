abstract class student  {
  public String name;
  protected double gpa;
  protected int credits;
  public abstract boolean probation();
  public abstract int tuition();
  public abstract String toString();
  public void stu(String n) {
    name = n;
    gpa = ((int) (Math.random() * 401)) / 100.0;
    credits = 3 + (int) (Math.random() * 15);
  }
}
class grad extends student {
  public String thesistopic;
  static String[] topics = {"p equals np", "halting problem", "distributed os", "real time fault tolerance", "compiler design", "cryptography", "machine learning"};
  public grad(String n) {
    stu(n);
    thesistopic = topics[(int) (Math.random() * topics.length)];
  }
  public boolean probation() {
    if (this.gpa < 3.0) {
      return true;
    } else {
      return false;
    }
  }
  public int tuition() {
    return (1800 * this.credits);
  }
  public String toString() {
    return ("Graduate student " + this.name + " is doing their thesis on " + this.thesistopic + " and has a GPA of " + this.gpa);
  }
}
class undergrad extends student {
  public String major;
  static String[] mjs= {"comp sci", "poly sci", "engineering", "math", "physics","biology","psychology", "history", "leisure studies"};
  public undergrad(String n) {
    stu(n);
    major = mjs[(int) (Math.random() * mjs.length)];
  }
  public boolean probation() {
    if (this.gpa < 2.0) {
      return true;
    } else {
      return false;
    }
  }
  public int tuition() {
    return (1500 * this.credits);
  }
  public String toString() {
    return ("Undergraduate student " + this.name + " is majoring in " + this.major + " and has a GPA of " + this.gpa);
  }
}
class cw01 {
  public static void main(String[] argv) {
    String[] names = {"Kevin","Emilia","Artem","Brandon","Vivian","Manuel","Nicko","Christopher","Dale","Ryan","Joseph","Danny","Irene","Darren","William","Mary","Larz","Narx"};
    student[] roster = new student[names.length];
    for(int i = 0; i < roster.length - 1; i += 2) {
      roster[i] = new grad(names[i]);
      roster[i+1] = new undergrad(names[i+1]);
    }
    double total = 0;
    for(student s : roster) {
      total += s.tuition();
    }
    System.out.println("total tution is " + total);
    for(student s : roster) {
      if (s.probation()) {
        System.out.println(s.toString());
      }
    }
  }
}