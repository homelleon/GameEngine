package object.gui.pattern.object;

import object.Nameable;
import object.gui.Hideable;

public abstract class GUIObject implements Hideable, Nameable {
	
	protected boolean isVisible = false;
	protected String name;
	
	public GUIObject(String name) {
		this.name = name;
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
