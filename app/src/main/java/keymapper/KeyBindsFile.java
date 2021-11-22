package keymapper;
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
    
    // contents of file
    private String bindsFileContents;

    /**
     * constructor
     * @param filename
     */
    public KeyBindsFile(String filename)
    {
        this.bindsFileName = filename;

        this.readFile();
    }

    /**
     * pull a list of all used keys from the file
     * TODO this is where we should check for modifiers instead of just keys
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

        System.out.println(allMatches.size());

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
			bindsFileContents = bindsFileContents.replaceFirst("Device=\"\\Q{\\ENoDevice\\Q}\\E\" Key=\"\" />", "Device=\"Keyboard\" Key=\"" + binds.get(x) + "\" />");
		}
		// add modifiers
		for(int y = 0; y < modifiers.length; y++) {
			String modifier = modifiers[y];
			for(int x = 0; x < binds.size(); x++) {
                Matcher m = Pattern.compile("<Primary Device=\"\\Q{\\ENoDevice\\Q}\\E\" Key=\"\" />")
                    .matcher(this.bindsFileContents);
		        if (m.find()) {
				    bindsFileContents = bindsFileContents.replaceFirst("<Primary Device=\"\\Q{\\ENoDevice\\Q}\\E\" Key=\"\" />", "<Primary Device=\"Keyboard\" Key=\"" + binds.get(x) + "\">\r\n			<Modifier Device=\"Keyboard\" Key=\"" + modifier + "\" />\r\n		</Primary>");
                } else {
                    bindsFileContents = bindsFileContents.replaceFirst("<Secondary Device=\"\\Q{\\ENoDevice\\Q}\\E\" Key=\"\" />", "<Secondary Device=\"Keyboard\" Key=\"" + binds.get(x) + "\">\r\n			<Modifier Device=\"Keyboard\" Key=\"" + modifier + "\" />\r\n		</Secondary>");
                }
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
            content = new String ( Files.readAllBytes( Paths.get(this.bindsFileName) ) );
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
        // Create file if it doesn't exist
        try {
            File newBindsFile = new File(this.bindsFileName + ".dup");
            if (newBindsFile.createNewFile()) {
                System.out.println("File created: " + newBindsFile.getName());
            } else {
                System.out.println("File already exists, overwriting.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred creating the file.");
            e.printStackTrace();
            return;
        }

        // write to file
        try {
            FileWriter bindsFileWriter = new FileWriter(System.getProperty("user.dir") + "/app/src/binds/KeyMapperv1.0.binds");
            bindsFileWriter.write(this.bindsFileContents);
            bindsFileWriter.close();
            System.out.println("Successfully wrote to the file.");
            System.out.println("You can now move KeyMapperv1.0.binds to your Elite Dangerous key-binds location.");
            System.out.println("Remember to back up your old file first, to be safe!");
        } catch (IOException e) {
            System.out.println("An error occurred writing the file.");
            e.printStackTrace();
            return;
        }
    }
}