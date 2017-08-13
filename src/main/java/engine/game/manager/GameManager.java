package game.manager;

import java.util.ArrayList;
import java.util.List;

import core.EngineMain;
import object.gui.gui.IGUI;
import object.scene.IScene;

public class GameManager implements IGameManager {

	IScene scene;

	public GameManager() {
		this.scene = EngineMain.getScene();
		List<IGUI> uis = new ArrayList<IGUI>();
	}

	@Override
	public IScene getScene() {
		return this.scene;
	}

}
