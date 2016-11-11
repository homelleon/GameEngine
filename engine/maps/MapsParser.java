package maps;

import java.io.BufferedReader;

import renderEngine.Loader;

public interface MapsParser {
	
	public GameMap readMap(String fileName, BufferedReader reader, Loader loader);

}
