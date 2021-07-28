import javax.xml.transform.TransformerException;

public class KeyMapper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ReadBinds binds = new ReadBinds("Custom.3.0.binds");
		ReadBinds binds = new ReadBinds("allkeys.binds");
		
		try {
			WriteBinds newBinds = new WriteBinds(binds, "src/allkeys.binds");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
