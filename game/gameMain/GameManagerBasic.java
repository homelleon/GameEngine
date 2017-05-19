package gameMain;


import java.util.ArrayList;
import java.util.List;

import engineMain.EngineMain;
import gui.GUIGroup;
import gui.GUIGroupInterface;
import gui.GUIInterface;
import gui.GUIManager;
import gui.GUIManagerInterface;
import scene.Scene;

public class GameManagerBasic implements GameManager {
	
	Scene scene;
	
	public GameManagerBasic() {
		this.scene = EngineMain.getScene();
		List<GUIInterface> uis = new ArrayList<GUIInterface>();
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

}
