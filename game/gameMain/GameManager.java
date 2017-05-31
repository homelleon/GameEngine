package gameMain;


import java.util.ArrayList;
import java.util.List;

import engineMain.EngineMain;
import objects.gui.GUIInterface;
import scene.SceneInterface;

public class GameManager implements GameManagerInterface {
	
	SceneInterface scene;
	
	public GameManager() {
		this.scene = EngineMain.getScene();
		List<GUIInterface> uis = new ArrayList<GUIInterface>();
	}

	@Override
	public SceneInterface getScene() {
		return this.scene;
	}

}
