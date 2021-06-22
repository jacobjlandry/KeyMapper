import java.util.ArrayList;
import java.util.Arrays;

public class Key {
	
	// prepend with Key_
	public String[] available = {
			"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "Scrolllock", "Pause",
			"Grave", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Minus", "Equals", "Backspace", "Insert", "Home", "PageUp",
			"Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "LeftBracket", "RightBracket", "BackSlash", "Delete", "End", "PageDown",
			"A", "S", "D", "F", "G", "H", "J", "K", "L", "SemiColon", "Apostrophe", "Enter",
			"Z", "X", "C", "V", "B", "N", "M", "Comma", "Period", "Slash", "LeftArrow", "DownArrow", "RightArrow", "UpArrow",
			"Space"
	};
	
	// prepend with Key_
	// goes in modifiers block
	public String[] modifiers = {
			"LeftControl", "RightControl", "LeftAlt", "RightAlt", "LeftShift", "RightShift"
	};
	
	// example layout
	//<SelectTarget_Buggy>	                                             << Action
	//  <Primary Device="Mouse" Key="Mouse_2" />                         << Primary Binding (what we want to map)
	//  <Secondary Device="Keyboard" Key="Key_A">                        << Secondary Binding (we ignore)
	//  	<Modifier Device="Keyboard" Key="Key_RightShift" />          << Modifier
	//  </Secondary>                                                     << End Modifier Block
	//</SelectTarget_Buggy>                                              << End Action Block

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
		// TODO don't put binds into used. loop through binds themselves and store unused binds so we can easily map them
		for(int x = 0; x < binds.size(); x++) {
			if(binds.get(x).key != "") {
				if(binds.get(x).modifier != null) {
					used[x] = binds.get(x).modifier + " + " + binds.get(x).key;
				} else {
					used[x] = binds.get(x).key;	
				}	
			}
		}
		
		String availableKey;
		String usedKey;
		String modifier;
		
		// find diff between used and available => unused
		for(int x = 0; x < this.available.length; x++) {
			int result = -1;  
			for(int y = 0; y < used.length; y++) {
				availableKey = "Key_" + this.available[x];
				usedKey = used[y];
				if(usedKey != null) {
					if(usedKey.equals(usedKey)) {
						result = y;
					}
				}
			}
	        if (result < 0)  
	            unused.add("Key_" + this.available[x]);
		}
		
		// search for modifier combinations as well
		for(int x = 0; x < this.modifiers.length; x++) {
			modifier = "Key_" + this.modifiers[x];
			for(int y = 0; y < this.available.length; y++) {
				availableKey = "Key_" + this.available[y];
				int result = -1;
				for(int z = 0; z < used.length; z++) {
					usedKey = used[z];
					if(usedKey != null) {
						if(usedKey.equals(modifier + " + " + availableKey)) {
							result = z;
						}
					}
					
				}
				
				if(result < 0) {
					unused.add(modifier + " + " + availableKey);
				}
			}
		}
		
		return unused;
	}
}
