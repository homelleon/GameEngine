package core.game;

import java.util.ArrayList;
import java.util.List;

import core.EngineMain;
import manager.gui.GUITextManager;
import manager.gui.GUITextureManager;
import object.gui.gui.GUI;
import scene.Scene;

public class GameManager implements IGameManager {

	Scene scene;
	protected GUITextManager textManager;
	protected GUITextureManager textureManager;	

	public GameManager() {
		this.scene = EngineMain.getScene();
		List<GUI> uis = new ArrayList<GUI>();
		this.textManager = scene.getUserInterface().getComponent().getTexts();
		this.textureManager = scene.getUserInterface().getComponent().getTextures();
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

	@Override
	public GUITextureManager getTextures() {
		return textureManager;
	}

	@Override
	public GUITextManager getTexts() {
		return textManager;
	}

}
