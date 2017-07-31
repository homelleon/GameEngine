package game.manager;

import java.util.ArrayList;
import java.util.List;

import core.EngineMain;
import object.gui.gui.GUIInterface;
import object.scene.scene.SceneInterface;

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
