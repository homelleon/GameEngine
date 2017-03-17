package maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import renderEngine.Loader;
import scene.ES;

public class MapsTXTLoader implements MapsLoader {
	
	
	public GameMap loadMap(String fileName, Loader loader) {
		FileReader isr = null;
        File mapFile = new File(ES.MAP_PATH + fileName + ".txt");
	        
        try {
            isr = new FileReader(mapFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res; don't use any extention");
        }
        BufferedReader reader = new BufferedReader(isr);
        MapsParser mapParser = new MapsTXTParser();
        
		return  mapParser.readMap(fileName, reader, loader);
	}
	
	public ObjectMap loadObjectMap(String fileName, Loader loader) {
		return null;
		
	}

}
