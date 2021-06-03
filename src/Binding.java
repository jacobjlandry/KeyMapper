
public class Binding {
	public String name;
	public String value;
	public String device;
	public String key;
	
	public Binding() {
		
	}
	
	public void print() {
		System.out.println(this.name);
		System.out.println(this.value);
		System.out.println(this.device);
		System.out.println(this.key);
	}
	
	// find empty binds
	// suggest Key() binds for empty ones based on unused keys
	// apply binds
}
