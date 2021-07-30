
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
		
			Document document = db.newDocument();
			
			//<Root PresetName="Custom" MajorVersion="3" MinorVersion="0">
			Element root = document.createElement("Root");
			root.setAttribute("PresetName", "Custom");
			root.setAttribute("MajorVersion", "3");
			root.setAttribute("MinorVersion", "0");
			document.appendChild(root);      
			
			// TODO make these the actual keys
			for(int x = 0; x < this.binds.size(); x++) {
				Binding bind = this.binds.get(x);

				// create element for key binding
				Element binding = document.createElement(bind.name);
				// add text content 
				if(bind.value != "") {
					binding.setTextContent(bind.value);	
				}
				
				if(bind.name == "MouseReset") {
					System.out.println(bind.name);
					System.out.println(bind.type);
					System.out.println(bind.value);
					System.out.println(bind.device);
					System.out.println(bind.key);
					System.out.println(bind.modifier);
				}
				if(bind.type != null) {
					System.out.print(bind.type);
				}
				root.appendChild(binding);
			}
			
			this.newXml = document;
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			
			 // send DOM to file
			// TODO use the WriteXml class for this
            transformer.transform(new DOMSource(document), 
                                 new StreamResult(new FileOutputStream("src/allkeys2.binds")));

		} catch (ParserConfigurationException | TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		              binding.value = tempNode.getTextContent().trim();
		              
		              //TODO get the rest of the data
	
		              if (tempNode.hasChildNodes()) {
		            	  binding = getBinding(tempNode.getChildNodes(), binding);
		            	  
		            	  this.binds.add(binding);
		              }
		          }
	          }
	      }
	  }
	  
	// TODO this is only reading primary right now. not secondary, binding, etc.
	// TODO we need to form a list of all child elements that we can use
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
