package object.gui.component;

import object.gui.text.manager.GUITextManager;
import object.gui.text.manager.GUITextManagerInterface;
import object.gui.texture.GUITextureManager;
import object.gui.texture.GUITextureManagerInterface;
import renderer.loader.Loader;

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
	public void render() {
		this.textManager.render();
	}	
	
	@Override
	public void cleanAll() {
		textManager.clearAll();
		textureManager.clearAll();
	}

}
