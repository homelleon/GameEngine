package object.gui.gui;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

/**
 * Stores {@link GUITexture} and {@link GUIText} objects to control them
 * together.
 * 
 * @author homelleon
 * @see GUIInterface
 *
 */
public class GUI implements GUIInterface {
	
	private String name;
	private boolean isShown;
	private List<GUITexture> guiTextures;
	private List<GUIText> guiTexts;
	
	public GUI(String name, List<GUITexture> guiTextureList, List<GUIText> guiTextList) {
		this.name = name;
		this.guiTextures = guiTextureList;
		this.guiTexts = guiTextList;
		this.isShown = false;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void show() {
		this.doSwitch(true);
	}

	@Override
	public void hide() {
		this.doSwitch(false);
	}
	
	private void doSwitch(boolean value) {
		if(!guiTextures.isEmpty()) {
			guiTextures.forEach(gui -> gui.setIsShown(value));
		}
		if(!guiTexts.isEmpty()) {
			guiTexts.forEach(tui -> tui.setIsShown(value));
		}
		this.isShown = value;
	}
	

	@Override
	public boolean getIsShown() {
		return this.isShown;
	}	

	@Override
	public void setTransparency(float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Vector2f position) {
		for(GUITexture gui : this.guiTextures) {			
			Vector2f newPosition = Vector2f.add(gui.getPosition(), position, null);
			gui.setPosition(newPosition);
		}	
		
		for(GUIText text : this.guiTexts) {
			Vector2f newPosition = Vector2f.add(text.getPosition(), position, null);
			text.setPosition(newPosition);
		}
	}

	@Override
	public void delete() {
		guiTextures.clear();
		guiTexts.clear();
	}


}
