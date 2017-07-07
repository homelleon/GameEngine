package object.map.levelMap;

import java.io.BufferedReader;

import renderer.Loader;

public interface LevelMapParserInterface {
	
	public LevelMapInterface readMap(String fileName, BufferedReader reader, Loader loader);

}
