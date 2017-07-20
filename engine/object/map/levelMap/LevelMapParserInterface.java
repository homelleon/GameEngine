package object.map.levelMap;

import java.io.BufferedReader;

public interface LevelMapParserInterface {
	
	public LevelMapInterface readMap(String fileName, BufferedReader reader);

}
