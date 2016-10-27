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

import engine.audio.AudioMaster;
import engine.audio.Source;
import engine.entities.Entity;
import engine.models.TexturedModel;
import engine.renderEngine.Loader;
import engine.scene.SceneObjectTools;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class MapFileLoader {
	
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
        List<Boolean> eTypes = new ArrayList<Boolean>();
        
        List<String> tNames = new ArrayList<String>();
        List<Vector2f> tCoords = new ArrayList<Vector2f>();
        List<String> tBaseTexs = new ArrayList<String>();
        List<String> trTexs = new ArrayList<String>();
        List<String> tgTexs = new ArrayList<String>();
        List<String> tbTexs = new ArrayList<String>();
        List<String> tBlends = new ArrayList<String>();
        List<Boolean> tProcGens = new ArrayList<Boolean>();
        List<String> tHeights = new ArrayList<String>();
        List<Float> tAmplitudes = new ArrayList<Float>();
        List<Integer> tOctaves = new ArrayList<Integer>();
        List<Float> tRoughnesses = new ArrayList<Float>();
        
        List<String> aNames = new ArrayList<String>();
        List<String> aPaths = new ArrayList<String>();
        List<Vector3f> aCoords = new ArrayList<Vector3f>();
        List<Integer> aMaxDistances = new ArrayList<Integer>();
        
        try {        	
	        while (true) {	      
				line = reader.readLine(); 
				
				/*Read entities*/	       
	        	if (line.startsWith("<e> ")) {
                    String[] currentLine = line.split(" ");
                    System.out.println("entity");
            
                    eNames.add(String.valueOf(currentLine[1]));
                    eModels.add(String.valueOf(currentLine[2]));
                    eTextures.add(String.valueOf(currentLine[3]));
                    eCoords.add(new Vector3f((float) Float.valueOf(currentLine[4]),
                            (float) Float.valueOf(currentLine[5]),
                            (float) Float.valueOf(currentLine[6])));
                    eScales.add(Float.valueOf(currentLine[7]));
                    eTypes.add(Boolean.valueOf(currentLine[8]));
	        	}
	        	
	        	/*Read normal mapped entities*/
	        	//TODO:Implement reader
	        	
	        	/*Read terrains*/
	        	if (line.startsWith("<t> ")) {
                    String[] currentLine = line.split(" ");
            
                    tNames.add(String.valueOf(currentLine[1]));
                    tCoords.add(new Vector2f((int) Integer.valueOf(currentLine[2]),
                    		(int) Integer.valueOf(currentLine[3])));
                    tBaseTexs.add(String.valueOf(currentLine[4]));
                    trTexs.add(String.valueOf(currentLine[5]));
                    tgTexs.add(String.valueOf(currentLine[6]));
                    tbTexs.add(String.valueOf(currentLine[7])); 
                    tBlends.add(String.valueOf(currentLine[8]));
                    tProcGens.add(Boolean.valueOf(currentLine[9]));
                    if(Boolean.valueOf(currentLine[9])){
                    	tAmplitudes.add(Float.valueOf(currentLine[10]));
                    	tOctaves.add(Integer.valueOf(currentLine[11]));
                    	tRoughnesses.add(Float.valueOf(currentLine[12]));
                    } else {
                    	tHeights.add(String.valueOf(currentLine[10]));
                    }  
	        	}
	        	
	        	/*Read audio loops*/
	        	if (line.startsWith("<a> ")) {
	        		String[] currentLine = line.split(" ");
	        		aNames.add(String.valueOf(currentLine[1]));
	        		aPaths.add(String.valueOf(currentLine[2]));
	        		aCoords.add(new Vector3f((Float) Float.valueOf(currentLine[3]),
	        				 (Float) Float.valueOf(currentLine[4]),
	        				 (Float) Float.valueOf(currentLine[5])));
	        		aMaxDistances.add((int)Integer.valueOf(currentLine[6]));	 
	        	}
	        	
	        	/*Read water planes*/
	        	//TODO:Implement reader
	        	
	        	if (line.startsWith("<end>")) {
	        		break;
	        	}	
	        }	      
	        
	        reader.close();	  
	        
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }
        
        //*Create enities*//
        List<Entity> entities = new ArrayList<Entity>();
        
        for(int i=0;i<eNames.size();i++) {
        	TexturedModel model = SceneObjectTools.loadStaticModel(eModels.get(i), eTextures.get(i), loader);
        	entities.add(new Entity(eNames.get(i), model, eCoords.get(i), 0, 0, 0, eScales.get(i)));
        }
        
        //*Create terrains*//
        List<Terrain> terrains = new ArrayList<Terrain>();
        
        for(int i=0;i<tNames.size();i++) {
        	if (tProcGens.get(i)) {
	        	terrains.add(SceneObjectTools.createMultiTexTerrain((int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i), tAmplitudes.get(i), tOctaves.get(i), 
	        			tRoughnesses.get(i), loader));

        	} else {
        		terrains.add(SceneObjectTools.createMultiTexTerrain((int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i),tHeights.get(i), loader));
        	}
        }
        
        //*Create audios*//
        List<Source> audios = new ArrayList<Source>();    
        
        for(int i=0;i<audios.size();i++) {      	
        	audios.add(new Source(aNames.get(i), aPaths.get(i), aMaxDistances.get(i), aCoords.get(i)));
        }
        
		GameMap map = new GameMap();
		map.entities = entities;
		map.terrains = terrains;
		map.audios = audios;
		
		return map;
	}

}
