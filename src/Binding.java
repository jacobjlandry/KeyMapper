
public class Binding {
	public String name;
	public String type;
	public String value;
	public String device;
	public String key;
	public String modifier;
	private String xml;
	
	public Binding() {
		
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
