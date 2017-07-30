package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import core.debug.EngineDebug;
import object.gui.gui.GUIInterface;
import object.gui.pattern.object.GUIObject;
import tool.math.Maths;

/**
 * 
 * @author homelleon
 * @see GUIButtonInterface
 */
public abstract class GUIButtonBase extends GUIObject implements GUIButtonInterface {

	protected String name;
	protected GUIInterface gui;
	protected boolean isSelected = false;
	protected Vector2f point1;
	protected Vector2f point2;

	protected GUIButtonBase(String name, GUIInterface gui, Vector2f point1, Vector2f point2) {
		this.name = name;
		this.gui = gui;
		this.point1 = point1;
		this.point2 = point2;
	}

	@Override
	public void select() {
		if (!this.isSelected) {
			this.isSelected = true;
			if(EngineDebug.hasDebugPermission()) {
				System.out.println("Button " + this.name + " is selected!");
			}
		}
	}

	@Override
	public void deselect() {
		if (this.isSelected) {
			this.isSelected = false;
		}
	}

	@Override
	public abstract void use();

	@Override
	public void show() {
		super.show();
		this.gui.show();
	}

	@Override
	public void hide() {
		super.hide();
		this.gui.hide();
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

	/**
	 * Gets graphic user interface name.
	 * 
	 * @return {@link String} value of gui button name
	 */
	public String getName() {
		return this.name;
	}

}
