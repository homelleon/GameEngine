package gameMain;

/**
 * Main game interface to control events in engine system.
 * 
 * @author homelleon
 * @see MyGame
 * 
 */
public interface GameInterface {
	
	/**
	 * Realize game events on engine start.
	 */
	public void onStart();
	
	/**
	 * Realize game events on engine update.
	 */
	public void onUpdate();

}
