package object.gui.control;

import org.lwjgl.util.vector.Vector2f;

import object.gui.gui.GUIInterface;
import object.gui.texture.GUITexture;

public class GUIButton implements GUIButtonInterface {
	
	private GUIInterface gui;	
	private boolean isSelected = false;
	
	public GUIButton(GUIInterface gui) {
		this.gui = gui;
	}

	@Override
	public void select() {
		this.isSelected = true;
	}

	@Override
	public void deselect() {
		this.isSelected = false;		
	}

	@Override
	public void use() {
		for(GUITexture texture : gui.getTextures()) {
			texture.setPosition(new Vector2f(0,0));
		}
	}

	@Override
	public void show() {
		this.gui.show();		
	}

	@Override
	public void hide() {
		this.gui.hide();
	}

	@Override
	public boolean getIsSelected() {
		return this.isSelected;
	}


}
