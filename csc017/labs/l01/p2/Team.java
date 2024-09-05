import java.util.*;

public class Team {
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
  public void calcWinRate() {
    double totGames = 0.0 + wins + losses;
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
  public void printRecord() {
    System.out.print("W-L-D: ");
    System.out.print(wins);
    System.out.print("-");
    System.out.print(losses);
  }
  public void play(Team other) {
    Random randGen = new Random();
    double randNum = randGen.nextGaussian();
    double rNum1 = randNum * (1 + this.losses) + this.winRate;
    double rNum2 = randNum * (1 + other.losses) + other.winRate;
    if (rNum1 > rNum2) {
      this.win();
      other.lose();
      //System.out.print(this.name);
      //System.out.println(" win");
    } else {
      this.lose();
      other.win();
      //System.out.print(other.name);
      //System.out.println(" win");
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
  public static void seasonResults(Team[] teams) {
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
    for (int i = 0; i < teams.length; i++) {
      System.out.print(orderedTeams[i].name);
      System.out.print(" W-L: ");
      System.out.print(orderedTeams[i].wins);
      System.out.print("-");
      System.out.print(orderedTeams[i].losses);
      System.out.print(" win rate: ");
      System.out.println(orderedTeams[i].winRate);
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
    String[] teamNames = {"49ers", "Bills", "Broncos", "Chargers", "Chiefs", "Cowboys", "Dolphins", "Eagles", "Falcons", "Giants", "Jets", "Packers", "Patriots", "Raiders", "Rams", "Saints", "Seahawks", "Steelers"};
    Team[] teams = makeTeams(teamNames);
    season(teams);
    seasonResults(teams);
  }
}