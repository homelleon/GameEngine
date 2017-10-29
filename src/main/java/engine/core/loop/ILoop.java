package core.loop;

import object.scene.IScene;
import tool.dataEditor.menu.DataEditorFrame;

/**
 * Runnable looping interaface for main game initialization and external
 * control.
 * 
 * @author homelleon
 * 
 * @see Runnable
 * @see Loop
 *
 */
public interface ILoop extends Runnable {
	
	/**
	 * Sets edit mode.
	 * 
	 * @param value boolean to switch edit mode.
	 */
	public void setEditMode(boolean value);
	
	/**
	 * Gets if the edit mode is on.
	 * 
	 * @return true if edit mode is on<br>
	 * 		   false if edit mode is off
	 */
	public boolean getEditMode();
	
	/**
	 * Sets frame for  
	 * @param frame
	 */
	public void setDisplayFrame(DataEditorFrame frame);
	
	/**
	 * Method to start main looping process.
	 * <p>
	 * Firstly prepare all variables and start object updates on looping until
	 * escape button is pressed. After that it clean all objects variables.
	 */
	@Override
	void run();

	/**
	 * Returns scene for external scene objects control.
	 * 
	 * @return Scene value
	 */
	IScene getScene();

	/**
	 * Turns scene pause mode on or off
	 * 
	 * @param value
	 *            {@link Boolean} variable to change pause mode<br>
	 *            if true - sets pause mode on<br>
	 *            if false - sets pause mode off<br>
	 */
	void setScenePaused(boolean value);

	/**
	 * Returns boolean varialbe to check if scene object was paused.
	 * 
	 * @return true if scene is paused<br>
	 *         false if scene is not paused
	 */
	boolean getIsScenePaused();
	
	/**
	 * Initialize exiting the application. 
	 */
	void exit();
}
