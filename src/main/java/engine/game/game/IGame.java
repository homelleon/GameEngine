package game.game;

/**
 * Main game interface to control events in engine system.
 * 
 * @author homelleon
 * @see Game
 * 
 */
public interface IGame {

	/**
	 * Realize game events on engine start.
	 */
	public void __onStart();

	/**
	 * Realize game events on engine update.
	 */
	public void __onUpdate();

	/**
	 * Relize game events on engine update event on pause.
	 */
	public void __onUpdateWithPause();

}
