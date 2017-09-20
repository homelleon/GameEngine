package object.gui.gui;

import java.util.List;

import object.gui.pattern.object.GUIObject;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import tool.math.vector.Vec2f;

/**
 * Stores {@link GUITexture} and {@link GUIText} objects to control them
 * together.
 * 
 * @author homelleon
 * @see IGUI
 *
 */
public class GUI extends GUIObject implements IGUI {

	private List<GUITexture> guiTextures;
	private List<GUIText> guiTexts;

	public GUI(String name, List<GUITexture> guiTextureList, List<GUIText> guiTextList) {
		super(name);
		this.guiTextures = guiTextureList;
		this.guiTexts = guiTextList;
	}

	@Override
	public void show() {
		super.show();
		this.doSwitch(true);
	}

	@Override
	public void hide() {
		super.hide();
		this.doSwitch(false);
	}

	private void doSwitch(boolean value) {
		if (!guiTextures.isEmpty()) {
			guiTextures.forEach(gui -> gui.setIsVisible(value));
		}
		if (!guiTexts.isEmpty()) {
			guiTexts.forEach(tui -> tui.setIsVisible(value));
		}
	}

	@Override
	public List<GUIText> getTexts() {
		return this.guiTexts;
	}

	@Override
	public List<GUITexture> getTextures() {
		return this.guiTextures;
	}

	@Override
	public void setTransparency(float value) {
		this.guiTextures.forEach(texture -> texture.setTransparency(value));
		
	}

	@Override
	public void move(Vec2f position) {
		for (GUITexture texture : this.guiTextures) {
			Vec2f newPosition = Vec2f.add(texture.getPosition(), position, null);
			texture.setPosition(newPosition);
		}

		for (GUIText text : this.guiTexts) {
			Vec2f newPosition = Vec2f.add(text.getPosition(), position, null);
			text.setPosition(newPosition);
		}
	}

	@Override
	public void clean() {
		guiTextures.clear();
		guiTexts.clear();
	}

}
