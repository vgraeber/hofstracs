public class Time {
  int mins;
  int secs;
  public Time() {
    mins = 0;
    secs = 0;
  }
  public Time(int min, int sec) {
    secs = sec % 60;
    mins = min + sec / 60;
  }
  public int totSecs() {
    return mins * 60 + secs;
  }
  public String toStr() {
    return mins + " mins and " + secs + " secs";
  }
  public void tick() {
    secs++;
    mins += secs / 60;
    secs %= 60;
  }
  public Time add(Time other) {
    int osecs = other.totSecs();
    int nsecs = secs + osecs;
    int nmins = mins + nsecs / 60;
    nsecs %= 60;
    return new Time(nmins, nsecs);
  }
  public int compare(Time other) {
    int totSecs = this.totSecs();
    int oTotSecs = other.totSecs();
    return totSecs - oTotSecs;
  }
  public static void main2() {
    Time t1 = new Time(3, 50);
    Time t2 = new HTime(1, 2, 30);
    System.out.println(t1.add(t2).toStr());
    Time[] times = new Time[4];
    times[0] = new Time(2, 30);
    times[1] = new HTime(1, 30, 25);
    times[2] = new Time(5, 45);
    times[3] = new HTime(2, 0, 0);
    Time sum = new HTime();
    for (int i = 0; i < times.length; i++) {
      sum = sum.add(times[i]);
    }
    System.out.print("total time: ");
    System.out.println(sum.toStr());
    System.out.print("can still compare HTimes: ");
    System.out.println(sum.compare(t2));
    System.out.println(new HTime(1, 0, 0).compare(new Time(60, 0)));
  }
  public static void main(String[] args) {
    Time t1 = new Time(2, 15);
    t1.tick();
    Time t2 = new Time(1, 44);
    Time t3 = t1.add(t2);
    System.out.print("t1: ");
    System.out.println(t1.toStr());
    System.out.print("t2: ");
    System.out.println(t2.toStr());
    System.out.print("sum Time: ");
    System.out.println(t3.toStr());
    System.out.print("compare t1 and t2: ");
    System.out.println(t1.compare(t2));
    System.out.print("compare t1 with equivalent value: ");
    System.out.println(t1.compare(new Time(0, 136)));
    main2();
  }
}

class HTime extends Time {
  int hrs;
  public HTime() {
    super();
    hrs = 0;
  }
  public HTime(int hr, int min, int sec) {
    super(min, sec);
    hrs = hr + mins / 60;
    mins %= 60;
  }
  public int totSecs() {
    return hrs * 3600 + mins * 60 + secs;
  }
  public String toStr() {
    return hrs + " hours, " + mins + " mins, and " + secs + " secs";
  }
  public void tick() {
    this.tick();
    hrs += mins / 60;
    mins %= 60;
  }
  public Time add(Time other) {
    int osecs = other.totSecs();
    int nsecs = secs + osecs;
    int nmins = mins + nsecs / 60;
    int nhrs = hrs + nmins / 60;
    nmins %= 60;
    nsecs %= 60;
    return new HTime(nhrs, nmins, nsecs);
  }
}