package engine.maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import engine.entities.Entity;
import engine.models.TexturedModel;
import engine.renderEngine.Loader;
import engine.scene.SceneObjectTools;
import engine.scene.Settings;

public class MapLoader {
	
	private final static String ENTITY_POINTER = "ep ";
	private final static String NORMAL_POINTER = "np ";
	private final static String TERRAIN_POINTER = "tr ";
	private final static String END_POINTER = "f ";
	
	
	public static Map loadMap(String fileName, Loader loader){
		FileReader isr = null;
        File mapFile = new File(Settings.MAP_PATH + fileName + ".mp");
	        
        try {
            isr = new FileReader(mapFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res; don't use any extention");
        }
        BufferedReader reader = new BufferedReader(isr);
        String line;
	        
        List<String> names = new ArrayList<String>();
        List<String> models = new ArrayList<String>();
        List<String> textures = new ArrayList<String>();
        List<Vector3f> coords = new ArrayList<Vector3f>();
        List<Float> scales = new ArrayList<Float>();  
        List<String> types = new ArrayList<String>();
        try {
	        while (true) {
	        	
				line = reader.readLine();
	       
	        	if (line.startsWith(ENTITY_POINTER)) {
	        		
                    String[] currentLine = line.split(" ");
                    
                    names.add(String.valueOf(currentLine[1]));
                    models.add(String.valueOf(currentLine[2]));
                    textures.add(String.valueOf(currentLine[3]));
                    Vector3f coord = new Vector3f((float) Float.valueOf(currentLine[4]),
                            (float) Float.valueOf(currentLine[5]),
                            (float) Float.valueOf(currentLine[6]));
                    coords.add(coord);
                    scales.add(Float.valueOf(currentLine[7]));
                    types.add(String.valueOf(currentLine[8]));      
	        	}
	        	 reader.close();
	        }
	     
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }

        List<Entity> entities = new ArrayList<Entity>();
        for(int i=0;i<names.size();i++){
        	TexturedModel model = SceneObjectTools.loadStaticModel(models.get(i), textures.get(i), loader);
        	entities.add(new Entity(names.get(i), model, coords.get(i), 0, 0, 0, scales.get(i)));
        }
        
		Map map = new Map();
		map.entities = entities;
		
		return map;
	}

}
