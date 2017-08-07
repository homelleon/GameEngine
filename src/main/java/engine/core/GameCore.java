package core;

import game.game.IGame;
import main.MyGame;

public class GameCore {

	/**
	 * Loads game logics.
	 * <p>
	 * Put here class name of your game in return.<br>
	 * Don't forget to add import for your class.
	 * 
	 * @return GameInterface game
	 */
	public static IGame loadGame() {
		return new MyGame();
	}

}
