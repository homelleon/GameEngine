package object.map.modelMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.entity.entity.Entity;
import object.entity.entity.EntityBuilderInterface;
import object.entity.entity.SimpleEntityBuilder;
import object.entity.entity.TexturedEntity;
import object.model.TexturedModel;
import tool.EngineUtils;
import tool.xml.XMLUtils;
import tool.xml.parser.ObjectParserInterface;
import tool.xml.parser.XMLParser;

public class ModelMapXMLParser extends XMLParser implements ObjectParserInterface<ModelMapInterface> {

	private ModelMapInterface modelMap;

	public ModelMapXMLParser(Document document, String fileName) {
		super(document);
		this.modelMap = new ModelMap(fileName);
	}

	@Override
	public ModelMapInterface parse() {
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.MODEL_MAP)) {
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (XMLUtils.ifNodeIsElement(node, XMLUtils.ENTITIES)) {
					parseEntities(node, modelMap);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TERRAINS)) {
					parseTerrains(node, modelMap);
				}
			}
			if (EngineDebug.hasDebugPermission()) {
				System.out.println("Loading complete!");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used model map file!");
		}

		return modelMap;
	}

	private void parseEntities(Node node, ModelMapInterface map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading entities...");
		}
		Node entities = node;
		NodeList entityList = entities.getChildNodes();
		for (int j = 0; j < entityList.getLength(); j++) {
			Node entityNode = entityList.item(j);
			if (XMLUtils.ifNodeIsElement(entityNode, XMLUtils.ENTITY)) {
				Element entityEl = (Element) entityNode;
				String ID = XMLUtils.getAttributeValue(entityNode, XMLUtils.ID);
				String name = XMLUtils.getTagValue(entityEl, XMLUtils.NAME);
				String model = XMLUtils.getTagValue(entityEl, XMLUtils.MODEL);
				String texture = XMLUtils.getTagValue(entityEl, XMLUtils.TEXTURE);
				Vector3f position = new Vector3f(0, 0, 0);
				Vector3f rotation = new Vector3f(0, 0, 0);
				float scale = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.SCALE));
				boolean isNormal = Boolean.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL));
				EntityBuilderInterface builder;
				if (isNormal) {
					String normalMap = XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL_TEXTURE);
					String specularMap = XMLUtils.getTagValue(entityEl, XMLUtils.SPECULAR_TEXTURE);
					float shine = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.SHINE_DUMPER));
					float reflectivity = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.REFLECTIVITY));
					builder = new SimpleEntityBuilder();
					builder.setModel(model).setTexture(texture).setPosition(position).setRotation(rotation).setScale(scale);
					Entity entity = builder.createEntity(name);
					TexturedModel staticModel = EngineUtils.loadNormalModel(name, texture, normalMap, specularMap);
					staticModel.getTexture().setShineDamper(shine);
					staticModel.getTexture().setReflectivity(reflectivity);
					TexturedEntity entity = new TexturedEntity(name, EngineSettings.ENTITY_TYPE_NORMAL, staticModel, position, rotation, scale);
					map.addEntity(entity);
				} else {
					builder = new SimpleEntityBuilder();
					builder.setModel(model).setTexture(texture).setPosition(position).setRotation(rotation).setScale(scale);
					Entity entity = builder.createEntity(name);
					map.createEntity(name, model, texture, position, new Vector3f(0, 0, 0), scale);
				}
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(map.getEntities().get(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
		}
	}

	private void parseTerrains(Node node, ModelMapInterface map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading terrains...");
		}
		Node terrains = node;
		NodeList terrainList = terrains.getChildNodes();
		for (int j = 0; j < terrainList.getLength(); j++) {
			Node terrain = terrainList.item(j);
			if (XMLUtils.ifNodeIsElement(terrain, XMLUtils.TERRAIN)) {
				Element terrainEl = (Element) terrain;
				String ID = XMLUtils.getAttributeValue(terrain, XMLUtils.ID);
				String name = XMLUtils.getTagValue(terrainEl, XMLUtils.NAME);
				String baseTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BASE_TEXTURE);
				String redTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.RED_TEXTURE);
				String greenTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.GREEN_TEXTURE);
				String blueTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BLUE_TEXTURE);
				String blendTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BLEND_TEXTURE);
				Boolean isProcedured = Boolean.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.PROCEDURE_GENERATED));
				Vector2f position = new Vector2f(0,0);
				if (isProcedured) {
					Float amplitude = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.AMPLITUDE));
					Integer octave = Integer.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.OCTAVE));
					Float roughness = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.ROUGHTNESS));
					map.createTerrain(name, position, baseTexture, redTexture, greenTexture, blueTexture, blendTexture,
							amplitude, octave, roughness);
				} else {
					String heightMap = XMLUtils.getTagValue(terrainEl, XMLUtils.HEIGHT_TEXTURE);
					map.createTerrain(name, position, baseTexture, redTexture, greenTexture, blueTexture, blendTexture,
							heightMap);
				}
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(map.getTerrains().get(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
		}
	}

}
