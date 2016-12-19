package engineMain;

import scene.Scene;

public interface Loop extends Runnable { 
	
	void run();
	Scene getScene();
	void setTerrainWiredFrame(boolean value);
	void setEntityWiredFrame(boolean value);
	void setScenePaused(boolean value);
}
