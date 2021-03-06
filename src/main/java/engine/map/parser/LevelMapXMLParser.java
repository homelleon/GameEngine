package map.parser;

import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import manager.scene.IObjectManager;
import map.objectMap.ObjectMapManager;
import object.entity.entity.IEntity;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

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
				System.out.println("Loading complete!");
				System.out.println("-------------------------");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used level map file!");
		}
		return levelMap;	
	}

	private void parseEntities(Node node, IObjectManager levelMap) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading entities...");
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
					System.out.println(">> " + levelMap.getEntities().get(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}
}
