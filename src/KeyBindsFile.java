import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyBindsFile {
    // file we are reading
    public String bindsFileName;
    private String bindsFilePath;
    // contents of file
    private String bindsFileContents;

    /**
     * constructor
     * @param filename
     */
    public KeyBindsFile(String filename)
    {
        this.bindsFileName = filename;
        this.bindsFilePath = System.getProperty("user.dir") + "/src/" + this.bindsFileName;

        this.readFile();
    }

    /**
     * pull a list of all used keys from the file
     * TODO this is where we should check for modifiers
     * @return List<String>
     */
    public List<String> getBinds()
    {
        // get all Key=".*" records
		ArrayList<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("Key=\".+\"")
			.matcher(this.bindsFileContents);
		while (m.find()) {
		  allMatches.add(m.group().replace("Key=\"", "").replace("\"",""));
		}

        return allMatches;
    }

    /**
     * replace empty keys with unused keys (including modifiers)
     * write to file
     * 
     * @param binds
     * @param modifiers
     */
    public void replaceContents(ArrayList<String> binds, String[] modifiers)
    {
        for(int x = 0; x < binds.size(); x++) {
			bindsFileContents = bindsFileContents.replaceFirst("<Primary Device=\"\\Q{\\ENoDevice\\Q}\\E\" Key=\"\" />", "<Primary Device=\"Keyboard\" Key=\"" + binds.get(x) + "\" />");
		}
		// add modifiers
		for(int y = 0; y < modifiers.length; y++) {
			String modifier = modifiers[y];
			for(int x = 0; x < binds.size(); x++) {
				bindsFileContents = bindsFileContents.replaceFirst("<Primary Device=\"\\Q{\\ENoDevice\\Q}\\E\" Key=\"\" />", "<Primary Device=\"Keyboard\" Key=\"" + binds.get(x) + "\">\r\n			<Modifier Device=\"Keyboard\" Key=\"" + modifier + "\" />\r\n		</Primary>");
			}
		}

        this.writeFile();
    }

    /**
     * read file into memory
     */
    private void readFile()
    {
        String content = "";
 
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(this.bindsFilePath) ) );
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        this.bindsFileContents = content;
    }

    /**
     * write the file to hard drive
     */
    private void writeFile()
    {
        // save text to file
		try {
			File myObj = new File(System.getProperty("user.dir") + "/src/" + this.bindsFileName + ".dup");
			if (myObj.createNewFile()) {
			  System.out.println("File created: " + myObj.getName());
			} else {
			  System.out.println("File already exists.");
			}
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
		  try {
			FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/src/" + this.bindsFileName + ".dup");
			myWriter.write(this.bindsFileContents);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
    }
}