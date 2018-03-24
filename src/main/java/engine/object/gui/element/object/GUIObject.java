package object.gui.element.object;

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
		this.isVisible = true;
	}
	
	@Override
	public void hide() {
		this.isVisible = false;
	}
	
	@Override
	public void switchVisibility() {
		if (this.isVisible) {
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
