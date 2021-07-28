
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
	public Document newXml;
	private ArrayList<Binding> binds = new ArrayList<Binding>();
	private ArrayList<Binding> emptyBinds = new ArrayList<Binding>();

	public ReadBinds(String filename) {
		// TODO Auto-generated method stub
		this.bindsFileName = filename;
		readFile();
	}
	
	public void readFile() {
		System.out.println("Reading File " + this.bindsFileName + "!");

		this.parseXml();
		
		System.out.println("Binds: " + this.binds.size());
		for(int count = 0; count < this.binds.size(); count++) {
			// if the map is empty, add it to our empty list, only for primary maps
			// this will ignore any extraneous headers we sucked in and all secondary maps, which should be custom
			if(this.binds.get(count).name == "Primary" && this.binds.get(count).key == "") {
				this.emptyBinds.add(this.binds.get(count));
			}
		}
		
		Key key = new Key();
		ArrayList<String> unused = key.findUnused(binds);
		
		System.out.println("Unused: " + unused.size());
		
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
		this.writeXml();
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

	          if (doc.hasChildNodes()) {
	              getActions(doc.getChildNodes());
	          }

	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }

	  }

	private void writeXml() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		
        try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("company");
        doc.appendChild(rootElement);

        doc.createElement("staff");
        rootElement.appendChild(doc.createElement("staff"));

        //...create XML elements, and others...

        this.newXml = doc;

	}
	
	private void getActions(NodeList nodeList) { 
	      for (int count = 0; count < nodeList.getLength(); count++) {
	
	          Node tempNode = nodeList.item(count);
	          
	          if(tempNode.getNodeName() == "Root") {
		  getActions(tempNode.getChildNodes());
	  } else {
	      // make sure it's element node.
		          if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
	
		              Binding binding = new Binding();
		              binding.name = tempNode.getNodeName();
		              binding.value = tempNode.getTextContent();
	
		              if (tempNode.hasChildNodes()) {
		            	  binding = getBinding(tempNode.getChildNodes(), binding);
		            	  
		            	  this.binds.add(binding);
		              }
		          }
	          }
	      }
	  }
	  
	private Binding getBinding(NodeList nodeList, Binding binding) 
	  {
		  for (int count = 0; count < nodeList.getLength(); count++) {
			  Node tempNode = nodeList.item(count);
	
	          // make sure it's element node.
	  if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
		  // get the primary binding
		  if(tempNode.getNodeName() == "Primary" && tempNode.hasAttributes()) {
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
	              
	              // check for modifier
	              if(tempNode.hasChildNodes()) {
	            	  NodeList modNodes = tempNode.getChildNodes();
	            	  for(int modifierCount = 0; modifierCount < modNodes.getLength(); modifierCount++) {
	            		  if(modNodes.item(modifierCount).getNodeName() == "Modifier") {
	                    			  binding.modifier = modNodes.item(modifierCount).getNodeValue();
	                    		  }
	                    	  }
	                      }
	          
	                  }
	        	  }
	          }
		  }
		
		  return binding;
	  }
	
	// read file from project resource's folder.
	private static InputStream readXmlFileIntoInputStream(final String fileName) {
	    return ReadBinds.class.getClassLoader().getResourceAsStream(fileName);
	}

}
