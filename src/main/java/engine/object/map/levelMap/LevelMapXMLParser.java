package object.map.levelMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import object.entity.entity.Entity;
import object.map.modelMap.ModelMapInterface;
import object.terrain.terrain.Terrain;
import tool.xml.XMLUtils;
import tool.xml.parser.ObjectParserInterface;
import tool.xml.parser.XMLParser;

public class LevelMapXMLParser extends XMLParser implements ObjectParserInterface<LevelMapInterface> {
	
	ModelMapInterface modelMap;
	
	public LevelMapXMLParser(Document document, ModelMapInterface modelMap) {
		super(document);
		this.modelMap = modelMap;
	}

	@Override
	public LevelMapInterface parse() {
		LevelMapInterface map = new LevelMap();
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
				System.out.println("Loading objects complete!");
				System.out.println("-------------------------");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used level map file!");
		}
		return map;	
	}

	private void parseEntities(Node node, LevelMapInterface levelMap) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading entity objects...");
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
				Entity entityBase = modelMap.getEntities().get(model);
				Entity entityClone = entityBase.clone(name);
				entityClone.setPosition(position);
				entityClone.setScale(scale);
				levelMap.addEntity(entityClone);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(entityClone.getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
		}
	}
	
	private void parseTerrains(Node node, LevelMapInterface levelMap) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading terrain objects...");
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
				Terrain terrainBase = modelMap.getTerrains().get(model);
				Terrain terrainClone = terrainBase.clone(name);
				terrainClone.setXPosition(x);
				terrainClone.setZPosition(z);
				levelMap.addTerrain(terrainClone);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(terrainClone.getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
		}
	}
}
