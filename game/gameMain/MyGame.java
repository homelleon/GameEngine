package gameMain;

import org.lwjgl.util.vector.Vector3f;

import engineMain.Main;
import particles.ParticleSystem;

public class MyGame implements Game {
	

	private int time = 0;
	private ParticleSystem particles;
	
		/* 
		 * use "Main.getMap()" to get methods of Map
		 * and all objects on Map 
		 * 
		 */	
		public void onStart() {
			
			Main.getMap().createEntity("Bo", "cube", "Cube1", new Vector3f(70, 50, 70), 0, 1, 0, 8);
			Main.getMap().createParticles("Part", "cosmic", 4, true, 50, 25, 0.3f, 4, 1);
			particles = Main.getMap().getParticles().get("Part");
			particles.randomizeRotation();
			particles.setDirection(new Vector3f(0, 1, 0), 0.1f);
			particles.setLifeError(0.5f);
			particles.setSpeedError(0.4f);
			particles.setScaleError(0.8f);			
		}
		
		/* 
		 * on screen update - here you can
		 * change objects on map in dynamic 
		 * use "Main.getMap().getEntities.get("Tree1")"
		 * to manipulate Entity named "Tree1"
		 * 
		 * Don't use while loop and etc
		 */
		public void onUpdate() {
			Main.getMap().getEntities().get("tree").increasePosition(0, 0.1f, 0);
			particles.generateParticles(new Vector3f(20,50,20));			
			
		}
}
