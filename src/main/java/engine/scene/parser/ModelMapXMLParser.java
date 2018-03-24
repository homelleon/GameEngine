package scene.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import manager.ObjectMapManager;
import manager.RawManager;
import manager.scene.IObjectManager;
import object.entity.EntityBuilder;
import object.terrain.Terrain;
import object.terrain.TerrainBuilder;
import primitive.buffer.Loader;
import primitive.model.Model;
import primitive.texture.Texture2D;
import primitive.texture.terrain.TerrainTexturePack;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

/**
 * Parser for creating manager of engine models from xml format.
 * 
 * @author homelleon
 * 
 * @see XMLParser
 * @see IObjectParser
 * @see IObjectManager
 */
public class ModelMapXMLParser extends XMLParser implements IObjectParser<IObjectManager> {

	private IObjectManager modelMap;
	private RawManager rawMap;

	public ModelMapXMLParser(Document document, RawManager rawMap) {
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
				EngineDebug.println("Models complete!");
				EngineDebug.printClose("Model map");
				EngineDebug.printBorder();
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used model map file!");
		}

		return modelMap;
	}

	private void parseEntities(Node node, IObjectManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading entities...", 1);
		}
		Node entities = node;
		NodeList entityList = entities.getChildNodes();
		for (int j = 0; j < entityList.getLength(); j++) {
			Node entityNode = entityList.item(j);
			if (XMLUtils.ifNodeIsElement(entityNode, XMLUtils.ENTITY)) {
				Element entityEl = (Element) entityNode;
				String ID = XMLUtils.getAttributeValue(entityNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(entityEl, XMLUtils.NAME);
				String modelName = XMLUtils.getAttributeValue(entityEl, XMLUtils.MODEL);
				float scale = Float.valueOf(XMLUtils.getAttributeValue(entityEl, XMLUtils.SCALE));
				int textureIndex = Integer.valueOf(XMLUtils.getAttributeValue(entityEl, XMLUtils.TEXTURE_INDEX));
				Model[] models = rawMap.getModelGroup(modelName);
				EntityBuilder builder =  new EntityBuilder()
						.setScale(scale)
						.setTextureIndex(textureIndex);
				for(int i = 0; i < models.length; i++) {
					builder.setModel(models[i]);
				}
				map.getEntities().add(builder.build(name));
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getEntities().get(name).getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
	}

	private void parseTerrains(Node node, IObjectManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading terrains...",1);
		}
		NodeList terrainNodeList = node.getChildNodes();
		for (int j = 0; j < terrainNodeList.getLength(); j++) {
			Node terrainsNode = terrainNodeList.item(j);
			if(XMLUtils.ifNodeIsElement(terrainsNode, XMLUtils.TERRAIN)) {
				Element terrainElement = (Element) terrainsNode;
				String ID = XMLUtils.getAttributeValue(terrainElement, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(terrainElement, XMLUtils.NAME);
				String terrainPackName = XMLUtils.getAttributeValue(terrainElement, XMLUtils.TERRAIN_PACK);
				String blendTextureName = XMLUtils.getAttributeValue(terrainElement, XMLUtils.BLEND_TEXTURE);
				String heightTextureName = XMLUtils.getAttributeValue(terrainElement, XMLUtils.HEIGHT_TEXTURE);
				int size = Integer.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.SIZE));
				Float amplitude = Float.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.AMPLITUDE));
				Integer octaves = Integer.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.OCTAVE));
				Float roughness = Float.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.ROUGHTNESS));
				Element positionElement = XMLUtils.getChildElementByTag(terrainElement, XMLUtils.POSITION);
				int x = Integer.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.X));
				int z = Integer.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.Z));
				TerrainTexturePack terrainPack = this.rawMap.getTerrainTexturePack(terrainPackName);
				Texture2D blendTexture = this.rawMap.getTexture(blendTextureName);
				
				TerrainBuilder terrainBuilder = new TerrainBuilder();
				terrainBuilder
					.setSize(size)
					.setXPosition(x)
					.setZPosition(z)
					.setAmplitude(amplitude)
					.setOctaves(octaves)
					.setRoughness(roughness);
				if(heightTextureName!= null) {
					if(!heightTextureName.isEmpty() && !heightTextureName.equals("") && !heightTextureName.equalsIgnoreCase("null")) {
						Texture2D heightMap = Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_HEIGHT_MAP_PATH, heightTextureName);
						terrainBuilder.setHeightMap(heightMap);
					}
				}
				Terrain terrain = terrainBuilder.build(name);
				
				terrain.getMaterial()
					.setTexturePack(terrainPack)
					.setBlendMap(blendTexture);
				
				map.getTerrains().add(terrain);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getTerrains().get(name).getName(),2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
	}

}
