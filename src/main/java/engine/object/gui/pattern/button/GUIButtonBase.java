package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import core.debug.EngineDebug;
import object.gui.group.IGUIGroup;
import object.gui.pattern.object.GUIObject;
import tool.math.Maths;

/**
 * 
 * @author homelleon
 * @see IGUIButton
 */
public abstract class GUIButtonBase extends GUIObject implements IGUIButton {

	protected IGUIGroup guiGroup;
	protected boolean isSelected = false;
	protected Vector2f point1;
	protected Vector2f point2;
	protected IAction event;

	protected GUIButtonBase(String name, IGUIGroup guiGroup, Vector2f point1, Vector2f point2) {
		super(name);
		this.guiGroup = guiGroup;
		this.point1 = point1;
		this.point2 = point2;
	}

	@Override
	public IGUIButton select() {
		this.isSelected = true;
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Button " + this.name + " is selected!");
		}
		return this;
	}

	@Override
	public void deselect() {
		if (this.isSelected) {
			this.isSelected = false;
		}
	}

	@Override
	public void use(IAction action) {
		action.start();
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Button " + this.name + " is used!");
		}
	}
	
	@Override
	public void use() {
		if(event != null) {this.event.start();}
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Button " + this.name + " is used!");
		}
	}
	
	@Override
	public void attachAction(IAction action) {
		this.event = action;
	}

	@Override
	public void show() {
		super.show();
		this.guiGroup.show();
	}

	@Override
	public void hide() {
		super.hide();
		this.guiGroup.hide();
	}
	
	@Override
	public void move(Vector2f position) {
		this.guiGroup.move(position);
	}

	/**
	 * Checks if mouse is pointing current button.
	 * 
	 * @return true if mouse is over the current button<br>
	 *         false if button is out of the current button
	 */
	public boolean getIsMouseOver(Vector2f cursorPosition) {
		return Maths.pointIsInQuad(cursorPosition, point1, point2);
	}

	/**
	 * Checks if the current button is selected or not.
	 * 
	 * @return true if the current button is selected<br>
	 *         false if the current button is not selected
	 */
	public boolean getIsSelected() {
		return this.isSelected;
	}
}
