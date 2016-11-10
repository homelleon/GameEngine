package engineTester;

import maps.GameMap;

public class Main {
		
	private static Engine engine;

	public static void main(String[] args) {
		engine = new MainEditorLoop();
		engine.loadMap("map1");
		engine.init();
		engine.run();
	}
	
	
	public static GameMap getMap() {
		return engine.getMap();
	}

}
