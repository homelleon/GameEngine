package game;

import org.lwjgl.util.vector.Vector3f;

import engineTester.Engine;
import engineTester.MainEditorLoop;
import engineTester.MainGameLoop;

public class Main {
	


	public static void main(String[] args) {
		Engine testEngine = new MainEditorLoop();
		testEngine.LoadMap("map1");
		preInit(testEngine);
		testEngine.init();
		afterInit(testEngine);
		testEngine.run();
	}
	
	/*to change map before initialization
	 * note: use "engine.getMap()" to get methods of Map
	*/	
	private static void preInit(Engine engine) {
		engine.getMap().createEntity("Bo", "cube", "Cube1", new Vector3f(70, 50, 70), 0, 1, 0, 8);
	}
	
	/*to change map after initialization
	 * note: here you can avoid static entities
	 * from spreadOnTerrainHeight-function
	*/
	private static void afterInit(Engine engine) {
		engine.getMap().getEntities().get("Tree1").increasePosition(0, 50, 0);
		System.out.println(engine.getMap().getEntities().get("Bo").getName());
	}

}
