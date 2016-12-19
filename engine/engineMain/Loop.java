package engineMain;

import scene.SceneO;

public interface Loop extends Runnable { 
	
	void loadMap(String name);
	void loadGameSettings();
	void init();
	void run();
	void exit();
	SceneO getScene();
}
