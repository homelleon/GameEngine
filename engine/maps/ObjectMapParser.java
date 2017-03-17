package maps;

import java.io.BufferedReader;

import renderEngine.Loader;

public interface ObjectMapParser {
	
	public ObjectMap readMap(String fileName, BufferedReader reader, Loader loader);

}
