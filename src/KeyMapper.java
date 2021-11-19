import java.util.ArrayList;
import java.util.Scanner;

public class KeyMapper {

	// TODO Tests
	// TODO add UI

	// path to the file we want to parse
	private static String filePath;

	public static void main(String[] args) {
		intro();
		requestFile();

		// read key binds file from Elite Dangerous
		KeyBindsFile binds = new KeyBindsFile(filePath);
		// create default map of keys
		KeyMaps maps = new KeyMaps();

		// determine what keys are already used
		maps.setUsed((ArrayList<String>) binds.getBinds());
		
		// determine what keys are available and replace them in the file
		binds.replaceContents(maps.getDiffs(), maps.getModifiers());
	}

	/**
	 * Print a little intro text
	 */
	private static void intro() {
		System.out.println(" +------------------------------------------------+ ");
		System.out.println(" |                   Key Mapper                   | ");
		System.out.println(" |                                                | ");
		System.out.println(" |                 By Jacob Landry                | ");
		System.out.println(" +------------------------------------------------+ ");
		System.out.println("This will take a .binds file from Elite Dangerous and provide you with a new, filled version of the file that you can use to replace the existing file.");
		System.out.println("I am still in the process of testing this new file in Elite Dangerous: Horizons, use at your own risk.");
		System.out.println("");
		System.out.println("");
	}

	/**
	 * Request file for parsing
	 */
	private static void requestFile()
	{
		System.out.println("Please type the absolute path of the file you want to replace");
		System.out.println("(Note: we will not edit this file, we will create a new file that you can use to replace the existing on your own)");

		// collect file from user
		System.out.println("file: ");
		Scanner inputScanner = new Scanner(System.in);
		filePath = inputScanner.nextLine();
		inputScanner.close();
		System.out.println("A new, completely filled file will be stored in the binds folder in this project.");
	}

}
