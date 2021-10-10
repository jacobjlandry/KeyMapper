import java.util.Map;
import java.util.HashMap;

public class Binding {
	public String name;
	public String type;
	public String content;
	public String value;
	public Map<String, String> attributes;	// replaces next three
	public String device;	// not needed
	public String key;		// not needed
	public String modifier;	// not needed
	public Binding[] children;
	private String xml;
	
	public Binding() {
		this.attributes = new HashMap<String, String>();
	}
	
	// Print for debug
	public void print() {
		System.out.println("Name: " + this.name);
		System.out.println("Type: " + this.type);
		System.out.println("Value: " + this.value);
		System.out.println("Device: " + this.device);
		System.out.println("Key: " + this.key);
	}
	
	public String makeXml()
	{
		return this.xml;
	}
	
	// find empty binds
	// suggest Key() binds for empty ones based on unused keys
	// apply binds
}
