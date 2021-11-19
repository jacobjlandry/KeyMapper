// TODO Currently when we determine which keys are being used we are not taking into account modifiers. We need to also collect that info.
import java.util.ArrayList;

/**
 * KeyMaps
 * Create a map of what keys are available on a standard american keybaord
 * take a list of used keys
 * determine what keys and modifiers are available to use
 */
public class KeyMaps {
    // all possible keys
    private String[] available = {
        "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "Scrolllock", "Pause",
        "Grave", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Minus", "Equals", "Backspace", "Insert", "Home", "PageUp",
        "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "LeftBracket", "RightBracket", "BackSlash", "Delete", "End", "PageDown",
        "A", "S", "D", "F", "G", "H", "J", "K", "L", "SemiColon", "Apostrophe", "Enter",
        "Z", "X", "C", "V", "B", "N", "M", "Comma", "Period", "Slash", "LeftArrow", "DownArrow", "RightArrow", "UpArrow",
        "Space"
    };
    // all possible modifiers
    private String[] modifiers = {
        "LeftControl", "RightControl", "LeftAlt", "RightAlt", "LeftShift", "RightShift"
    };

    // used and unused maps
    private ArrayList<String> unused;
    private ArrayList<String> used;
    
    /**
     * Constructor
     */
    public KeyMaps() {
		this.unused = new ArrayList<String>();
    }

    /**
     * return the difference between the used and available data
     * @return ArrayList<String>
     */
    public ArrayList<String> getDiffs()
    {
        return this.unused;
    }

    /**
     * Get the list of modifiers
     * @return String[]
     */
    public String[] getModifiers()
    {
        return this.modifiers;
    }

    /**
     * set list of used keys
     * @param list
     */
    public void setUsed(ArrayList<String> list)
    {
        this.used = list;
        this.setDiffs();
    }

    /**
     * compare used to available and get unused keys
     */
    private void setDiffs()
    {
        // find diff between used and available => unused
		for(int x = 0; x < available.length; x++) {
			int result = -1;  
			for(int y = 0; y < this.used.size(); y++) {
				String availableKey = "Key_" + available[x];
				String usedKey = this.used.get(y);
				if(usedKey != null) {
					if(usedKey.equals(availableKey)) {
						result = y;
						break;
					}
				}
			}
	        if (result < 0)  
	            unused.add("Key_" + available[x]);
		}
    }

}
