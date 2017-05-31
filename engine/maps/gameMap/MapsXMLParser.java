package maps.gameMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.debug.EngineDebug;
import toolbox.XMLUtils;

public class MapsXMLParser implements MapsParser {

	@Override
	public GameMap readMap (Document document, GameMap map) {        
        
        NodeList nodeList = document.getDocumentElement().getChildNodes();        
        
        for (int i = 0; i < nodeList.getLength(); i++) {
           Node node = nodeList.item(i);
           if (XMLUtils.ifNodeIsElement(node, XMLUtils.ENTITIES)) {
        	   createEntities(node, map);
	        } else if (XMLUtils.ifNodeIsElement(node, XMLUtils.TERRAINS)) {
	        	createTerrains(node, map);
	        }	        	
        }
        if(EngineDebug.hasDebugPermission()) {
        	System.out.println("Loading complete!");
        }
		
		return map;
	}
	
	private void createEntities(Node node, GameMap map) {
		if(EngineDebug.hasDebugPermission()) {
		   System.out.println("Loading entities...");
	   	}
	    Node entities = node;
	    NodeList entityList = entities.getChildNodes();        		   
	    for(int j = 0; j < entityList.getLength(); j++) {
			   Node entity = entityList.item(j);	        		   
			   if (XMLUtils.ifNodeIsElement(entity, XMLUtils.ENTITY)) {
					   Element entityEl = (Element) entity;
		               String ID = XMLUtils.getAttributeValue(entity, XMLUtils.ID);
		               String name = XMLUtils.getTagValue(entityEl, XMLUtils.NAME);		            		   
		               String model = XMLUtils.getTagValue(entityEl, XMLUtils.MODEL);
		               String texture = XMLUtils.getTagValue(entityEl, XMLUtils.TEXTURE);
		               float x = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.X));
		               float y = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.Y));
		               float z = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.Z));
		               Vector3f position = new Vector3f(x, y, z);
		               float scale = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.SCALE));
		               boolean isNormal = Boolean.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL));
		               if (isNormal) {
		            	   String normalMap = XMLUtils.getTagValue(entityEl, XMLUtils.NORMAL_TEXTURE);
		            	   String specularMap = XMLUtils.getTagValue(entityEl, XMLUtils.SPECULAR_TEXTURE);
		            	   float shine = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.SHINE_DUMPER));
		            	   float reflectivity = Float.valueOf(XMLUtils.getTagValue(entityEl, XMLUtils.REFLECTIVITY));
		            	   map.createEntity(name, model, 
		   						texture, normalMap, specularMap,
		   						position, 0, 0, 0, scale, 
		   						shine, reflectivity);
		               } else {
		            	   map.createEntity(name, model, 
		            			   texture, position, 0, 0, 0, scale);		            	   
		               }
		               if(EngineDebug.hasDebugPermission()) {
		            	   System.out.println(map.getEntities().get(name).getName());
		               }
		   		 }
		   }
		   if(EngineDebug.hasDebugPermission()) {
			   System.out.println("Succed!"); 
		   }
	}
	
	private void createTerrains(Node node, GameMap map) {
		if(EngineDebug.hasDebugPermission()) {
    		System.out.println("Loading terrains...");
    	}
    	Node terrains = node;
    	NodeList terrainList = terrains.getChildNodes();
    	for(int j = 0; j < terrainList.getLength(); j++) {
		   Node terrain = terrainList.item(j);	        		   
		   if (XMLUtils.ifNodeIsElement(terrain, XMLUtils.TERRAIN)) {
			   Element terrainEl = (Element) terrain;
               String ID = XMLUtils.getAttributeValue(terrain, XMLUtils.ID);
               String name = XMLUtils.getTagValue(terrainEl, XMLUtils.NAME);			               
               float x = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.X));
               float y = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.Y));
               Vector2f position = new Vector2f(x,y);
               String baseTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BASE_TEXTURE);
               String redTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.RED_TEXTURE);
               String greenTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.GREEN_TEXTURE);
               String blueTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BLUE_TEXTURE);
               String blendTexture = XMLUtils.getTagValue(terrainEl, XMLUtils.BLEND_TEXTURE);
               Boolean isProcedured = Boolean.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.PROCEDURE_GENERATED));
               if(isProcedured) {
	               Float amplitude = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.AMPLITUDE));
	               Integer octave = Integer.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.OCTAVE));
	               Float roughness = Float.valueOf(XMLUtils.getTagValue(terrainEl, XMLUtils.ROUGHTNESS));
	               map.createTerrain(name, position, baseTexture, redTexture, greenTexture, blueTexture, blendTexture, amplitude, octave, roughness);
               } else {
            	   String heightMap = XMLUtils.getTagValue(terrainEl, XMLUtils.HEIGHT_TEXTURE);
            	   map.createTerrain(name, position, baseTexture, redTexture, greenTexture, blueTexture, blendTexture, heightMap);
               }
               if(EngineDebug.hasDebugPermission()) {
            	   System.out.println(map.getTerrains().get(name).getName());
               }
		   }
    	}    
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Succed!");
		}
	}

}
