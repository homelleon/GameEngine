package maps;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import debug.EngineDebug;
import toolbox.EngineUtils;
import toolbox.XMLUtils;

public class MapsXMLParser implements MapsParser {

	@Override
	public GameMap readMap (Document document, GameMap map) {        
        
        NodeList nodeList = document.getDocumentElement().getChildNodes();        
        
        for (int i = 0; i < nodeList.getLength(); i++) {
           Node node = nodeList.item(i);
           if (XMLUtils.ifNodeIsElement(node, "entities")) {
        	   createEntities(node, map);
	        } else if (XMLUtils.ifNodeIsElement(node, "terrains")) {
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
			   if (XMLUtils.ifNodeIsElement(entity, "entity")) {
					   Element entityEl = (Element) entity;
		               String ID = entity.getAttributes().getNamedItem("id").getNodeValue();
		               String name = entityEl.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
		               String model = entityEl.getElementsByTagName("model").item(0).getChildNodes().item(0).getNodeValue();
		               String texture = entityEl.getElementsByTagName("texture").item(0).getChildNodes().item(0).getNodeValue();
		               float x = Float.valueOf(entityEl.getElementsByTagName("x").item(0).getChildNodes().item(0).getNodeValue());
		               float y = Float.valueOf(entityEl.getElementsByTagName("y").item(0).getChildNodes().item(0).getNodeValue());
		               float z = Float.valueOf(entityEl.getElementsByTagName("z").item(0).getChildNodes().item(0).getNodeValue());
		               Vector3f position = new Vector3f(x,y,z);
		               float scale = Float.valueOf(entityEl.getElementsByTagName("scale").item(0).getChildNodes().item(0).getNodeValue());
		               boolean isNormal = Boolean.valueOf(entityEl.getElementsByTagName("normal").item(0).getChildNodes().item(0).getNodeValue());
		               if (isNormal) {
		            	   String normalMap = entityEl.getElementsByTagName("normal_map").item(0).getChildNodes().item(0).getNodeValue();
		            	   String specularMap = entityEl.getElementsByTagName("specular_map").item(0).getChildNodes().item(0).getNodeValue();
		            	   float shine = Float.valueOf(entityEl.getElementsByTagName("shine_dumper").item(0).getChildNodes().item(0).getNodeValue());
		            	   float reflectivity = Float.valueOf(entityEl.getElementsByTagName("reflectivity").item(0).getChildNodes().item(0).getNodeValue());
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
		   if (XMLUtils.ifNodeIsElement(terrain, "terrain")) {
			   Element terrainEl = (Element) terrain;
               String ID = terrain.getAttributes().getNamedItem("id").getNodeValue();
               String name = terrainEl.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();			               
               float x = Float.valueOf(terrainEl.getElementsByTagName("x").item(0).getChildNodes().item(0).getNodeValue());
               float y = Float.valueOf(terrainEl.getElementsByTagName("y").item(0).getChildNodes().item(0).getNodeValue());
               Vector2f position = new Vector2f(x,y);
               String baseTexture = terrainEl.getElementsByTagName("base_texture").item(0).getChildNodes().item(0).getNodeValue();
               String redTexture = terrainEl.getElementsByTagName("red_texture").item(0).getChildNodes().item(0).getNodeValue();
               String greenTexture = terrainEl.getElementsByTagName("green_texture").item(0).getChildNodes().item(0).getNodeValue();
               String blueTexture = terrainEl.getElementsByTagName("blue_texture").item(0).getChildNodes().item(0).getNodeValue();
               String blendTexture = terrainEl.getElementsByTagName("blend_texture").item(0).getChildNodes().item(0).getNodeValue();
               Boolean isProcedured = Boolean.valueOf(terrainEl.getElementsByTagName("procedure_generated").item(0).getChildNodes().item(0).getNodeValue());
               if(isProcedured) {
	               Float amplitude = Float.valueOf(terrainEl.getElementsByTagName("amplitude").item(0).getChildNodes().item(0).getNodeValue());
	               Integer octave = Integer.valueOf(terrainEl.getElementsByTagName("octave").item(0).getChildNodes().item(0).getNodeValue());
	               Float roughness = Float.valueOf(terrainEl.getElementsByTagName("roughness").item(0).getChildNodes().item(0).getNodeValue());
	               map.createTerrain(name, position, baseTexture, redTexture, greenTexture, blueTexture, blendTexture, amplitude, octave, roughness);
               } else {
            	   String heightMap = terrainEl.getElementsByTagName("height_texture").item(0).getChildNodes().item(0).getNodeValue();
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
