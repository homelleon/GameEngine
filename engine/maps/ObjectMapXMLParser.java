package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import renderEngine.Loader;
import scene.ES;
import toolbox.EngineUtils;

public class ObjectMapXMLParser implements ObjectMapParser {

	@Override
	public ObjectMapInterface readMap(String fileName, BufferedReader reader, Loader loader) {
		String line;
		 /* entities */
		List<String> eNames = new ArrayList<String>();
        List<String> eModels = new ArrayList<String>();
        List<String> eTextures = new ArrayList<String>();
        List<String> eTypesString = new ArrayList<String>();
        List<Integer> eTypesInteger = new ArrayList<Integer>();

        try {
	        while ((line = reader.readLine()) != null) {
				/*Read entities*/
				
				if (line.startsWith(ES.XML_ENTITY_BEGIN) &&
	        			line.endsWith(ES.XML_ENTITY_END)) {
	        		line = EngineUtils.pullLineFromWords(
        				line, ES.XML_ENTITY_BEGIN, ES.XML_ENTITY_END
    				);
                    String[] currentLine = line.split(ES.XML_SEPARATOR);

                    eNames.add(String.valueOf(currentLine[0]));
                    eModels.add(String.valueOf(currentLine[1]));
                    eTextures.add(String.valueOf(currentLine[2]));
                    eTypesString.add(String.valueOf(currentLine[3]));
	        	}	        		
	        }	      	        
	        reader.close();	 
	        
        } catch (IOException e) {
        	System.err.println("Error reading the file");
        }     
        
        ObjectMapInterface map = new ObjectMap(loader);
	        
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
