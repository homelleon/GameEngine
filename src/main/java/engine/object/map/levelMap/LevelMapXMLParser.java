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
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.MODEL_MAP)) {
			NodeList nodeList = this.document.getDocumentElement().getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (XMLUtils.ifNodeIsElement(node, XMLUtils.MAP)) {
					NodeList mapNodeList = node.getChildNodes();
					for(int j = 0; j < mapNodeList.getLength(); j++) {
						Node mapNode = mapNodeList.item(j);
						if(XMLUtils.ifNodeIsElement(node, XMLUtils.ENTITIES)) {
							parseEntities(mapNode, map);
						}					
					}
				}
			}
			if (EngineDebug.hasDebugPermission()) {
				System.out.println("Loading complete!");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used level map file!");
		}
		return map;	
	}

	private void parseEntities(Node node, LevelMapInterface levelMap) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading entities...");
		}
		Node entities = node;
		NodeList entityList = entities.getChildNodes();
		for (int k = 0; k < entityList.getLength(); k++) {
			Node entityNode = entityList.item(k);
			if (XMLUtils.ifNodeIsElement(entityNode, XMLUtils.ENTITY)) {
				Element entityEl = (Element) entityNode;
				String ID = XMLUtils.getAttributeValue(entityNode, XMLUtils.ID);
				String name = XMLUtils.getTagValue(entityEl, XMLUtils.NAME);
				String model = XMLUtils.getTagValue(entityEl, XMLUtils.MODEL);
				Element positionEl = XMLUtils.getChildElementByTag(entityEl, XMLUtils.POSITION);
				float x = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.X));
				float y = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.Y));
				float z = Float.valueOf(XMLUtils.getTagValue(positionEl, XMLUtils.Z));
				Vector3f position = new Vector3f(x, y, z);
				Entity entityBase = modelMap.getEntities().get(model);
				Entity entityClone = entityBase.clone(name);
				entityClone.setPosition(position);
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
}
