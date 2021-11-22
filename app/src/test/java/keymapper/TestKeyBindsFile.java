package keymapper;
import java.util.ArrayList;

import org.junit.Test;

public class TestKeyBindsFile {
    
    /**
     * Make sure our test file returns 198 binds
     */
    @Test public void testGetBinds()
    {
        KeyBindsFile file = new KeyBindsFile(System.getProperty("user.dir") + "/src/test/resources/Custom.3.0.binds");
        assert(file.getBinds().size() == 198);
    }

    /**
     * Make sure we can submit our test file with 198 binds and receive a filled file with 355 binds
     */
    @Test public void testReplaceContents()
    {
        // start by creating a full binds file from our test
        // read key binds file from Elite Dangerous
		KeyBindsFile binds = new KeyBindsFile(System.getProperty("user.dir") + "/src/test/resources/Custom.3.0.binds");
		// create default map of keys
		KeyMaps maps = new KeyMaps();
        // determine what keys are already used
		maps.setUsed((ArrayList<String>)binds.getBinds());
		// determine what keys are available and replace them in the file
		binds.replaceContents(maps.getDiffs(), maps.getModifiers());

        // ensure the new file is full
        KeyBindsFile file2 = new KeyBindsFile(System.getProperty("user.dir") + "/src/binds/KeyMapperv1.0.binds");
        assert(file2.getBinds().size() == 865);
    }

}
