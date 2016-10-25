package engine.maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engine.entities.Entity;
import engine.models.TexturedModel;
import engine.renderEngine.Loader;
import engine.scene.SceneObjectTools;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class MapFileLoader {
	
	private final static String ENTITY_POINTER = "<e> ";
	private final static String NORMAL_POINTER = "<n> ";
	private final static String TERRAIN_POINTER = "<t> ";
	private final static String END_POINTER = "<end>";
	
	
	public static GameMap loadMap(String fileName, Loader loader) {
		FileReader isr = null;
        File mapFile = new File(Settings.MAP_PATH + fileName + ".txt");
	        
        try {
            isr = new FileReader(mapFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res; don't use any extention");
        }
        BufferedReader reader = new BufferedReader(isr);
        String line;
	        
        List<String> eNames = new ArrayList<String>();
        List<String> eModels = new ArrayList<String>();
        List<String> eTextures = new ArrayList<String>();
        List<Vector3f> eCoords = new ArrayList<Vector3f>();
        List<Float> eScales = new ArrayList<Float>();  
        List<String> eTypes = new ArrayList<String>();
        
        List<String> tNames = new ArrayList<String>();
        List<Vector2f> tCoords = new ArrayList<Vector2f>();
        List<String> tBaseTexs = new ArrayList<String>();
        List<String> trTexs = new ArrayList<String>();
        List<String> tgTexs = new ArrayList<String>();
        List<String> tbTexs = new ArrayList<String>();
        List<String> tBlends = new ArrayList<String>();
        List<Boolean> tProcGens = new ArrayList<Boolean>();
        List<String> tHeights = new ArrayList<String>();
        
        try {        	
	        while (true) {	      
				line = reader.readLine(); 
				
				/*Read entities*/	       
	        	if (line.startsWith(ENTITY_POINTER)) {
                    String[] currentLine = line.split(" ");
            
                    eNames.add(String.valueOf(currentLine[1]));
                    eModels.add(String.valueOf(currentLine[2]));
                    eTextures.add(String.valueOf(currentLine[3]));
                    Vector3f coord = new Vector3f((float) Float.valueOf(currentLine[4]),
                            (float) Float.valueOf(currentLine[5]),
                            (float) Float.valueOf(currentLine[6]));
                    eCoords.add(coord);
                    eScales.add(Float.valueOf(currentLine[7]));
                    eTypes.add(String.valueOf(currentLine[8]));   
	        	}
	        	
	        	/*Read normal mapped entities*/
	        	//TODO:Implement reader
	        	
	        	/*Read terrains*/
	        	if (line.startsWith(TERRAIN_POINTER)) {
                    String[] currentLine = line.split(" ");
            
                    tNames.add(String.valueOf(currentLine[1]));
                    Vector2f coord = new Vector2f((int) Integer.valueOf(currentLine[2]),
                    		(int) Integer.valueOf(currentLine[3]));
                    tCoords.add(coord);
                    tBaseTexs.add(String.valueOf(currentLine[4]));
                    trTexs.add(String.valueOf(currentLine[5]));
                    tgTexs.add(String.valueOf(currentLine[6]));
                    tbTexs.add(String.valueOf(currentLine[7])); 
                    tBlends.add(String.valueOf(currentLine[8]));
                    tProcGens.add(Boolean.valueOf(currentLine[9]));
                    tHeights.add(String.valueOf(currentLine[10]));
                    System.out.println("map");   
	        	}
	        	
	        	if (line.startsWith(END_POINTER)) {
	        		break;
	        	}
	        	
	        	/*Read water planes*/
	        	//TODO:Implement reader
	        }
	      
	        reader.close();
	     
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }

        List<Entity> entities = new ArrayList<Entity>();
        
        for(int i=0;i<eNames.size();i++) {
        	TexturedModel model = SceneObjectTools.loadStaticModel(eModels.get(i), eTextures.get(i), loader);
        	entities.add(new Entity(eNames.get(i), model, eCoords.get(i), 0, 0, 0, eScales.get(i)));
        }
        
        List<Terrain> terrains = new ArrayList<Terrain>();
        
        for(int i=0;i<tNames.size();i++) {
        	if (tProcGens.get(i)) {
	        	terrains.add(SceneObjectTools.createMultiTexTerrain((int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i), 1f, 1, 1f, loader));
        	} else {
        		//TODO: Implement HeightMap generated terrain 
        	}
        }
        
		GameMap map = new GameMap();
		map.entities = entities;
		map.terrains = terrains;
		
		return map;
	}

}
