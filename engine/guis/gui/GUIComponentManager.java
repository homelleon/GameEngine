package guis.gui;

import guis.guiTexts.GUITextManager;
import guis.guiTexts.GUITextManagerInterface;
import guis.guiTextures.GUITextureManager;
import guis.guiTextures.GUITextureManagerInterface;
import renderers.Loader;

public class GUIComponentManager implements GUIComponentManagerInterface {
	
	private GUITextureManagerInterface textureManager;
	private GUITextManagerInterface textManager;	
	
	public GUIComponentManager(GUITextureManagerInterface textureManager, GUITextManagerInterface textManager) {
		this.textManager = textManager;
		this.textureManager = textureManager;
	}
	
	public GUIComponentManager(String textureFileName, String textFileName, Loader loader) {
		this.textureManager = new GUITextureManager();
		this.textManager = new GUITextManager(loader);		
		textManager.readFile(textFileName);
	}
	
	@Override
	public GUITextureManagerInterface getTextures() {
		return textureManager;
	}
	
	@Override
	public GUITextManagerInterface getTexts() {
		return textManager;
	}
	
	
	@Override
	public void cleanAll() {
		textManager.clearAll();
		textureManager.clearAll();
	}

}
