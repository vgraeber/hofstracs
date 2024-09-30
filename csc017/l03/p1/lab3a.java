import java.util.Comparator;

class person implements Comparable<person> {
	String name;
	int age;
	public person(String n, int a) {
		name = n;
		age = a;
	}
	public int compareTo(person other) {
		if ((this.age - other.age) == 0) {
			return this.name.compareTo(other.name);
		} else {
			return (this.age - other.age);
		}
	}
}

class team implements Comparable<team> {
	protected int wins = 0;
	protected int losses = 0;
	public void win() {
		wins++;
	}
	public void lose() {
		losses++;
	}
	public void play(team other) {
		if (Math.random() < 0.5) {
			this.win();  
			other.lose();
			//System.out.println("I win, you suck");
		} else {
			other.win();
			this.lose();
			//System.out.println("You win, but you still suck");
		}
	}
	public double winning_percentage() {
		double totGames = 0.0 + wins + losses;
		if (totGames == 0) {
			return 0.0;
		} else {
			return wins / totGames;
		}
	}
	public int compareTo(team other) {
		int winRate = (int)((this.winning_percentage() + .5) * 1000);
		int oWinRate = (int)((other.winning_percentage() + .5) * 1000);
		return (winRate - oWinRate);
	}
}

// 3. Modify the following class so that it can also use a Comparator instead
// of Comparable.  This allows us to change the compare method easily using
// a lambda expression.  That is, add a
// Comparator<T> cmp = (x,y) -> x.compareTo(y);
// and a set_comparator method that allows the comparator to be changed.
// Modify the code inside the largest and sorted functions to use the
// comparator (cmp.compare instead of .compareTo).
// When finished, uncomment the three indicated lines in main to test.
// Leave the very last line commented out.

class SomeRoutines<T extends Comparable<? super T>> {
	T[] A;
	Comparator<T> cmp = (x, y) -> x.compareTo(y);
	public SomeRoutines(T[] A) {
		this.A = A;
	}
	public void set_comparator(Comparator<T> newcmp) {
		if (newcmp != null) {
			cmp = newcmp;
		}
	}
	public int largest() {
		if ((A == null) || (A.length == 0)) {
			return -1;
		}
		int maxi = 0;
		for (int i = 1; i < A.length; i++) {
			if (cmp.compare(A[maxi], A[i]) < 0) {
				maxi = i;
			}
		}
		return maxi;
	}
	public boolean sorted() {
		for (int i = 0; i < (A.length - 1); i++) {
			if (cmp.compare(A[i], A[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}
}

/* 4. The following subclass of team represents teams that can tie as
   well as win and lose.  DO NOT TOUCH THIS CLASS!  Do not add or
   edit this class.  This class is PERFECT!  Also, uncomment the last
   line in main but DO NOT CHANGE IT.  It must still work.

   You may, however, discover that you might need to make some minor
   modifications to other parts of program in order to get this class,
   and main, to compile and work.  The changes should be very small.

   Again, do not change this class and only uncomment the last line in
   main.  Change minimally other parts of your program to get it to work.
*/
class wlt_team extends team {  // win-lose-tie team : DO NOT TOUCH!!!
    protected int ties=0;
    public void tie() { ties++; }

    @Override
    public double winning_percentage() {
				int totalgames = wins+losses+ties;
				if (totalgames<1) return 0.0;
				else return (wins+0.5*ties)/totalgames;
    }

    // The following function is not an "OVERRIDE" but an "OVERLOAD"
    // because it takes a different type of argument than team::play.
    // This makes it an entirely new function, might as well call it play2:

    // @Override   // uncommenting this directive will result in error
    public void play(wlt_team other) {
				double r = Math.random();
				if (r < 0.475) {
						this.win();
						other.lose();
						System.out.println("I win, you suck");
				}
				else if (r >= .525) {
						other.win();
						this.lose();
						System.out.println("You win, but you still suck");
				}
				else {
						this.tie();
						other.tie();
						System.out.println("Everybody sucks");
				}
    }// a team that can tie can only play another team that can tie
}//wlt_team  copyright (c) 2024 Chuck Liang.    CHANGE AND I'LL SUE!

public class lab3a {
  public static void main(String[] args) {
		team mets = new team();
		team yankees = new team();
		for (int i = 0; i < 162; i++) mets.play(yankees);
		System.out.printf("Mets: %.3f\n", mets.winning_percentage());
		System.out.printf("Yankees: %.3f\n", yankees.winning_percentage());

		wlt_team jets = new wlt_team();
		wlt_team giants = new wlt_team();
		for (int i = 0; i < 17; i++) jets.play(giants);
		System.out.printf("Jets: %.3f\n", jets.winning_percentage());
		System.out.printf("Giants: %.3f\n", giants.winning_percentage());
		team[] MLB = {mets, yankees};
		wlt_team[] NFL = {jets, giants};

		// add code to test your person class.  Create an array of persons
		// and instantiate the SomeRoutines class, change the comparator
		// of the SomeRoutines object, etc.

		SomeRoutines<team> sr = new SomeRoutines<team>(MLB);
		sr.set_comparator( (a, b) -> b.compareTo(a) );
		System.out.println("MLB sorted in increasing order: " + sr.sorted());

		// uncomment the following line after part 4, but DO NOT CHANGE IT:
		SomeRoutines<wlt_team> sr2 = new SomeRoutines<wlt_team>(NFL);
  }
}
