package object.gui.control.button;

import org.lwjgl.util.vector.Vector2f;

import object.gui.gui.GUIInterface;
import tool.math.Maths;

public abstract class GUIButtonBase {
	
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
	
	/**
	 * Does selecetion action for current button.
	 */
	public void select() {
		if(!this.isSelected) {
			this.isSelected = true;
		}
	}

	/**
	 * Does desecelection action for current button.
	 */
	public void deselect() {
		if(this.isSelected) {
			this.isSelected = false;
		}
	}

	/**
	 * Uses button command that was pre-implemented.
	 */
	public abstract void use();

	/**
	 * Sets to show current button for rendering engine.
	 */
	public void show() {
		this.gui.show();		
	}

	/**
	 * Sets to hide current button for rendering engine.
	 */
	public void hide() {
		this.gui.hide();
	}

	/**
	 * Checks if mouse is pointing current button.
	 * 
	 * @return true if mouse is over the current button<br>
	 * 		   false if button is out of the current button
	 */
	public boolean getIsMouseOver(Vector2f cursorPosition) {		
		return Maths.pointIsInQuad(cursorPosition, point1, point2);
	}

	/**
	 * Checks if the current button is selected or not.
	 * 
	 * @return true if the current button is selected<br>
	 * 		   false if the current button is not selected 
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
