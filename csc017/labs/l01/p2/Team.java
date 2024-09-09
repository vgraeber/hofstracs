import java.util.*;

interface Playable {
  void win();
  void lose();
  void printRecord();
  void play(Team other);
  double calcWinRate();
}

public class Team implements Playable{
  static String[] teamNames = {"49ers", "Bills", "Broncos", "Chargers", "Chiefs", "Cowboys", "Dolphins", "Eagles", "Falcons", "Giants", "Jets", "Packers", "Patriots", "Raiders", "Rams", "Saints", "Seahawks", "Steelers"};
  double winRate;
  int wins;
  int losses;
  int draws;
  String name;
  public Team(String teamName) {
    wins = 0;
    losses = 0;
    winRate = 0;
    name = teamName;
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
  public void printRecord() {
    System.out.printf("W-L: %d%s%d", wins, "-", losses);
  }
  public void play(Team other) {
    Random randGen = new Random();
    double randNum = randGen.nextGaussian();
    double rNum1 = randNum * (1 + this.losses) + this.winRate;
    double rNum2 = randNum * (1 + other.losses) + other.winRate;
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
  public static Team[] makeTeams(String[] teamNames) {
    Team[] teams = new Team[teamNames.length];
    for (int i = 0; i < teamNames.length; i++) {
      teams[i] = new Team(teamNames[i]);
    }
    return teams;
  }
  public static void season(Team[] teams) {
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        teams[i].play(teams[j]);
      }
    }
  }
  public static Team[] sortTeams(Team[] teams) {
    Team[] orderedTeams = teams.clone();
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        if (orderedTeams[i].winRate < orderedTeams[j].winRate) {
          Team temp = orderedTeams[i];
          orderedTeams[i] = orderedTeams[j];
          orderedTeams[j] = temp;
        }
      }
    }
    return orderedTeams;
  }
  public static void seasonResults(Team[] teams, boolean topOnly) {
    Team[] orderedTeams = sortTeams(teams);
    int longestStr = 0;
    for (int i = 0; i < teams.length; i++) {
      if (orderedTeams[i].name.length() > longestStr) {
        longestStr = orderedTeams[i].name.length();
      }
    }
    longestStr += 2;
    if (topOnly) {
      System.out.print("The team with the highest win rate this season is the ");
      System.out.println(orderedTeams[0].name);
    } else {
      for (int i = 0; i < teams.length; i++) {
        System.out.print(String.format("%-" + longestStr + "s", orderedTeams[i].name));
        System.out.printf(" W-L: %02d%s%02d", orderedTeams[i].wins, " - ", orderedTeams[i].losses);
        System.out.printf("   win rate: %#.2f%n", orderedTeams[i].winRate);
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
    season(teams);
    seasonResults(teams, true);
    seasonResults(teams, false);
    DTeam.main(args);
  }
}

class DTeam extends Team implements Playable {
  int draws;
  public DTeam(String teamName) {
    super(teamName);
    draws = 0;
  }
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
  public static DTeam[] makeTeams(String[] teamNames) {
    DTeam[] teams = new DTeam[teamNames.length];
    for (int i = 0; i < teamNames.length; i++) {
      teams[i] = new DTeam(teamNames[i]);
    }
    return teams;
  }
  public static void season(DTeam[] teams) {
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        teams[i].play(teams[j]);
      }
    }
  }
  public static DTeam[] sortTeams(DTeam[] teams) {
    DTeam[] orderedTeams = teams.clone();
    for (int i = 0; i < teams.length; i++) {
      for (int j = i + 1; j < teams.length; j++) {
        if (orderedTeams[i].winRate < orderedTeams[j].winRate) {
          DTeam temp = orderedTeams[i];
          orderedTeams[i] = orderedTeams[j];
          orderedTeams[j] = temp;
        }
      }
    }
    return orderedTeams;
  }
  public static void seasonResults(DTeam[] teams, boolean topOnly) {
    DTeam[] orderedTeams = sortTeams(teams);
    int longestStr = 0;
    for (int i = 0; i < teams.length; i++) {
      if (orderedTeams[i].name.length() > longestStr) {
        longestStr = orderedTeams[i].name.length();
      }
    }
    longestStr += 2;
    if (topOnly) {
      System.out.printf("The team with the highest win rate this season is the %s%n", orderedTeams[0].name);
    } else {
      for (int i = 0; i < teams.length; i++) {
        System.out.print(String.format("%-" + longestStr + "s", orderedTeams[i].name));
        System.out.printf(" W-L-D: %02d%s%02d%s%02d", orderedTeams[i].wins, " - ", orderedTeams[i].losses, " - ", orderedTeams[i].draws);
        System.out.printf("   win rate: %#.2f%n", orderedTeams[i].winRate);
      }
    }
  }
  public static void main(String[] args) {
    DTeam[] teams = makeTeams(teamNames);
    season(teams);
    seasonResults(teams, true);
    seasonResults(teams, false);
  }
}