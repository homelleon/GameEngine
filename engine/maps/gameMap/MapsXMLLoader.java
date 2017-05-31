package maps.gameMap;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import core.debug.EngineDebug;
import core.settings.ES;
import maps.objectMap.ObjectMapInterface;
import renderers.Loader;

public class MapsXMLLoader implements MapsLoader {
	
	
	public GameMap loadMap(String fileName, Loader loader) {
		if(EngineDebug.hasDebugPermission()) {
        	System.out.println("Start loading map '" + fileName + "'...");
        }
        File mapFile = new File(ES.MAP_PATH + fileName + ".xml");
        
        MapsParser mapParser = new MapsXMLParser();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;       
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(mapFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}	
		
        GameMap map = new GameMap(fileName, loader);
        
		return  mapParser.readMap(document, map);
	}
	
	public ObjectMapInterface loadObjectMap(String fileName, Loader loader) {
		return null;
		
	}

}
