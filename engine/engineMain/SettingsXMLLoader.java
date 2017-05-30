package engineMain;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import scene.ES;

/**
 * 
 * @author homelleon
 * @see SettingsLoaderInterface
 */
public class SettingsXMLLoader implements SettingsLoaderInterface {
 
	@Override
	public GameSettings loadSettings(String fileName) {
		
        File settingsFile = new File(ES.GAME_SETTINGS_PATH + fileName + ".xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;       
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(settingsFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}	
		
		SettingsParserInterface parser = new SettingsTXTParser();
		return parser.readSettings(document);   
	}

}
