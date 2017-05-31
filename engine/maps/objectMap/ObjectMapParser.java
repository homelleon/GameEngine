package maps.objectMap;

import java.io.BufferedReader;

import renderers.Loader;

public interface ObjectMapParser {
	
	public ObjectMapInterface readMap(String fileName, BufferedReader reader, Loader loader);

}
