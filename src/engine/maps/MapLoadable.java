package engine.maps;

import engine.renderEngine.Loader;

public interface MapLoadable {
	
	public GameMap loadMap(String fileName, Loader loader);

}
