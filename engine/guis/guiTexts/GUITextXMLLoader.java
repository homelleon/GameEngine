package guis.guiTexts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import core.debug.EngineDebug;
import core.settings.ES;
import font.GUIText;
import font.TextMaster;

public class GUITextXMLLoader implements GUITextLoaderInterface {
	
	public List<GUIText> loadFile(String fileName, TextMaster master) {
		if(EngineDebug.hasDebugPermission()) {
        	System.out.println("Start loading file '" + fileName + "'...");
        }
		File textFile = new File(ES.INTERFACE_PATH + fileName + ".xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;       
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(textFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}	
		
        GUITextParserInterface txtParser = new GUITextXMLParser();
        
		return txtParser.parse(document, master);
	}

}
