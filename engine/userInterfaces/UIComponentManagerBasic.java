package userInterfaces;

import guis.GUIManager;
import guis.GUIManagerStructured;
import renderEngine.Loader;
import texts.GUITextManager;
import texts.GUITextManagerStructured;

public class UIComponentManagerBasic implements UIComponentManager {
	
	private GUIManager guiManager;
	private GUITextManager textManager;	
	
	public UIComponentManagerBasic(GUIManager guiManager, GUITextManager textManager) {
		this.textManager = textManager;
		this.guiManager = guiManager;
	}
	
	public UIComponentManagerBasic(String guiFileName, String textFileName, Loader loader) {
		this.guiManager = new GUIManagerStructured();
		this.textManager = new GUITextManagerStructured(loader);		
		textManager.readFile(textFileName);
	}
	
	@Override
	public GUIManager getTextures() {
		return guiManager;
	}
	
	@Override
	public GUITextManager getTexts() {
		return textManager;
	}
	
	
	@Override
	public void cleanAll() {
		textManager.clearAll();
		guiManager.clearAll();
	}

}
