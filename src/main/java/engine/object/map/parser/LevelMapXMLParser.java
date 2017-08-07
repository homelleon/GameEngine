package object.map.parser;

import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import object.entity.entity.IEntity;
import object.map.objectMap.IObjectManager;
import object.map.objectMap.ObjectMapManager;
import object.terrain.terrain.ITerrain;
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
		IObjectManager map = new ObjectMapManager("levelMap");
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.LEVEL_MAP)) {
			NodeList nodeList = this.document.getDocumentElement().getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);			
				if(XMLUtils.ifNodeIsElement(node, XMLUtils.ENTITIES)) {
					parseEntities(node, map);
				} else if(XMLUtils.ifNodeIsElement(node, XMLUtils.TERRAINS)) {
					parseTerrains(node, map);
				}
			}
			if (EngineDebug.hasDebugPermission()) {
				System.out.println("Loading complete!");
				System.out.println("-------------------------");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used level map file!");
		}
		return map;	
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
				String name = XMLUtils.getTagValue(entityElement, XMLUtils.NAME);
				String model = XMLUtils.getTagValue(entityElement, XMLUtils.MODEL);
				Element positionElement = XMLUtils.getChildElementByTag(entityElement, XMLUtils.POSITION);
				float x = Float.valueOf(XMLUtils.getTagValue(positionElement, XMLUtils.X));
				float y = Float.valueOf(XMLUtils.getTagValue(positionElement, XMLUtils.Y));
				float z = Float.valueOf(XMLUtils.getTagValue(positionElement, XMLUtils.Z));
				Vector3f position = new Vector3f(x, y, z);
				float scale = Float.valueOf(XMLUtils.getTagValue(entityElement, XMLUtils.SCALE));
				IEntity entityBase = modelMap.getEntities().get(model);
				IEntity entityClone = entityBase.clone(name);
				entityClone.setPosition(position);
				entityClone.setScale(scale);
				levelMap.getEntities().add(entityClone);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + entityClone.getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}
	
	private void parseTerrains(Node node, IObjectManager levelMap) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading terrains...");
		}
		Node terrains = node;
		NodeList terrainList = terrains.getChildNodes();
		for (int j = 0; j < terrainList.getLength(); j++) {
			Node terrainNode = terrainList.item(j);
			if (XMLUtils.ifNodeIsElement(terrainNode, XMLUtils.TERRAIN)) {
				Element terrainElelement = (Element) terrainNode;
				String ID = XMLUtils.getAttributeValue(terrainNode, XMLUtils.ID);
				String name = XMLUtils.getTagValue(terrainElelement, XMLUtils.NAME);
				String model = XMLUtils.getTagValue(terrainElelement, XMLUtils.MODEL);
				Element positionElement = XMLUtils.getChildElementByTag(terrainElelement, XMLUtils.POSITION);
				int x = Integer.valueOf(XMLUtils.getTagValue(positionElement, XMLUtils.X));
				int z = Integer.valueOf(XMLUtils.getTagValue(positionElement, XMLUtils.Z));
				ITerrain terrainBase = modelMap.getTerrains().get(model);
				ITerrain terrainClone = terrainBase.clone(name);
				terrainClone.setXPosition(x);
				terrainClone.setZPosition(z);
				levelMap.getTerrains().add(terrainClone);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + terrainClone.getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}
}