package engineMain;

import maps.GameMap;
import triggers.Trigger;

public interface Engine extends Runnable {
	
	void loadMap(String name);
	void init();
	void run();
	void exit();
	GameMap getMap();
}
