import java.util.*;

interface Playable {
  void win();
  void lose();
  void printRecord();
  void play(Team other);
  double calcWinRate();
}

public class Team {
  static String[] teamNames = {"49ers", "Bills", "Broncos", "Chargers", "Chiefs", "Cowboys", "Dolphins", "Eagles", "Falcons", "Giants", "Jets", "Packers", "Patriots", "Raiders", "Rams", "Saints", "Seahawks", "Steelers"};
  static int namePadLen;
  double winRate;
  int wins;
  int losses;
  int draws;
  String name;
  public Team() {
    wins = 0;
    losses = 0;
    winRate = 0;
    name = "Default name";
  }
  public Team(String teamName) {
    wins = 0;
    losses = 0;
    winRate = 0;
    name = teamName;
  }
  public static Team[] makeTeams(String[] teamNames) {
    Team[] teams = new Team[teamNames.length];
    for (int i = 0; i < teamNames.length; i++) {
      teams[i] = new Team(teamNames[i]);
    }
    return teams;
  }
  public static int getNamePadLen(Team[] teams) {
    int namePadLen = 0;
    for (int i = 0; i < teams.length; i++) {
      if (teams[i].name.length() > namePadLen) {
        namePadLen = teams[i].name.length();
      }
    }
    return namePadLen + 2;
  }
  public double calcWinRate() {
    double totGames = 0.0 + wins + losses;
    return wins / totGames;
  }
  public void win() {
    wins += 1;
    winRate = calcWinRate();
  }
  public void lose() {
    losses += 1;
    winRate = calcWinRate();
  }
  public void play(Team other) {
    Random randGen = new Random();
    double rNum1 = randGen.nextGaussian() * (1 + this.losses) + this.winRate;
    double rNum2 = randGen.nextGaussian() * (1 + other.losses) + other.winRate;
    if (rNum1 > rNum2) {
      this.win();
      other.lose();
      //System.out.printf("%s%s%n", this.name, " win");
    } else {
      this.lose();
      other.win();
      //System.out.printf("%s%s%n", other.name, " win");
    }
  }
  public static void season(Team[] teams) {
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        teams[i].play(teams[j]);
      }
    }
  }
  public static void sortTeams(Team[] teams) {
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        if (teams[i].winRate < teams[j].winRate) {
          Team temp = teams[i];
          teams[i] = teams[j];
          teams[j] = temp;
        }
      }
    }
  }
  public void printRecord() {
    System.out.printf("%-" + namePadLen + "s%s%02d%s%02d%s%#.2f%n", name, " W-L: ", wins, " - ", losses, "   win rate: ", winRate);
  }
  public static void seasonResults(Team[] teams, boolean topOnly) {
    if (topOnly) {
      System.out.printf("The team with the highest win rate this season is the %s%n", teams[0].name);
    } else {
      for (int i = 0; i < teams.length; i++) {
        teams[i].printRecord();
      }
    }
  }
  public static void main(String[] args) {
    /*
    Team t1 = new Team("giants");
    Team t2 = new Team("jets");
    t1.lose();
    t2.win();
    t2.lose();
    t2.printRecord();
    t1.play(t2);
    System.out.println(t1.winRate);
    */
    Team[] teams = makeTeams(teamNames);
    namePadLen = getNamePadLen(teams);
    season(teams);
    sortTeams(teams);
    seasonResults(teams, true);
    seasonResults(teams, false);
    DTeam.main2();
  }
}

class DTeam extends Team {
  int draws;
  public DTeam() {
    super();
    draws = 0;
  }
  public DTeam(String teamName) {
    super(teamName);
    draws = 0;
  }
  public static DTeam[] makeTeams(String[] teamNames) {
    DTeam[] teams = new DTeam[teamNames.length];
    for (int i = 0; i < teamNames.length; i++) {
      teams[i] = new DTeam(teamNames[i]);
    }
    return teams;
  }
  @Override
  public double calcWinRate() {
    double totGames = 0.0 + wins + losses + draws;
    return wins / totGames;
  }
  public void draw() {
    draws += 1;
    winRate = calcWinRate();
  }
  public void play(DTeam other) {
    Random randGen = new Random();
    double rNum1 = randGen.nextGaussian() * (1 + this.losses) + this.winRate;
    double rNum2 = randGen.nextGaussian() * (1 + other.losses) + other.winRate;
    if (Math.abs(rNum1 - rNum2) < .5) {
      this.draw();
      other.draw();
    } else if (rNum1 > rNum2) {
      this.win();
      other.lose();
      //System.out.printf("%s%s%n", this.name, " win");
    } else {
      this.lose();
      other.win();
      //System.out.printf("%s%s%n", other.name, " win");
    }
  }
  public static void season(DTeam[] teams) {
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        teams[i].play(teams[j]);
      }
    }
  }
  @Override
  public void printRecord() {
    System.out.printf("%-" + namePadLen + "s%s%02d%s%02d%s%02d%s%#.2f%n", name, " W-L-D: ", wins, " - ", losses, " - ", draws, "   win rate: ", winRate);
  }
  public static void seasonResults(DTeam[] teams, boolean topOnly) {
    if (topOnly) {
      System.out.printf("The team with the highest win rate this season is the %s%n", teams[0].name);
    } else {
      for (int i = 0; i < teams.length; i++) {
        teams[i].printRecord();
      }
    }
  }
  public static void main2() {
    DTeam[] teams = makeTeams(teamNames);
    season(teams);
    sortTeams(teams);
    seasonResults(teams, true);
    seasonResults(teams, false);
  }
}