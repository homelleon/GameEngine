package core;

import game.game.IGame;
import main.MyGame;

public class GameCore {

	/**
	 * Loads game logics.
	 * <p>
	 * Put class name of your game as return object.<br>
	 * Don't forget to add import for your class.
	 * 
	 * @return GameInterface game
	 */
	public static IGame loadGame() {
		return new MyGame();
	}

}
