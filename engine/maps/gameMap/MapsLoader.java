package maps.gameMap;

import maps.objectMap.ObjectMapInterface;
import renderers.Loader;

public interface MapsLoader {
	
	public GameMap loadMap(String fileName, Loader loader);
	public ObjectMapInterface loadObjectMap(String fileName, Loader loader);

}
