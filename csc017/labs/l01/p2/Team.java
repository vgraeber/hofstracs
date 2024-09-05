public class Team {
  double winRate;
  int wins;
  int losses;
  int draws;
  String name;
  public Team(String teamName) {
    wins = 0;
    losses = 0;
    draws = 0;
    winRate = 0;
    name = teamName;
  }
  public void calcWinRate() {
    double totGames = 0.0 + wins + losses + draws;
    winRate = wins / totGames;
  }
  public void win() {
    wins += 1;
    calcWinRate();
  }
  public void lose() {
    losses += 1;
    calcWinRate();
  }
  public void draw() {
    draws += 1;
    calcWinRate();
  }
  public void printRecord() {
    System.out.print("W-L-D: ");
    System.out.print(wins);
    System.out.print("-");
    System.out.print(losses);
    System.out.print("-");
    System.out.println(draws);
  }
  public void play(Team other) {
    int n1 = (int)(Math.random() * 100);
    int n2 = (int)(Math.random() * 100);
    if (n1 > n2) {
      this.win();
      other.lose();
      System.out.print(this.name);
      System.out.println(" win");
    } else if (n1 < n2) {
      this.lose();
      other.win();
      System.out.print(other.name);
      System.out.println(" win");
    } else {
      this.draw();
      other.draw();
      System.out.println("It's a draw");
    }
  }
  public static void sortnames(String[] names) {
    sort(names);
    System.out.println(names);
  }
  public static void main(String[] args) {
    Team t1 = new Team("giants");
    Team t2 = new Team("jets");
    t1.lose();
    t2.win();
    t2.lose();
    t2.printRecord(); // should print "W-L: 1-1"
    t1.play(t2); // should print "giants win" or "jets win"
    System.out.println(t1.winRate); // prints .000 to 1.00
    String[] teamNames = {"Giants", "Jets", "Rams", "Patriots", "Falcons", "Steelers", "Packers", "Eagles", "Chiefs", "Bills", "Seahawks", "Cowboys", "Chargers", "Raiders", "Dolphins", "Saints", "49ers", "Broncos"};
    sortnames(teamNames);
  }
}