package maps;

import java.io.BufferedReader;

import renderEngine.Loader;

public interface ObjectMapParser {
	
	public ObjectMapInterface readMap(String fileName, BufferedReader reader, Loader loader);

}
