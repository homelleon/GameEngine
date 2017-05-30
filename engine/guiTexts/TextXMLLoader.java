package guiTexts;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import scene.ES;

public class TextXMLLoader implements TextLoaderInterface {

	@Override
	public String loadFile(String fileName, Integer lineNumber) {
		
		File textFile = new File(ES.TEXT_PATH + fileName + ".xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;       
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(textFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}	
		
        TextParserInterface txtParser = new TextXMLParser();
		
		return txtParser.readText(document);
	}

}
