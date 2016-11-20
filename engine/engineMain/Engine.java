package engineMain;

import maps.GameMap;

public interface Engine extends Runnable { 
	
	void loadMap(String name);
	void loadGameSettings();
	void init();
	void run();
	void exit();
	GameMap getMap();
}