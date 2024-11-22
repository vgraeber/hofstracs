import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

public class PhraseTrie<VT> extends AbstTrie<List<String>, String, VT> {
	protected int key_length(List<String> key) {
		return key.size();
	}
	protected String kctAt(List<String> key, int i) {
		return key.get(i);
	}
	protected List<String> empty_key() {
		return new ArrayList<String>();
	}
	protected List<String> kt_add_kct(List<String> key, String keypart) {
		key.add(keypart);
    return key;
	}
	public String toStr(List<String> key) {
		String phrasestr = "";
		for (int i = 0; i < key_length(key); i++) {
			phrasestr += key.get(i) + " ";
		}
		return phrasestr;
	}
}