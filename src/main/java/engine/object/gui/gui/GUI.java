package object.gui.gui;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import object.gui.pattern.object.GUIObject;
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
public class GUI extends GUIObject implements GUIInterface {

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
			guiTextures.forEach(gui -> gui.setIsShown(value));
		}
		if (!guiTexts.isEmpty()) {
			guiTexts.forEach(tui -> tui.setIsShown(value));
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
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Vector2f position) {
		for (GUITexture gui : this.guiTextures) {
			Vector2f newPosition = Vector2f.add(gui.getPosition(), position, null);
			gui.setPosition(newPosition);
		}

		for (GUIText text : this.guiTexts) {
			Vector2f newPosition = Vector2f.add(text.getPosition(), position, null);
			text.setPosition(newPosition);
		}
	}

	@Override
	public void clean() {
		guiTextures.clear();
		guiTexts.clear();
	}

}
