package main;


import java.util.ArrayList;
import java.util.List;

import core.EngineMain;
import guis.gui.GUIInterface;
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
