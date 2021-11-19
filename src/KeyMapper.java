import java.util.ArrayList;

public class KeyMapper {

	public static void main(String[] args) {
		// TODO Tests
		// TODO allow input file
		// TODO add UI

		// read key binds file from Elite Dangerous
		KeyBindsFile binds = new KeyBindsFile("Custom.3.0.binds");
		// create default map of keys
		KeyMaps maps = new KeyMaps();

		// determine what keys are already used
		maps.setUsed((ArrayList<String>) binds.getBinds());
		
		// determine what keys are available and replace them in the file
		binds.replaceContents(maps.getDiffs(), maps.getModifiers());
	}

}
