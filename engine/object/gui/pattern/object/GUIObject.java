package object.gui.pattern.object;

import object.gui.Hideable;

public abstract class GUIObject implements Hideable {
	
	protected boolean isShown = false;
	protected String name;
	
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
	
	public String getName() {
		return this.name;
	}
	

}
