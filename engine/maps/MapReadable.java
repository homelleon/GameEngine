package maps;

import java.io.BufferedReader;

import renderEngine.Loader;

public interface MapReadable {
	
	public GameMap readMap(String fileName, BufferedReader reader, Loader loader);

}
