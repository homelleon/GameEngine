package object.map.levelMap;

import java.io.BufferedReader;

import renderer.loader.Loader;

public interface LevelMapParserInterface {
	
	public LevelMapInterface readMap(String fileName, BufferedReader reader, Loader loader);

}
