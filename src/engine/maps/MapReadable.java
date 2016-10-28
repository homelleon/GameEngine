package engine.maps;

import java.io.BufferedReader;

import engine.renderEngine.Loader;

public interface MapReadable {
	
	public GameMap readMap(String fileName, BufferedReader reader, Loader loader);

}
