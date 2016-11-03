package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.Source;
import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;
import scene.SceneObjectTools;
import terrains.Terrain;

public class MapParser implements MapReadable {

	@Override
	public GameMap readMap(String fileName, BufferedReader reader, Loader loader) {	
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
        	Entity entity = new Entity(eNames.get(i), model, eCoords.get(i), 0, 0, 0, eScales.get(i));
        	entities.add(entity);
        }
        
        //*Create terrains*//
        List<Terrain> terrains = new ArrayList<Terrain>();
        
        for(int i=0;i<tNames.size();i++) {
        	if (tProcGens.get(i)) {
        		Terrain terrain = SceneObjectTools.createMultiTexTerrain(tNames.get(i), (int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i), tAmplitudes.get(i), tOctaves.get(i), 
	        			tRoughnesses.get(i), loader);
	        	terrains.add(terrain);

        	} else {
        		Terrain terrain = SceneObjectTools.createMultiTexTerrain(tNames.get(i),  (int) tCoords.get(i).x, 
	        			(int) tCoords.get(i).y, tBaseTexs.get(i), trTexs.get(i), tgTexs.get(i), 
	        			tbTexs.get(i), tBlends.get(i),tHeights.get(i), loader);
        		terrains.add(terrain);
        	}
        }
        
        //*Create audios*//
        List<Source> audios = new ArrayList<Source>();    
        
        for(int i=0;i<audios.size();i++) {
        	Source source = new Source(aNames.get(i), aPaths.get(i), aMaxDistances.get(i), aCoords.get(i));
        	audios.add(source);
        }
        
		GameMap map = new GameMap(fileName, loader);
     
		map.setEntities(entities);
		map.setTerrains(terrains);
		map.setAudios(audios);
		
		return map;
	}

}