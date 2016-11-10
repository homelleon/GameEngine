package game;

import org.lwjgl.util.vector.Vector3f;

import engineTester.Main;
import entities.Entity;

public class Game1 implements Game {
	

	private int time = 0;
	
		/* use "Main.getMap()" to get methods of Map
		 * and all objects on Map */	
		public void onStart() {
			
			Main.getMap().createEntity("Bo", "cube", "Cube1", new Vector3f(70, 50, 70), 0, 1, 0, 8);
		}
		
		/* on screen update - here you can
		 * change objects on map in dynamic 
		 * use "Main.getMap().getEntities.get("Tree1")"
		 * to manipulate Entity named "Tree1"
		 * Don't use while loop and etc*/
		public void onUpdate() {
			time += 1;
			System.out.println(Main.getMap().getEntities().get("Bo").getName());
			Entity tree = Main.getMap().getEntities().get("Tree1");
				if ((time % 10 == 0) && (tree.getPosition().y <=50)) {
					tree.increasePosition(0, 1, 0);				
			}
		}
}
