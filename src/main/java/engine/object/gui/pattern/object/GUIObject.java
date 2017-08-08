package object.gui.pattern.object;

import object.Nameable;
import object.gui.Hideable;

public abstract class GUIObject implements Hideable, Nameable {
	
	protected boolean isShown = false;
	protected String name;
	
	public GUIObject(String name) {
		this.name = name;
	}
	
	@Override
	public void show() {
		this.isShown = true;
	}
	
	@Override
	public void hide() {
		this.isShown = false;
	}
	
	@Override
	public boolean getIsShown() {
		return this.isShown;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

}
