package scene.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.EngineDebug;
import manager.ObjectMapManager;
import manager.scene.ObjectManager;
import object.entity.Entity;
import tool.math.vector.Vector3f;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

/**
 * Parser for creating manager relying on their positions and preloaded models from xml format.
 * 
 * @author homelleon
 * @see XMLParser
 * @see ObjectManager
 * @see IObjectParser
 */
public class LevelMapXMLParser extends XMLParser implements IObjectParser<ObjectManager> {
	
	ObjectManager modelMap;
	
	public LevelMapXMLParser(Document document, ObjectManager modelMap) {
		super(document);
		this.modelMap = modelMap;
	}

	@Override
	public ObjectManager parse() {
		ObjectManager levelMap = new ObjectMapManager();
		levelMap.getTerrains().addAll(this.modelMap.getTerrains().getAll());
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.LEVEL_MAP)) {
			NodeList nodeList = this.document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);			
				if (XMLUtils.ifNodeIsElement(node, XMLUtils.ENTITIES))
					parseEntities(node, levelMap);
			}
			if (EngineDebug.hasDebugPermission()) {
				EngineDebug.println("Level complete!");
				EngineDebug.printClose("Level map");
				EngineDebug.printBorder();
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used level map file!");
		}
		return levelMap;	
	}

	private void parseEntities(Node node, ObjectManager levelMap) {
		if (EngineDebug.hasDebugPermission())
			EngineDebug.println("Loading entities...", 1);
		
		Node entities = node;
		NodeList entityList = entities.getChildNodes();
		for (int j = 0; j < entityList.getLength(); j++) {
			Node entityNode = entityList.item(j);
			if (XMLUtils.ifNodeIsElement(entityNode, XMLUtils.ENTITY)) {
				Element entityElement = (Element) entityNode;
				String ID = XMLUtils.getAttributeValue(entityNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(entityNode, XMLUtils.NAME);
				String model = XMLUtils.getAttributeValue(entityNode, XMLUtils.MODEL);
				float scale = Float.valueOf(XMLUtils.getAttributeValue(entityNode, XMLUtils.SCALE));
				Element positionElement = XMLUtils.getChildElementByTag(entityElement, XMLUtils.POSITION);
				float x = Float.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.X));
				float y = Float.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.Y));
				float z = Float.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.Z));
				Vector3f position = new Vector3f(x, y, z);
				Entity entityBase = modelMap.getEntities().get(model);
				Entity entityClone = entityBase.clone(name);
				entityClone.setPosition(position);
				entityClone.setScale(new Vector3f(scale, scale, scale));
				levelMap.getEntities().add(entityClone);
				if (EngineDebug.hasDebugPermission())
					EngineDebug.println(levelMap.getEntities().get(name).getName(), 2);
			}
		}
		if (EngineDebug.hasDebugPermission())
			EngineDebug.println("Succed!", 1);
	}
}
