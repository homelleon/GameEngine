package game;

import engine.Engine;
import engine.engineTester.MainGameLoop;

public class Main {

	public static void main(String[] args) {
		Engine eT = new MainGameLoop();
		eT.run();
	}

}
