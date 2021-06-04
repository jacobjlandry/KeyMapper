
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

public class ReadBinds {
	
	public String bindsFileName;
	private ArrayList<Binding> binds = new ArrayList<Binding>();
	private ArrayList<Binding> emptyBinds = new ArrayList<Binding>();

	public ReadBinds(String filename) {
		// TODO Auto-generated method stub
		System.out.println("Hello World!");
		this.bindsFileName = filename;
		readFile();
	}
	
	public void readFile() {
		System.out.println("Reading File " + this.bindsFileName + "!");

		this.parseXml();
		
		System.out.println("Binds: " + this.binds.size());
		for(int count = 0; count < this.binds.size(); count++) {
			System.out.println("Printing Item " + count);
			this.binds.get(count).print();
			
			// if the map is empty, add it to our empty list
			if(this.binds.get(count).key == "") {
				this.emptyBinds.add(this.binds.get(count));
			}
		}
		
		Key key = new Key();
		ArrayList<String> unused = key.findUnused(binds);
		
		for(int count = 0; count < unused.size(); count++) {
			System.out.println("Found Unused Key: " + unused.get(count));
		}
		
		// fill in empty binds with available keys
		if(this.emptyBinds.size() > unused.size()) {
			System.out.println("Unable to fill all keys");
		} else {
			for(int x = 0; x < this.emptyBinds.size(); x++) {
				this.emptyBinds.get(x).key = unused.get(x);
			}
		}
		
		// merge newly filled empty binds back into binds
		for(int x = 0; x < this.binds.size(); x++) {
			if(this.binds.get(x).key == "") {
				for(int y = 0; y < this.emptyBinds.size(); y++) {
					if(this.binds.get(x).name == this.emptyBinds.get(y).name) {
						this.binds.get(x).key = this.emptyBinds.get(y).key;
					}
				}
			}
		}
		
		// rewrite binds xml file
		
	}
	
	private void parseXml()
	{
		   // Instantiate the Factory
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	      try (InputStream is = readXmlFileIntoInputStream(this.bindsFileName)) {

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          // read from a project's resources folder
	          Document doc = db.parse(is);

	          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
	          System.out.println("------");

	          if (doc.hasChildNodes()) {
	              printNote(doc.getChildNodes());
	          }

	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }

	  }

	  private void printNote(NodeList nodeList) {
		  
	      for (int count = 0; count < nodeList.getLength(); count++) {

	          Node tempNode = nodeList.item(count);

	          // make sure it's element node.
	          if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

	              Binding binding = new Binding();
	              binding.name = tempNode.getNodeName();
	              binding.value = tempNode.getTextContent();

	              if (tempNode.hasAttributes()) {

	                  // get attributes names and values
	                  NamedNodeMap nodeMap = tempNode.getAttributes();
	                  for (int i = 0; i < nodeMap.getLength(); i++) {
	                      Node node = nodeMap.item(i);
	                     
	                      if(node.getNodeName() == "Device") {
	                    	  binding.device = node.getNodeValue();
	                      }
	                      if(node.getNodeName() == "Key") {
	                    	  binding.key = node.getNodeValue();
	                      }
	          
	                  }

	              }
	              
	              this.binds.add(binding);

	              if (tempNode.hasChildNodes()) {
	                  // loop again if has child nodes
	                  printNote(tempNode.getChildNodes());
	              }

	          }

	      }

	  }

	  // read file from project resource's folder.
	  private static InputStream readXmlFileIntoInputStream(final String fileName) {
	      return ReadBinds.class.getClassLoader().getResourceAsStream(fileName);
	  }

}
