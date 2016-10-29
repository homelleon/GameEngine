package maps;

import renderEngine.Loader;

public interface MapLoadable {
	
	public GameMap loadMap(String fileName, Loader loader);

}
