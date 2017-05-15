package userInterfaces;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.GUIText;
import guis.GuiTexture;

/**
 * Stores {@link GuiTexture} and {@link GUIText} objects to control them
 * together.
 * 
 * @author homelleon
 * @see UI
 *
 */
public class UISimple implements UI {
	
	private String name;
	private List<GuiTexture> guis;
	private List<GUIText> texts;
	
	public UISimple(String name, List<GuiTexture> guis, List<GUIText> texts) {
		this.name = name;
		this.guis = guis;
		this.texts = texts;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void show() {
		guis.forEach(gui -> gui.setIsShown(true));
		texts.forEach(tui -> tui.setIsShown(true));
	}

	@Override
	public void hide() {
		guis.forEach(gui -> gui.setIsShown(false));
		texts.forEach(tui -> tui.setIsShown(false));
	}

	@Override
	public void setTransparency(float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Vector2f position) {
		for(GuiTexture gui : this.guis) {			
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
