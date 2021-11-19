package keymapper;
import org.junit.Test;

import java.util.ArrayList;

public class TestKeyMaps {
    
    /**
     * We start with an array of 75 options (not including modifiers)
     * Make sure that when we remove some, we get the correct amount of unused keys
     */
    @Test public void testGetDiffs()
    {
        KeyMaps maps = new KeyMaps();
        ArrayList<String> used = new ArrayList<String>();
        used.add("KEY_F1");
        used.add("KEY_F2");
        used.add("KEY_F3");
        used.add("KEY_F4");
        used.add("KEY_F5");
        used.add("KEY_F6");
        used.add("KEY_F7");
        used.add("KEY_F8");
        used.add("KEY_F9");
        used.add("KEY_F10");
        used.add("KEY_F11");
        used.add("KEY_F12");

        // default to 75 items, we are removing 12
        maps.setUsed(used);
        assert(maps.getDiffs().size() == 63);
    }

    /**
     * Make sure the modifiers are set properly
     */
    @Test public void testGetModifiers()
    {
        KeyMaps maps = new KeyMaps();
        
        // ensure we're getting our modifiers
        assert(maps.getModifiers().length == 6);
    }

}
