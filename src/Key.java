import java.util.ArrayList;
import java.util.Arrays;

public class Key {
	
	public String[] available = {
			"space", "left_arrow", "down_arrow", "right_arrow", "up_arrow", "z", "x", "c", "v", "b", "n", "m", ",", ".", "/", "a", "s", "d", "f", "g",
			"h", "j", "k", "l", ";", "'", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "[", "]", "\\", "`", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "0", "-", "=", "ins", "home", "pgup", "del", "end", "pgdn"
	};
	public String[] modifiers = {
			"left_ctrl", "right_ctrl", "left_alt", "right_alt", "left_shift", "right_shift"
	};

	// list of available keys and modifiers that can be mapped
	// use both keyboard and HOTAS keys
	public Key() {
		// TODO Auto-generated constructor stub
		Arrays.sort(this.available);
	}
	
	// accept binds and find unused keys
	public ArrayList<String> findUnused(ArrayList<Binding> binds) {
		ArrayList<String> unused = new ArrayList<String>();
		String[] used = new String[binds.size()];
		
		// build list of just the keys we have used
		for(int x = 0; x < binds.size(); x++) {
			used[x] = binds.get(x).key;
		}
		
		// find diff between used and available => unused
		for(int x = 0; x < this.available.length; x++) {
			int result = -1;  
			for(int y = 0; y < used.length; y++) {
				if(used[y] == this.available[x]) {
					result = y;
				}
			}
	        if (result < 0)  
	            unused.add(this.available[x]);
		}
		
		// search for modifier combinations as well
		for(int x = 0; x < this.modifiers.length; x++) {
			for(int y = 0; y < this.available.length; y++) {
				int result = -1;
				for(int z = 0; z < used.length; z++) {
					if(used[z] == this.modifiers[x] + this.available[y]) {
						result = z;
					}
				}
				
				if(result < 0) {
					unused.add(this.modifiers[x] + this.available[y]);
				}
			}
		}
		
		return unused;
	}
}
