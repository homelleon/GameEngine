package map.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import manager.scene.IObjectManager;
import map.objectMap.ObjectMapManager;
import object.entity.entity.IEntity;
import tool.math.vector.Vector3f;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

/**
 * Parser for creating manager relying on their positions and preloaded models from xml format.
 * 
 * @author homelleon
 * @see XMLParser
 * @see IObjectManager
 * @see IObjectParser
 */
public class LevelMapXMLParser extends XMLParser implements IObjectParser<IObjectManager> {
	
	IObjectManager modelMap;
	
	public LevelMapXMLParser(Document document, IObjectManager modelMap) {
		super(document);
		this.modelMap = modelMap;
	}

	@Override
	public IObjectManager parse() {
		IObjectManager levelMap = new ObjectMapManager();
		levelMap.getTerrains().addAll(this.modelMap.getTerrains().getAll());
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.LEVEL_MAP)) {
			NodeList nodeList = this.document.getDocumentElement().getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);			
				if(XMLUtils.ifNodeIsElement(node, XMLUtils.ENTITIES)) {
					parseEntities(node, levelMap);
				}
			}
			if (EngineDebug.hasDebugPermission()) {
				EngineDebug.print("Level complete!");
				EngineDebug.printClose("Level map");
				EngineDebug.printBorder();
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used level map file!");
		}
		return levelMap;	
	}

	private void parseEntities(Node node, IObjectManager levelMap) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Loading entities...", 1);
		}
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
				IEntity entityBase = modelMap.getEntities().get(model);
				IEntity entityClone = entityBase.clone(name);
				entityClone.setPosition(position);
				entityClone.setScale(scale);
				levelMap.getEntities().add(entityClone);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.print(levelMap.getEntities().get(name).getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Succed!", 1);
		}
	}
}
