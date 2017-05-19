package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.EntityInterface;
import entities.Entity;
import renderEngine.Loader;
import scene.ES;

public class ObjectMapTXTParser implements ObjectMapParser {

	@Override
	public ObjectMap readMap(String fileName, BufferedReader reader, Loader loader) {
		String line;
		 /* entities */
		List<String> eNames = new ArrayList<String>();
        List<String> eModels = new ArrayList<String>();
        List<String> eTextures = new ArrayList<String>();
        List<String> eTypesString = new ArrayList<String>();
        List<Integer> eTypesInteger = new ArrayList<Integer>();
        
        try {        	
	        while (true) {	      
				line = reader.readLine(); 
				
				/*Read entities*/	       
	        	if (line.startsWith("<e> ")) {
                    String[] currentLine = line.split(" ");
            
                    eNames.add(String.valueOf(currentLine[1]));
                    eModels.add(String.valueOf(currentLine[2]));
                    eTextures.add(String.valueOf(currentLine[3]));
                    eTypesString.add(String.valueOf(currentLine[4]));
	        	}	
	        	
	        	if (line.startsWith("<end>")) {
	        		break;
	        	}	
	        }	      	        
	        reader.close();	 
	        
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }     
        
        ObjectMap map = new ObjectMapSimple(loader);
	        
        for(int i = 0; i < eNames.size(); i++) {
        	switch(eTypesString.get(i)) {
        	case "simple":
        		eTypesInteger.add(ES.ENTITY_TYPE_SIMPLE);
        		break;
        	case "normal":
        		eTypesInteger.add(ES.ENTITY_TYPE_NORMAL);
        		break;
        	case "detail":
        		eTypesInteger.add(ES.ENTITY_TYPE_DETAIL);
        		break;
        	default:
        		throw new IllegalArgumentException("Entity type of defalut object is unknown");
        	}
        	map.loadEntity(eNames.get(i), eModels.get(i), eTextures.get(i));
        }
    
		return map;
	}

}
