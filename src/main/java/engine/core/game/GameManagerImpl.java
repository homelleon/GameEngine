package core.game;

import core.EngineMain;
import manager.gui.GUITextManager;
import manager.gui.GUITextureManager;
import scene.Scene;

public class GameManagerImpl implements GameManager {

	protected Scene scene;
	protected GUITextManager textManager;
	protected GUITextureManager textureManager;	

	public GameManagerImpl() {
		this.scene = EngineMain.getScene();
		this.textManager = scene.getUserInterface().getComponent().getTexts();
		this.textureManager = scene.getUserInterface().getComponent().getTextures();
	}

	@Override
	public Scene getScene() {
		return scene;
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
