package engineMain;

import scene.Scene;

public interface Engine extends Runnable { 
	
	void loadMap(String name);
	void loadGameSettings();
	void init();
	void run();
	void exit();
	Scene getScene();
}
