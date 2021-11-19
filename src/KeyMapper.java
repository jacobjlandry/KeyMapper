import java.util.ArrayList;

public class KeyMapper {

	public static void main(String[] args) {
		// TODO Clean up
		// TODO Difference between List<String> and ArrayList<String> and String[] and why do i use each one?
		// TODO Tests
		// TODO Comments
		// TODO README
		// TODO allow input file
		// TODO clean up output messaging
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
