import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * KeyMaps
 * Create a map of what keys are available on a standard american keybaord
 * take a list of used keys
 * determine what keys and modifiers are available to use
 */
public class KeyMaps {
    // all possible keys
    private String[] available = {
        "KEY_F1", "KEY_F2", "KEY_F3", "KEY_F4", "KEY_F5", "KEY_F6", "KEY_F7", "KEY_F8", "KEY_F9", "KEY_F10", "KEY_F11", "KEY_F12", "KEY_Scrolllock", 
        "KEY_Pause", "KEY_Grave", "KEY_1", "KEY_2", "KEY_3", "KEY_4", "KEY_5", "KEY_6", "KEY_7", "KEY_8", "KEY_9", "KEY_0", "KEY_Minus", "KEY_Equals", 
        "KEY_Backspace", "KEY_Insert", "KEY_Home", "KEY_PageUp", "KEY_Tab", "KEY_Q", "KEY_W", "KEY_E", "KEY_R", "KEY_T", "KEY_Y", "KEY_U", "KEY_I", 
        "KEY_O", "KEY_P", "KEY_LeftBracket", "KEY_RightBracket", "KEY_BackSlash", "KEY_Delete", "KEY_End", "KEY_PageDown", "KEY_A", "KEY_S", "KEY_D", 
        "KEY_F", "KEY_G", "KEY_H", "KEY_J", "KEY_K", "KEY_L", "KEY_SemiColon", "KEY_Apostrophe", "KEY_Enter", "KEY_Z", "KEY_X", "KEY_C", "KEY_V", "KEY_B",
        "KEY_N", "KEY_M", "KEY_Comma", "KEY_Period", "KEY_Slash", "KEY_LeftArrow", "KEY_DownArrow", "KEY_RightArrow", "KEY_UpArrow", "KEY_Space"
    };
    // all possible modifiers
    private String[] modifiers = {
        "KEY_LeftControl", "KEY_RightControl", "KEY_LeftAlt", "KEY_RightAlt", "KEY_LeftShift", "KEY_RightShift"
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
        var allKeys = new HashSet<String>(Arrays.asList(available));
        var usedKeys = new HashSet<String>(used);
        allKeys.removeAll(usedKeys);

        this.unused = new ArrayList<String>(allKeys);
    }

}
