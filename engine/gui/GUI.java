package gui;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.GUIText;
import guiTextures.GUITexture;

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
	private List<GUITexture> guis;
	private List<GUIText> texts;
	
	public GUI(String name, List<GUITexture> guis, List<GUIText> texts) {
		this.name = name;
		this.guis = guis;
		this.texts = texts;
		this.isShown = false;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void show() {
		if(!guis.isEmpty()) {
			guis.forEach(gui -> gui.setIsShown(true));
		}
		
		if(!texts.isEmpty()) {
			texts.forEach(tui -> tui.setIsShown(true));
		}
		this.isShown = true;
	}

	@Override
	public void hide() {
		if(!guis.isEmpty()) {
			guis.forEach(gui -> gui.setIsShown(false));
		}
		if(!texts.isEmpty()) {
			texts.forEach(tui -> tui.setIsShown(false));
		}
		this.isShown = false;
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
		for(GUITexture gui : this.guis) {			
			Vector2f newPosition = Vector2f.add(gui.getPosition(), position, null);
			gui.setPosition(newPosition);
		}	
		
		for(GUIText text : this.texts) {
			Vector2f newPosition = Vector2f.add(text.getPosition(), position, null);
			text.setPosition(newPosition);
		}
	}

	@Override
	public void delete() {
		guis.clear();
		texts.clear();
	}


}
