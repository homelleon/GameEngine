package object.map.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.map.raw.IRawManager;
import object.map.raw.RawManager;
import object.model.RawModel;
import object.texture.model.ModelTexture;
import object.texture.terrain.ITerrainTexturePackBuilder;
import object.texture.terrain.TerrainTexturePackBuilder;
import renderer.loader.Loader;
import renderer.loader.TextureBufferLoader;
import tool.converter.object.ModelData;
import tool.converter.object.OBJFileLoader;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

public class RawMapXMLParser extends XMLParser implements IObjectParser<IRawManager> {

	public RawMapXMLParser(Document document) {
		super(document);
	}

	@Override
	public IRawManager parse() {
		//TODO: CHANGE IT ALL!
		IRawManager map = new RawManager();
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.RAW_MAP)) {
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (XMLUtils.ifNodeIsElement(node, XMLUtils.MODELS)) {
					parseRawModel(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TEXTURES)) {
					parseTextures(node, map);
				}
			}
			if (EngineDebug.hasDebugPermission()) {
				System.out.println("Loading complete!");
				System.out.println("-------------------------");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used model map file!");
		}

		return map;
	}
	
	private void parseRawModel(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading models...");
		}
		Node rawModels = node;
		NodeList rawModelList = rawModels.getChildNodes();
		for (int j = 0; j < rawModelList.getLength(); j++) {
			Node rawModelNode = rawModelList.item(j);
			if (XMLUtils.ifNodeIsElement(rawModelNode, XMLUtils.RAW_MODEL)) {
				Element rawModelElement = (Element) rawModelNode;
				String ID = XMLUtils.getAttributeValue(rawModelNode, XMLUtils.ID);
				String name = XMLUtils.getTagValue(rawModelElement, XMLUtils.NAME);
				String model = XMLUtils.getTagValue(rawModelElement, XMLUtils.MODEL);
				ModelData data = OBJFileLoader.loadOBJ(model);
				RawModel rawModel = Loader.getInstance().getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
						data.getIndices());
				map.addRawModel(rawModel);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getRawModel(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}
	
	private void parseTextures(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading textures...");
		}
		Node rawModels = node;
		NodeList rawModelList = rawModels.getChildNodes();
		for (int j = 0; j < rawModelList.getLength(); j++) {
			Node rawModelNode = rawModelList.item(j);
			if (XMLUtils.ifNodeIsElement(rawModelNode, XMLUtils.ENTITIES)) {
				parseEntities(rawModelNode, map);
			} else if (XMLUtils.ifNodeIsElement(rawModelNode, XMLUtils.TERRAINS)) {
				parseTerrains(rawModelNode, map);
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
		
	}

	private void parseEntities(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading entities...");
		}
		Node entities = node;
		NodeList entityList = entities.getChildNodes();
		for (int k = 0; k < entityList.getLength(); k++) {
			Node entityNode = entityList.item(k);
			if (XMLUtils.ifNodeIsElement(entityNode, XMLUtils.TEXTURE)) {
				Element entityEl = (Element) entityNode;
				String ID = XMLUtils.getAttributeValue(entityNode, XMLUtils.ID);
				String name = XMLUtils.getTagValue(entityEl, XMLUtils.NAME);
				String baseTexture = XMLUtils.getTagValue(entityEl, XMLUtils.BASE_TEXTURE);
				TextureBufferLoader textureLoader = Loader.getInstance().getTextureLoader();
				int textureID = textureLoader.loadTexture(EngineSettings.TEXTURE_MODEL_PATH, baseTexture);
				ModelTexture texture = new ModelTexture(name, textureID);
				boolean isNormal = Boolean.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL));
				if(isNormal) {
					String normalTexture = XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL_TEXTURE);
					int normalID = textureLoader.loadTexture(EngineSettings.TEXTURE_NORMAL_MAP_PATH, normalTexture);
					texture.setNormalMap(normalID);
				}
				boolean isSpecular = Boolean.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.SPECULAR));
				if(isSpecular) {
					String specularTexture = XMLUtils.getTagValue(entityEl, XMLUtils.SPECULAR_TEXTURE);
					int specularID = textureLoader.loadTexture(EngineSettings.TEXTURE_SPECULAR_MAP_PATH, specularTexture);
					texture.setSpecularMap(specularID);
				}
				map.addModelTexture(texture);

				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getModelTexture(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}

	private void parseTerrains(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading terrains...");
		}
		Node terrains = node;
		NodeList terrainList = terrains.getChildNodes();
		for (int k = 0; k < terrainList.getLength(); k++) {
			Node terrain = terrainList.item(k);
			if (XMLUtils.ifNodeIsElement(terrain, XMLUtils.TERRAIN)) {
				Element terrainElement = (Element) terrain;
				String ID = XMLUtils.getAttributeValue(terrain, XMLUtils.ID);
				String name = XMLUtils.getTagValue(terrainElement, XMLUtils.NAME);
				String baseTexture = XMLUtils.getTagValue(terrainElement, XMLUtils.BASE_TEXTURE);
				String redTexture = XMLUtils.getTagValue(terrainElement, XMLUtils.RED_TEXTURE);
				String greenTexture = XMLUtils.getTagValue(terrainElement, XMLUtils.GREEN_TEXTURE);
				String blueTexture = XMLUtils.getTagValue(terrainElement, XMLUtils.BLUE_TEXTURE);
				ITerrainTexturePackBuilder textureBuilder = new TerrainTexturePackBuilder();
				textureBuilder.setBackgroundTexture(baseTexture).setRedTexture(redTexture)
							  .setGreenTexture(greenTexture).setBlueTexture(blueTexture);
				map.addTerrainTexturePack(textureBuilder.create(name));
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getTerrainTexturePack(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}

}
