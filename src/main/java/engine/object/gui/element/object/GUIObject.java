package object.gui.element.object;

import object.GameObject;
import object.gui.Hideable;

public abstract class GUIObject extends GameObject implements Hideable {
	
	protected boolean isVisible = false;
	
	public GUIObject(String name) {
		super(name);
	}
	
	@Override
	public void show() {
		this.isVisible = true;
	}
	
	@Override
	public void hide() {
		this.isVisible = false;
	}
	
	@Override
	public void switchVisibility() {
		if(this.isVisible) {
			this.hide();
		} else {
			this.show();
		}
	}
	
	@Override
	public boolean getIsVisible() {
		return this.isVisible;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

}
