package object.gui;

/**
 * Interface for switching object in two states: show and hide.
 * 
 * @author homelleon
 *
 */
public interface Hideable {

	/**
	 * Sets object to be show for renderer;
	 */
	void show();

	/**
	 * Sets object to be hidden for renderer;
	 */
	void hide();

}
