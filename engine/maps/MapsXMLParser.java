package maps;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import debug.EngineDebug;
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
		               String ID = entity.getAttributes().getNamedItem(XMLUtils.ID).getNodeValue();
		               String name = entityEl.getElementsByTagName(XMLUtils.NAME).item(0).getChildNodes().item(0).getNodeValue();
		               String model = entityEl.getElementsByTagName(XMLUtils.MODEL).item(0).getChildNodes().item(0).getNodeValue();
		               String texture = entityEl.getElementsByTagName(XMLUtils.TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
		               float x = Float.valueOf(entityEl.getElementsByTagName(XMLUtils.X).item(0).getChildNodes().item(0).getNodeValue());
		               float y = Float.valueOf(entityEl.getElementsByTagName(XMLUtils.Y).item(0).getChildNodes().item(0).getNodeValue());
		               float z = Float.valueOf(entityEl.getElementsByTagName(XMLUtils.Z).item(0).getChildNodes().item(0).getNodeValue());
		               Vector3f position = new Vector3f(x, y, z);
		               float scale = Float.valueOf(entityEl.getElementsByTagName(XMLUtils.SCALE).item(0).getChildNodes().item(0).getNodeValue());
		               boolean isNormal = Boolean.valueOf(entityEl.getElementsByTagName(XMLUtils.NORMAL).item(0).getChildNodes().item(0).getNodeValue());
		               if (isNormal) {
		            	   String normalMap = entityEl.getElementsByTagName(XMLUtils.NORMAL_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
		            	   String specularMap = entityEl.getElementsByTagName(XMLUtils.SPECULAR_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
		            	   float shine = Float.valueOf(entityEl.getElementsByTagName(XMLUtils.SHINE_DUMPER).item(0).getChildNodes().item(0).getNodeValue());
		            	   float reflectivity = Float.valueOf(entityEl.getElementsByTagName(XMLUtils.REFLECTIVITY).item(0).getChildNodes().item(0).getNodeValue());
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
               String ID = terrain.getAttributes().getNamedItem(XMLUtils.ID).getNodeValue();
               String name = terrainEl.getElementsByTagName(XMLUtils.NAME).item(0).getChildNodes().item(0).getNodeValue();			               
               float x = Float.valueOf(terrainEl.getElementsByTagName(XMLUtils.X).item(0).getChildNodes().item(0).getNodeValue());
               float y = Float.valueOf(terrainEl.getElementsByTagName(XMLUtils.Y).item(0).getChildNodes().item(0).getNodeValue());
               Vector2f position = new Vector2f(x,y);
               String baseTexture = terrainEl.getElementsByTagName(XMLUtils.BASE_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
               String redTexture = terrainEl.getElementsByTagName(XMLUtils.RED_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
               String greenTexture = terrainEl.getElementsByTagName(XMLUtils.GREEN_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
               String blueTexture = terrainEl.getElementsByTagName(XMLUtils.BLUE_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
               String blendTexture = terrainEl.getElementsByTagName(XMLUtils.BLEND_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
               Boolean isProcedured = Boolean.valueOf(terrainEl.getElementsByTagName(XMLUtils.PROCEDURE_GENERATED).item(0).getChildNodes().item(0).getNodeValue());
               if(isProcedured) {
	               Float amplitude = Float.valueOf(terrainEl.getElementsByTagName(XMLUtils.AMPLITUDE).item(0).getChildNodes().item(0).getNodeValue());
	               Integer octave = Integer.valueOf(terrainEl.getElementsByTagName(XMLUtils.OCTAVE).item(0).getChildNodes().item(0).getNodeValue());
	               Float roughness = Float.valueOf(terrainEl.getElementsByTagName(XMLUtils.ROUGHTNESS).item(0).getChildNodes().item(0).getNodeValue());
	               map.createTerrain(name, position, baseTexture, redTexture, greenTexture, blueTexture, blendTexture, amplitude, octave, roughness);
               } else {
            	   String heightMap = terrainEl.getElementsByTagName(XMLUtils.HEIGHT_TEXTURE).item(0).getChildNodes().item(0).getNodeValue();
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
