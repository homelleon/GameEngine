package object.gui.element;

import object.gui.Hideable;
import scene.Subject;
import tool.math.vector.Vector2f;

public abstract class GUIObject extends Subject<Vector2f> implements Hideable {
	
	protected boolean isVisible = false;
	
	public GUIObject(String name) {
		super(name);
		position = new Vector2f(0, 0);
		rotation = new Vector2f(0, 0);
	}
	
	@Override
	public void show() {
		isVisible = true;
	}
	
	@Override
	public void hide() {
		isVisible = false;
	}
	
	@Override
	public void switchVisibility() {
		if (isVisible) {
			hide();
		} else {
			show();
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
