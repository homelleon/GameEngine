package core.game;

import java.util.ArrayList;
import java.util.List;

import core.EngineMain;
import manager.gui.text.IGUITextManager;
import manager.gui.texture.IGUITextureManager;
import object.gui.gui.IGUI;
import scene.Scene;

public class GameManager implements IGameManager {

	Scene scene;
	protected IGUITextManager textManager;
	protected IGUITextureManager textureManager;	

	public GameManager() {
		this.scene = EngineMain.getScene();
		List<IGUI> uis = new ArrayList<IGUI>();
		this.textManager = scene.getUserInterface().getComponent().getTexts();
		this.textureManager = scene.getUserInterface().getComponent().getTextures();
	}

	@Override
	public Scene getScene() {
		return this.scene;
	}

	@Override
	public IGUITextureManager getTextures() {
		return textureManager;
	}

	@Override
	public IGUITextManager getTexts() {
		return textManager;
	}

}
