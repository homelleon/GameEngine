package object.map.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.map.raw.IRawManager;
import object.map.raw.RawManager;
import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;
import object.texture.terrain.pack.TerrainTexturePack;
import object.texture.terrain.texture.TerrainTexture;
import renderer.loader.Loader;
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
		IRawManager map = new RawManager();
		if(document.getDocumentElement().getNodeName().equals(XMLUtils.RAW_MAP)) {
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (XMLUtils.ifNodeIsElement(node, XMLUtils.MODEL_TEXTURES)) {
					parseModelTextures(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TERRAIN_TEXTURES)) {
					parseTerrainTextures(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TERRAIN_PACKS)) {
					parseTerrainPacks(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.MODELS)) {
					parseRawModels(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TEXTURED_MODELS)) {
					parseTexturedModels(node, map);
				} 
			}
			if (EngineDebug.hasDebugPermission()) {
				System.out.println("Raws complete!");
				System.out.println("-------------------------");
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used model map file!");
		}

		return map;
	}
	
	private void parseRawModels(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading raw models...");
		}
		NodeList rawModelList = node.getChildNodes();
		for (int j = 0; j < rawModelList.getLength(); j++) {
			Node rawModelNode = rawModelList.item(j);
			if (XMLUtils.ifNodeIsElement(rawModelNode, XMLUtils.RAW_MODEL)) {
				Element rawModelElement = (Element) rawModelNode;
				String ID = XMLUtils.getAttributeValue(rawModelNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(rawModelElement, XMLUtils.NAME);
				String file = XMLUtils.getAttributeValue(rawModelElement, XMLUtils.FILE);
				ModelData data = OBJFileLoader.loadOBJ(file);
				RawModel rawModel = new RawModel(name, Loader.getInstance().getVertexLoader()
						.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
						data.getIndices()));
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
	
	private void parseModelTextures(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading model textures...");
		}
		NodeList texturesNodeList = node.getChildNodes();
		for (int j = 0; j < texturesNodeList.getLength(); j++) {
			Node textureNode = texturesNodeList.item(j);
			if (XMLUtils.ifNodeIsElement(textureNode, XMLUtils.MODEL_TEXTURE)) {
				Element textureElement = (Element) textureNode;
				String ID = XMLUtils.getAttributeValue(textureNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(textureElement, XMLUtils.NAME);
				String file = XMLUtils.getAttributeValue(textureElement, XMLUtils.FILE);
				int bufferId = Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_PATH, file);
				ModelTexture texture = new ModelTexture(name, bufferId);
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

	private void parseTerrainTextures(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading terrain textures...");
		}
		NodeList textureList = node.getChildNodes();
		for (int k = 0; k < textureList.getLength(); k++) {
			Node textureNode = textureList.item(k);
			if (XMLUtils.ifNodeIsElement(textureNode, XMLUtils.TERRAIN_TEXTURE)) {
				Element textureElement = (Element) textureNode;
				String ID = XMLUtils.getAttributeValue(textureNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(textureElement, XMLUtils.NAME);
				String file = XMLUtils.getAttributeValue(textureElement, XMLUtils.FILE);
				int bufferId = Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_PATH, file);
				TerrainTexture texture = new TerrainTexture(name, bufferId);
				map.addTerrainTexture(texture);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getTerrainTexture(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}

	private void parseTerrainPacks(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading terrain packs...");
		}
		NodeList packList = node.getChildNodes();
		for (int k = 0; k < packList.getLength(); k++) {
			Node textureNode = packList.item(k);
			if (XMLUtils.ifNodeIsElement(textureNode, XMLUtils.TERRAIN_PACK)) {
				Element packElement = (Element) textureNode;
				String ID = XMLUtils.getAttributeValue(textureNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(packElement, XMLUtils.NAME);
				String baseTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.BASE_TEXTURE);
				TerrainTexture baseTexture = map.getTerrainTexture(baseTextureName);
				String redTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.RED_TEXTURE);
				TerrainTexture redTexture = map.getTerrainTexture(redTextureName);
				String greenTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.GREEN_TEXTURE);
				TerrainTexture greenTexture = map.getTerrainTexture(greenTextureName);
				String blueTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.BLUE_TEXTURE);
				TerrainTexture blueTexture = map.getTerrainTexture(blueTextureName);
				TerrainTexturePack texture = new TerrainTexturePack(name, baseTexture, redTexture, greenTexture, blueTexture);
				map.addTerrainTexturePack(texture);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getTerrainTexturePack(name).getName());
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}
	
	private void parseTexturedModels(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Loading textured models...");
		}
		NodeList nodeList = node.getChildNodes();
		for (int j = 0; j < nodeList.getLength(); j++) {
			Node modelNode = nodeList.item(j);
			if (XMLUtils.ifNodeIsElement(modelNode, XMLUtils.SIMPLE)) {
				parseSimpleTexturedModels(modelNode, map);
			} else if(XMLUtils.ifNodeIsElement(modelNode, XMLUtils.NORMAL)) {
				parseNormalTexturedModels(modelNode, map);
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}
	private void parseSimpleTexturedModels(Node node, IRawManager map) {
		NodeList texturedModelList = node.getChildNodes();
		for (int j = 0; j < texturedModelList.getLength(); j++) {
			Node texturedModelNode = texturedModelList.item(j);
			if (XMLUtils.ifNodeIsElement(texturedModelNode, XMLUtils.TEXTURED_MODEL)) {
				Element texturedModelElement = (Element) texturedModelNode;
				String ID = XMLUtils.getAttributeValue(texturedModelNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.NAME);
				String rawModelName = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.RAW_MODEL);
				String baseTextureName = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.BASE_TEXTURE);
				RawModel rawModel = map.getRawModel(rawModelName);
				ModelTexture texture = map.getModelTexture(baseTextureName);
				TexturedModel model = new TexturedModel(name, rawModel, texture);
				map.addTexturedModel(model);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getTexturedModel(name).getName());
				}
			}
		}
	}
	
	
	private void parseNormalTexturedModels(Node node, IRawManager map) {
		NodeList texturedModelList = node.getChildNodes();
		for (int j = 0; j < texturedModelList.getLength(); j++) {
			Node texturedModelNode = texturedModelList.item(j);
			if (XMLUtils.ifNodeIsElement(texturedModelNode, XMLUtils.TEXTURED_MODEL)) {
				Element texturedModelElement = (Element) texturedModelNode;
				String ID = XMLUtils.getAttributeValue(texturedModelNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.NAME);
				String rawModelName = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.RAW_MODEL);
				String baseTextureName = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.BASE_TEXTURE);
				String normalTextureName = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.NORMAL_TEXTURE);
				String specularTextureName = XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.SPECULAR_TEXTURE);
				float shineDamper = Float.valueOf(XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.SHINE_DAMPER));
				float reflectivity = Float.valueOf(XMLUtils.getAttributeValue(texturedModelElement, XMLUtils.REFLECTIVITY));
				RawModel rawModel = map.getRawModel(rawModelName);
				ModelTexture baseTexture = map.getModelTexture(baseTextureName);
				ModelTexture normalTexture = map.getModelTexture(normalTextureName);
				ModelTexture specularTexture = map.getModelTexture(specularTextureName);
				baseTexture.setNormalMap(normalTexture.getID());
				baseTexture.setSpecularMap(specularTexture.getID());
				baseTexture.setShineDamper(shineDamper);
				baseTexture.setReflectivity(reflectivity);
				TexturedModel model = new TexturedModel(name, rawModel, baseTexture);
				map.addTexturedModel(model);
				if (EngineDebug.hasDebugPermission()) {
					System.out.println(">> " + map.getTexturedModel(name).getName());
				}
			}
		}
	}

}
