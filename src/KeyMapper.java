import java.util.ArrayList;

public class KeyMapper {

	// TODO Tests
	// TODO allow input file
	// TODO add UI
	public static void main(String[] args) {
		intro();

		// read key binds file from Elite Dangerous
		KeyBindsFile binds = new KeyBindsFile("/Users/jacoblandry/Documents/Custom.3.0.binds");
		// create default map of keys
		KeyMaps maps = new KeyMaps();

		// determine what keys are already used
		maps.setUsed((ArrayList<String>) binds.getBinds());
		
		// determine what keys are available and replace them in the file
		binds.replaceContents(maps.getDiffs(), maps.getModifiers());
	}

	private static void intro() {
		System.out.println(" +------------------------------------------------+ ");
		System.out.println(" |                   Key Mapper                   | ");
		System.out.println(" |                                                | ");
		System.out.println(" |                 By Jacob Landry                | ");
		System.out.println(" +------------------------------------------------+ ");
		System.out.println("");
		System.out.println("Reading input file from binds folder. A new, completely filled file will be stored in the binds folder in this project.");
	}

}
