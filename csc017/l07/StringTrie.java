import java.util.stream.Stream;

public class StringTrie<VT> extends AbstTrie<String, Character, VT> {
	protected int key_length(String key) {
		return key.length();
	}
	protected Character kctAt(String key, int i) {
		return key.charAt(i);
	}
	protected String empty_key() {
		return "";
	}
	protected String kt_add_kct(String key, Character keypart) {
		return (key + keypart);
	}
	public static void main(String[] args) {
		var GPA = new StringTrie<Double>();
		String[] Roster = {"Alex", "Tyrone", "Alexi", "Alexander", "Alexandra", "Al", "Tyler"};
		for(String n : Roster) {
	    GPA.set(n, ((int) (Math.random() * 401)) / 100.0);
		}
		GPA.remove("Alexi");
		GPA.set("Alexander", 3.5);
		System.out.println("size: " + GPA.size());
		for(String n : Roster) {
	    System.out.println(n + " has a GPA of " + GPA.get(n));
		}
		GPA.set("Alexi", 1.0);
		GPA.stream("Alex", 2).forEach(System.out :: println);
		GPA.remove("Alexander");
		GPA.remove("Alexandra");
		System.out.println("size: " + GPA.size());
		System.out.println("load factor: " + GPA.load_factor());
		int cleaned = GPA.cleanup();
		System.out.println("cleaned: " + cleaned);
		System.out.println("load factor: " + GPA.load_factor());
	}
}