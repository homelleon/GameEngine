package engineTester;

import maps.GameMap;
import triggers.Trigger;

public interface Engine extends Runnable {
	
	void LoadMap();
	void init();
	void run();
	void exit();
	GameMap getMap();
}
