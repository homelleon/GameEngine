package map.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import map.raw.IRawManager;
import map.raw.RawManager;
import object.texture.Texture2D;
import object.texture.material.Material;
import object.texture.terrain.TerrainTexturePack;
import primitive.model.Mesh;
import primitive.model.Model;
import primitive.model.meshLoader.objloader.OBJLoader;
import tool.math.vector.Vector3f;
import tool.xml.XMLUtils;
import tool.xml.parser.IObjectParser;
import tool.xml.parser.XMLParser;

/**
 * Parser for creating raw object manager from xml format.
 * 
 * @author homelleon
 * 
 * @see XMLParser
 * @see IObjectParser
 * @see IRawManager
 * @see RawManaer
 */
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
				if (XMLUtils.ifNodeIsElement(node, XMLUtils.TEXTURES)) {
					parseTextures(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.MATERIALS)) {
					parseMaterials(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TERRAIN_PACKS)) {
					parseTerrainPacks(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.MESHES)) {
					parseMeshes(node, map);
				} else if (XMLUtils.ifNodeIsElement(node, XMLUtils.MODELS)) {
					parseModels(node, map);
				} 
			}
			if (EngineDebug.hasDebugPermission()) {
				EngineDebug.println("Raws complete!");
				EngineDebug.printClose("Raw map");
				EngineDebug.printBorder();
			}
		} else {
			throw new NullPointerException("Incorrect parent element name of used model map file!");
		}

		return map;
	}
	
	private void parseMeshes(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading meshes...", 1);
		}
		NodeList meshList = node.getChildNodes();
		for (int j = 0; j < meshList.getLength(); j++) {
			Node meshNode = meshList.item(j);
			if (XMLUtils.ifNodeIsElement(meshNode, XMLUtils.MESH)) {
				Element meshElement = (Element) meshNode;
				String ID = XMLUtils.getAttributeValue(meshNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(meshElement, XMLUtils.NAME);
				String file = XMLUtils.getAttributeValue(meshElement, XMLUtils.FILE);
				String type = XMLUtils.getAttributeValue(meshElement, XMLUtils.TYPE);
				
				OBJLoader objLoader = type.equals("normal") ? new OBJLoader(true) : new OBJLoader();
				Mesh[] meshesBase = objLoader.load(EngineSettings.MODEL_PATH, file, null);
				objLoader.clean();
				
				Mesh[] meshes = new Mesh[meshesBase.length];
				for(int i=0; i < meshesBase.length; i++) {
					meshes[i] = meshesBase[i].clone(name);
				}
				
				map.addMeshGroup(meshes);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getMeshGroup(name)[0].getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
	}
	
	private void parseTextures(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading textures...", 1);
		}
		NodeList texturesNodeList = node.getChildNodes();
		for (int j = 0; j < texturesNodeList.getLength(); j++) {
			Node textureNode = texturesNodeList.item(j);
			if (XMLUtils.ifNodeIsElement(textureNode, XMLUtils.TEXTURE)) {
				Element textureElement = (Element) textureNode;
				String ID = XMLUtils.getAttributeValue(textureNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(textureElement, XMLUtils.NAME);
				String file = XMLUtils.getAttributeValue(textureElement, XMLUtils.FILE);
				int numberOfRows = Integer.valueOf(XMLUtils.getAttributeValue(textureElement, XMLUtils.ROWS));
				Texture2D texture = new Texture2D(name, EngineSettings.TEXTURE_PATH + file + ".png");
				texture.setNumberOfRows(numberOfRows);
				map.addTexture(texture);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getTexture(name).getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
		
	}
	
	private void parseMaterials(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading materials...", 1);
		}
		NodeList materialList = node.getChildNodes();
		for (int k = 0; k < materialList.getLength(); k++) {
			Node materialNode = materialList.item(k);
			if (XMLUtils.ifNodeIsElement(materialNode, XMLUtils.MATERIAL)) {
				Element materialElement = (Element) materialNode;
				String ID = XMLUtils.getAttributeValue(materialNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(materialElement, XMLUtils.NAME);
				String diffuseMapName = XMLUtils.getAttributeValue(materialElement, XMLUtils.DIFFUSE_MAP);
				String normalMapName = XMLUtils.getAttributeValue(materialElement, XMLUtils.NORMAL_MAP);
				String displaceMapName = XMLUtils.getAttributeValue(materialElement, XMLUtils.DISPLACE_MAP);
				String ambientMapName = XMLUtils.getAttributeValue(materialElement, XMLUtils.AMBIENT_MAP);
				String specularMapName = XMLUtils.getAttributeValue(materialElement, XMLUtils.SPECULAR_MAP);
				String alphaMapName = XMLUtils.getAttributeValue(materialElement, XMLUtils.ALPHA_MAP);				
				Element colorElement = XMLUtils.getChildElementByTag(materialElement, XMLUtils.COLOR);
				int r = Integer.valueOf(XMLUtils.getAttributeValue(colorElement, XMLUtils.RED));
				int g = Integer.valueOf(XMLUtils.getAttributeValue(colorElement, XMLUtils.GREEN));
				int b = Integer.valueOf(XMLUtils.getAttributeValue(colorElement, XMLUtils.BLUE));
				float shininess = Float.valueOf(XMLUtils.getAttributeValue(materialElement, XMLUtils.SHININESS));
				float reflectivity = Float.valueOf(XMLUtils.getAttributeValue(materialElement, XMLUtils.REFLECTIVITY));
				Texture2D diffuseMap = null;
				Texture2D normalMap = null;
				Texture2D displaceMap = null;
				Texture2D ambientMap = null;
				Texture2D specularMap = null;
				Texture2D alphaMap = null;
				if(!diffuseMapName.equals("null"))
					diffuseMap = map.getTexture(diffuseMapName);
				if(!normalMapName.equals("null"))
					normalMap = map.getTexture(normalMapName);
				if(!displaceMapName.equals("null"))
					displaceMap = map.getTexture(displaceMapName);
				if(!ambientMapName.equals("null"))
					ambientMap = map.getTexture(ambientMapName);
				if(!specularMapName.equals("null"))
					specularMap = map.getTexture(specularMapName);
				if(!alphaMapName.equals("null"))
					alphaMap = map.getTexture(alphaMapName);
				
				
				Material material = new Material(name, diffuseMap);
				material.setNormalMap(normalMap);
				material.setDisplaceMap(displaceMap);
				material.setAmbientMap(ambientMap);
				material.setSpecularMap(specularMap);
				material.setAlphaMap(alphaMap);
				material.setShininess(shininess);
				material.setReflectivity(reflectivity);
				material.setColor(new Vector3f(r,g,b));
				map.addMaterial(material);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getMaterial(name).getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
	}

	private void parseTerrainPacks(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading terrain packs...", 1);
		}
		NodeList packList = node.getChildNodes();
		for (int k = 0; k < packList.getLength(); k++) {
			Node textureNode = packList.item(k);
			if (XMLUtils.ifNodeIsElement(textureNode, XMLUtils.TERRAIN_PACK)) {
				Element packElement = (Element) textureNode;
				String ID = XMLUtils.getAttributeValue(textureNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(packElement, XMLUtils.NAME);
				String baseTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.BASE_TEXTURE);
				
				String redTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.RED_TEXTURE);
				String greenTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.GREEN_TEXTURE);
				String blueTextureName = XMLUtils.getAttributeValue(packElement, XMLUtils.BLUE_TEXTURE);
				
				Texture2D baseTexture = map.getTexture(baseTextureName);
				Texture2D redTexture = map.getTexture(redTextureName);
				Texture2D blueTexture = map.getTexture(blueTextureName);
				Texture2D greenTexture = map.getTexture(greenTextureName);
				
				TerrainTexturePack texture = new TerrainTexturePack(name, baseTexture, redTexture, greenTexture, blueTexture);
				map.addTerrainTexturePack(texture);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getTerrainTexturePack(name).getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
	}
	
	private void parseModels(Node node, IRawManager map) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading models...", 1);
		}
		NodeList modelList = node.getChildNodes();
		for (int j = 0; j < modelList.getLength(); j++) {
			Node modelNode = modelList.item(j);
			if (XMLUtils.ifNodeIsElement(modelNode, XMLUtils.MODEL)) {
				Element modelElement = (Element) modelNode;
				String ID = XMLUtils.getAttributeValue(modelNode, XMLUtils.ID);
				String name = XMLUtils.getAttributeValue(modelElement, XMLUtils.NAME);
				String meshName = XMLUtils.getAttributeValue(modelElement, XMLUtils.MESH);
				String materialName = XMLUtils.getAttributeValue(modelElement, XMLUtils.MATERIAL);
				int meshGroupSize = map.getMeshGroup(meshName).length;
				Mesh[] meshes = new Mesh[meshGroupSize];
				for(int i = 0; i < meshGroupSize; i++) {
					meshes[i] = map.getMeshGroup(meshName)[i].clone(meshName);
				}
				Material material = map.getMaterial(materialName).clone(materialName);
				Model[] models = new Model[meshGroupSize];
				for(int i = 0; i < meshGroupSize; i++) {
					models[i] = new Model(name, meshes[i], material);
				}
				map.addModelGroup(models);
				if (EngineDebug.hasDebugPermission()) {
					EngineDebug.println(map.getModelGroup(name)[0].getName(), 2);
				}
			}
		}
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Succed!", 1);
		}
	}

}
