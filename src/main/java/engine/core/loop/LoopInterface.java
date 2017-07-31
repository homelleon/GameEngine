package core.loop;

import object.scene.scene.SceneInterface;

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
public interface LoopInterface extends Runnable {

	/**
	 * Method to start main looping process.
	 * <p>
	 * Firstly prepare all variables and start object updates on looping until
	 * escape button is pressed. After that it clean all objects variables.
	 */
	@Override
	void run(); // запуск

	/**
	 * Returns scene for external scene objects control.
	 * 
	 * @return Scene value
	 */
	SceneInterface getScene(); // вернуть игровую сцену

	/**
	 * Switch visualization of rendering terrain between normal and wired frame
	 * mode.
	 * 
	 * @param value
	 *            {@link Boolean} variable to change visualization mode<br>
	 *            if true - sets wrired frame mode<br>
	 *            if flase - sets normal mode
	 * 
	 * @see #setEntityWiredFrame(boolean)
	 */
	void setTerrainWiredFrame(boolean value); // показать сетку ландшафта

	/**
	 * Switch visualization of rendering entities between normal and wired frame
	 * mode.
	 * 
	 * @param value
	 *            {@link Boolean} variable to change visualization mode<br>
	 *            if true - sets wrired frame mode<br>
	 *            if flase - sets normal mode
	 * 
	 * @see #setTerrainWiredFrame(boolean)
	 */
	void setEntityWiredFrame(boolean value); // показать сетку объектов

	/**
	 * Turns scene pause mode on or off
	 * 
	 * @param value
	 *            {@link Boolean} variable to change pause mode<br>
	 *            if true - sets pause mode on<br>
	 *            if false - sets pause mode off<br>
	 */
	void setScenePaused(boolean value); // установка паузы для сцены

	/**
	 * Returns boolean varialbe to check if scene object was paused.
	 * 
	 * @return true if scene is paused<br>
	 *         false if scene is not paused
	 */
	boolean getIsScenePaused();
}
