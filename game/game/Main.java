package game;

import engineTester.Engine;
import engineTester.MainGameLoop;

public class Main {

	public static void main(String[] args) {
		Engine eT = new MainGameLoop();
		eT.run();
	}

}
