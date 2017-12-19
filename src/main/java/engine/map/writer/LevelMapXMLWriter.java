package map.writer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import core.settings.EngineSettings;
import manager.scene.IObjectManager;

/**
 * Writer for creating xml file from object manager with object datas.
 * 
 * @author homelleon
 * @see ILevelMapWriter
 * @see IObjectManager
 */
public class LevelMapXMLWriter implements ILevelMapWriter {
	
	private final String saveName = "levelMap1";

	@Override
	public void write(IObjectManager levelMap) {
		try {
			System.out.println("Saving level map...");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			
			builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element map = document.createElement("level_map");
			document.appendChild(map);			
			this.writeEntities(levelMap, document, map);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			File mapFile = new File(EngineSettings.MAP_PATH + saveName + ".xml");
			StreamResult result = new StreamResult(mapFile);
			DOMSource srouce = new DOMSource(document);
			transformer.transform(srouce, result);
				
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
		System.out.println("Save complete!");
	}
	
	private void writeEntities(IObjectManager levelMap, Document document, Element mapElement) {
		Element entities = (Element) mapElement.appendChild(document.createElement("entities"));
		levelMap.getEntities().getAll()
			.forEach(entity -> {
				Element entityElement = (Element) entities.appendChild(document.createElement("entity"));
				Element positionElement = (Element) entityElement.appendChild(document.createElement("position"));
				entityElement.setAttribute("id", "1");
				entityElement.setAttribute("name", entity.getName());
				entityElement.setAttribute("model", entity.getBaseName());
				entityElement.setAttribute("scale", String.valueOf(entity.getScale()));
				positionElement.setAttribute("x", String.valueOf(entity.getPosition().x));
				positionElement.setAttribute("y", String.valueOf(entity.getPosition().y));
				positionElement.setAttribute("z", String.valueOf(entity.getPosition().z));
			});
	}
	
	private void writeTerrains(IObjectManager levelMap, Document document, Element mapElement) {
		
	}

}
