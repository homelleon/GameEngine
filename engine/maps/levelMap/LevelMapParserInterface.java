package maps.levelMap;

import java.io.BufferedReader;

import renderers.Loader;

public interface LevelMapParserInterface {
	
	public LevelMapInterface readMap(String fileName, BufferedReader reader, Loader loader);

}
