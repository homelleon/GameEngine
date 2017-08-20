package map.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import manager.scene.IObjectManager;
import map.objectMap.ObjectMapManager;
import map.raw.IRawManager;
import object.entity.entity.builder.EntityBuilder;
import object.entity.entity.builder.IEntityBuilder;
import object.model.textured.TexturedModel;
import object.terrain.generator.HeightsGenerator;
import object.terrain.terrain.ProceduredTerrain;
import object.terrain.terrain.builder.ITerrainBuilder;
import object.terrain.terrain.builder.ProceduredTerrainBuilder;
import object.texture.terrain.pack.TerrainTexturePack;
import object.texture.terrain.texture.TerrainTexture;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

public class ModelMapXMLParser extends XMLParser implements IObjectParser<IObjectManager> {

	private IObjectManager modelMap;
	private IRawManager rawMap;
	private float[] heights;

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
				System.out.println("Models complete!");
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
				String name = XMLUtils.getAttributeValue(entityEl, XMLUtils.NAME);
				String modelName = XMLUtils.getAttributeValue(entityEl, XMLUtils.TEXTURED_MODEL);
				float scale = Float.valueOf(XMLUtils.getAttributeValue(entityEl, XMLUtils.SCALE));
				TexturedModel model = rawMap.getTexturedModel(modelName);
				IEntityBuilder builder =  new EntityBuilder();
				builder.setModel(model);
				builder.setScale(scale);
				map.getEntities().add(builder.createEntity(name));
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
		NodeList terrainNodeList = node.getChildNodes();
		for (int j = 0; j < terrainNodeList.getLength(); j++) {
			Node terrainsNode = terrainNodeList.item(j);
			if(XMLUtils.ifNodeIsElement(terrainsNode, XMLUtils.PROCEDURE_GENERATED)) {
				NodeList proceduredNodeList = terrainsNode.getChildNodes();
				for(int k = 0; k < proceduredNodeList.getLength(); k++) {
					Node terrainNode = proceduredNodeList.item(k);
					if(XMLUtils.ifNodeIsElement(terrainNode, XMLUtils.TERRAIN)) {
						Element terrainElement = (Element) terrainNode;
						String ID = XMLUtils.getAttributeValue(terrainElement, XMLUtils.ID);
						String name = XMLUtils.getAttributeValue(terrainElement, XMLUtils.NAME);
						String terrainPackName = XMLUtils.getAttributeValue(terrainElement, XMLUtils.TERRAIN_PACK);
						String blendTextureName = XMLUtils.getAttributeValue(terrainElement, XMLUtils.BLEND_TEXTURE);					
						Float amplitude = Float.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.AMPLITUDE));
						Integer octaves = Integer.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.OCTAVE));
						Float roughness = Float.valueOf(XMLUtils.getAttributeValue(terrainElement, XMLUtils.ROUGHTNESS));
						Element positionElement = XMLUtils.getChildElementByTag(terrainElement, XMLUtils.POSITION);
						int x = Integer.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.X));
						int z = Integer.valueOf(XMLUtils.getAttributeValue(positionElement, XMLUtils.Z));
						TerrainTexturePack terrainPack = this.rawMap.getTerrainTexturePack(terrainPackName);
						TerrainTexture blendTexture = this.rawMap.getTerrainTexture(blendTextureName);
						ITerrainBuilder terrainBuilder = new ProceduredTerrainBuilder();
						terrainBuilder
							.setXPosition(x)
							.setZPosition(z)
							.setTexturePack(terrainPack)
							.setBlendTexture(blendTexture)
							.setAmplitude(amplitude)
							.setOctaves(octaves)
							.setRoughness(roughness)
							.setHeights(heights);
						map.getTerrains().add(terrainBuilder.create(name));
						if(map.getTerrains().get(name).getIsProcedureGenerated()) {
							this.heights = new float[128];
							for(int i = 0; i < 128; i++) {
								this.heights[i] = map.getTerrains().get(name).getHeightOfTerrain(127, i);
							}
						}
						if (EngineDebug.hasDebugPermission()) {
							System.out.println(">> " + map.getTerrains().get(name).getName());
						}
					}
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("> Succed!");
		}
	}

}
