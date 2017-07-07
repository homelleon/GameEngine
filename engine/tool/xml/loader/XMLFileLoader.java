package tool.xml.loader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author homelleon
 * @see XMLLoaderInterface
 */
public class XMLFileLoader implements XMLLoaderInterface {
	
	private File file;
	
	public XMLFileLoader(String fullFileName) {
		this.file = new File(fullFileName);
	}
	
	@Override
	public Document load() {		
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;       
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return document;
	}

}
