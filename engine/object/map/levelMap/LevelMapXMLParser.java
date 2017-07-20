package object.map.levelMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.settings.EngineSettings;
import renderer.loader.Loader;
import tool.xml.XMLUtils;

public class LevelMapXMLParser implements LevelMapParserInterface {

	@Override
	public LevelMapInterface readMap(String fileName, BufferedReader reader) {
		String line;
		 /* entities */
		List<String> eNames = new ArrayList<String>();
        List<String> eModels = new ArrayList<String>();
        List<String> eTextures = new ArrayList<String>();
        List<String> eTypesString = new ArrayList<String>();
        List<Integer> eTypesInteger = new ArrayList<Integer>();

        //TODO: change parsering due to new code
        try {
	        while ((line = reader.readLine()) != null) {
				/*Read entities*/
				
				if (line.startsWith(XMLUtils.ENTITY) &&
	        			line.endsWith(XMLUtils.ENTITY)) {
	        		line = XMLUtils.pullLineFromWords(
        				line, XMLUtils.ENTITY, XMLUtils.ENTITY
    				);
                    String[] currentLine = line.split(XMLUtils.SEPARATOR);

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
        
        LevelMapInterface map = new LevelMap();
	        
        for(int i = 0; i < eNames.size(); i++) {
        	switch(eTypesString.get(i)) {
        	case "simple":
        		eTypesInteger.add(EngineSettings.ENTITY_TYPE_SIMPLE);
        		break;
        	case "normal":
        		eTypesInteger.add(EngineSettings.ENTITY_TYPE_NORMAL);
        		break;
        	case "detail":
        		eTypesInteger.add(EngineSettings.ENTITY_TYPE_DETAIL);
        		break;
        	default:
        		throw new IllegalArgumentException("Entity type of defalut object is unknown");
        	}
        	map.loadEntity(eNames.get(i), eModels.get(i), eTextures.get(i));
        }
    
		return map;
	}

}
