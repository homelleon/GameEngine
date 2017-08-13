package object.map.parser;

import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import manager.scene.IObjectManager;
import object.entity.entity.IEntity;
import object.entity.entity.IEntityBuilder;
import object.entity.entity.NormalMappedEntityBuilder;
import object.entity.entity.SimpleEntityBuilder;
import object.map.objectMap.ObjectMapManager;
import object.map.raw.IRawManager;
import object.model.raw.RawModel;
import object.terrain.builder.ITerrainBuilder;
import object.terrain.builder.MappedTerrainBuilder;
import object.terrain.builder.ProceduredTerrainBuilder;
import object.texture.terrain.pack.builder.ITerrainTexturePackBuilder;
import object.texture.terrain.pack.builder.TerrainTexturePackBuilder;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

public class ModelMapXMLParser extends XMLParser implements IObjectParser<IObjectManager> {

	private IObjectManager modelMap;
	private IRawManager rawMap;

	public ModelMapXMLParser(Document document, IRawManager rawMap) {
		super(document);
		this.rawMap = rawMap;
		this.modelMap = new ObjectMapManager();
	}

	@Override
	public IObjectManager parse() {
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
				System.out.println("-------------------------");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used model map file!");
		}

		return modelMap;
	}

	private void parseEntities(Node node, IObjectManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading entities...");
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
				boolean isNormal = Boolean.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL));
				IEntityBuilder builder;
				IEntity entity = null;
				if (isNormal) {
					builder = new NormalMappedEntityBuilder();
					String normalMap = XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL_TEXTURE);
					String specularMap = XMLUtils.getTagValue(entityEl, XMLUtils.SPECULAR_TEXTURE);
					float shiness = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.SHINE_DAMPER));
					float reflectivity = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.REFLECTIVITY));
					builder.setNormalTexture(normalMap).setSpecularTexture(specularMap)
						   .setTextureReflectivity(reflectivity).setTextureShiness(shiness);
					
				} else {
					builder = new SimpleEntityBuilder();
				}
				RawModel rawModel = rawMap.getRawModel(model);
				builder.setModel(model, rawModel).setTexture(texture).setPosition(position).setRotation(rotation);
				entity = builder.createEntity(name);
				map.getEntities().add(entity);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getEntities().get(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}

	private void parseTerrains(Node node, IObjectManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading terrains...");
		}
		Node terrains = node;
		NodeList terrainList = terrains.getChildNodes();
		for (int j = 0; j < terrainList.getLength(); j++) {
			Node terrain = terrainList.item(j);
			if (XMLUtils.ifNodeIsElement(terrain, XMLUtils.TERRAIN)) {
				ITerrainBuilder terrainBuilder;
				ITerrainTexturePackBuilder textureBuilder = new TerrainTexturePackBuilder();
				Element terrainEl = (Element) terrain;
				String ID = XMLUtils.getAttributeValue(terrain, XMLUtils.ID);
				String name = XMLUtils.getTagValue(terrainEl, XMLUtils.NAME);
				String baseTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BASE_TEXTURE);
				String redTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.RED_TEXTURE);
				String greenTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.GREEN_TEXTURE);
				String blueTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BLUE_TEXTURE);
				String blendTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BLEND_TEXTURE);
				Boolean isProcedured = Boolean.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.PROCEDURE_GENERATED));
				textureBuilder.setBackgroundTexture(baseTexture).setRedTexture(redTexture)
							  .setGreenTexture(greenTexture).setBlueTexture(blueTexture);
				if (isProcedured) {
					terrainBuilder = new ProceduredTerrainBuilder();					
					Float amplitude = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.AMPLITUDE));
					Integer octaves = Integer.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.OCTAVE));
					Float roughness = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.ROUGHTNESS));					
					terrainBuilder.setAmplitude(amplitude).setOctaves(octaves).setRoughness(roughness);				
				} else {
					terrainBuilder = new MappedTerrainBuilder();
					String heightMap = XMLUtils.getTagValue(terrainEl, XMLUtils.HEIGHT_TEXTURE);
					terrainBuilder.setHeightTextureName(heightMap);
				}
				terrainBuilder.setTexturePack(textureBuilder.create(name + "TexturePack"))
							  .setBlendTextureName(blendTexture);
				map.getTerrains().add(terrainBuilder.create(name));
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getTerrains().get(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}

}
