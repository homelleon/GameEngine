package tool.xml.loader;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import core.EngineDebug;

/**
 * 
 * @author homelleon
 * @see IXMLLoader
 */
public class XMLFileLoader implements IXMLLoader {
	
	private InputStream stream;

	public XMLFileLoader(String fullFileName) {
		this.stream = Class.class.getResourceAsStream(fullFileName);
		if(EngineDebug.hasDebugPermission()) {
			EngineDebug.println(fullFileName);
		}
	}

	@Override
	public Document load() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(stream);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		return document;
	}

}