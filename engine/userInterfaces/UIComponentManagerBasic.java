package userInterfaces;

import guis.GUIManagerStructured;
import guis.GuiManager;
import texts.GUITextManager;
import texts.GUITextManagerStructured;

public class UIComponentManagerBasic implements UIComponentManager {
	
	private GuiManager guiManager;
	private GUITextManager textManager;	
	
	public UIComponentManagerBasic(GuiManager guiManager, GUITextManager textManager) {
		this.textManager = textManager;
		this.guiManager = guiManager;
	}
	
	public UIComponentManagerBasic(String guiFileName, String textFileName) {
		this.guiManager = new GUIManagerStructured();
		this.textManager = new GUITextManagerStructured();		
		textManager.readFile(textFileName);
	}
	
	@Override
	public GuiManager getTextures() {
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
