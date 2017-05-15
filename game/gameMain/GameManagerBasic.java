package gameMain;


import java.util.ArrayList;
import java.util.List;

import engineMain.EngineMain;
import scene.Scene;
import userInterfaces.UI;
import userInterfaces.UIGroup;
import userInterfaces.UIGroupSimple;
import userInterfaces.UIManager;
import userInterfaces.UIManagerBasic;

public class GameManagerBasic implements GameManager {
	
	Scene scene;
	
	public GameManagerBasic() {
		this.scene = EngineMain.getScene();
		List<UI> uis = new ArrayList<UI>();
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

}