package userInterfaces;

import guis.GUIManagerStructured;
import guis.GUIManager;
import texts.GUITextManager;
import texts.GUITextManagerStructured;

public class UIComponentManagerBasic implements UIComponentManager {
	
	private GUIManager guiManager;
	private GUITextManager textManager;	
	
	public UIComponentManagerBasic(GUIManager guiManager, GUITextManager textManager) {
		this.textManager = textManager;
		this.guiManager = guiManager;
	}
	
	public UIComponentManagerBasic(String guiFileName, String textFileName) {
		this.guiManager = new GUIManagerStructured();
		this.textManager = new GUITextManagerStructured();		
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
