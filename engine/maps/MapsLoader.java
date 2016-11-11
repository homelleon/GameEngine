package maps;

import renderEngine.Loader;

public interface MapsLoader {
	
	public GameMap loadMap(String fileName, Loader loader);

}
