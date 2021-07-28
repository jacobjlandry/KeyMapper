import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import javax.swing.text.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WriteBinds {
	
	public String bindsFileName;

	public WriteBinds(ReadBinds binds, String source) throws TransformerException {
		this.bindsFileName = source;
		try {
			this.backup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
			e.printStackTrace();
		}
		
		// write dom document to a file
        try (FileOutputStream output =
                     new FileOutputStream(this.bindsFileName)) {
            this.writeXml(binds.newXml, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void backup() throws IOException
	{
		long millis = System.currentTimeMillis();

		 	FileChannel sourceChannel = null;
		    FileChannel destChannel = null;
		    File source = new File(this.bindsFileName);
		    File dest = new File(this.bindsFileName + "." + millis + ".backup");
		    
		    try {
		        sourceChannel = new FileInputStream(source).getChannel();
		        destChannel = new FileOutputStream(dest).getChannel();
		        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		       } finally {
		           sourceChannel.close();
		           destChannel.close();
		   }
	}
	
	// write doc to output stream
    private void writeXml(org.w3c.dom.Document newXml,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource();
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
}
