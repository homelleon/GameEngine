package gameMain;

/**
 * Main game interface to control events in engine system.
 * 
 * @author homelleon
 * @see MyGame
 * 
 */
public interface Game {
	
	/**
	 * Realize game events on engine start.
	 */
	public void onStart();
	
	/**
	 * Realize game events on engine update.
	 */
	public void onUpdate();

}
