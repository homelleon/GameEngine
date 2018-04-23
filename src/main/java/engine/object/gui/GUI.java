package object.gui;

import java.util.List;

import object.gui.element.GUIObject;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import tool.math.vector.Vector2f;

/**
 * Stores {@link GUITexture} and {@link GUIText} objects to control them
 * together.
 * 
 * @author homelleon
 * @see IGUI
 *
 */
public class GUI extends GUIObject {

	private List<GUITexture> guiTextures;
	private List<GUIText> guiTexts;

	public GUI(String name, List<GUITexture> guiTextureList, List<GUIText> guiTextList) {
		super(name);
		this.guiTextures = guiTextureList;
		this.guiTexts = guiTextList;
	}

	public void show() {
		super.show();
		doSwitch(true);
	}

	public void hide() {
		super.hide();
		doSwitch(false);
	}

	private void doSwitch(boolean value) {
		if (!guiTextures.isEmpty())
			guiTextures.forEach(gui -> gui.setIsVisible(value));
		if (!guiTexts.isEmpty())
			guiTexts.forEach(tui -> tui.setIsVisible(value));
	}

	public List<GUIText> getTexts() {
		return guiTexts;
	}

	public List<GUITexture> getTextures() {
		return guiTextures;
	}
	
	public void setTransparency(float value) {
		guiTextures.forEach(texture -> texture.setTransparency(value));
		
	}

	public void move(Vector2f position) {
		for (GUITexture texture : guiTextures) {
			Vector2f newPosition = Vector2f.add(texture.getPosition(), position);
			texture.setPosition(newPosition);
		}

		for (GUIText text : guiTexts) {
			Vector2f newPosition = Vector2f.add(text.getPosition(), position);
			text.setPosition(newPosition);
		}
	}

	public void clean() {
		guiTexts.forEach(GUIText::delete);
		guiTextures.clear();
		guiTexts.clear();
	}

}
