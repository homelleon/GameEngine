package scene;

import maps.GameMap;

public interface Scene {

	public void loadMap(String name);
	public GameMap getMap();
	public void render();
	public void init();
	public void cleanUp();
	public void setScenePaused(boolean isScenePaused);
	public void setTerrainWiredFrame(boolean value);	
	public void setEntityWiredFrame(boolean value);
	
}
