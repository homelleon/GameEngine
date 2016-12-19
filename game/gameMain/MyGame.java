package gameMain;

import org.lwjgl.util.vector.Vector3f;

import engineMain.EngineMain;
import entities.Entity;
import particles.ParticleSystem;
import physicMain.PE10;
import scene.SceneO;

public class MyGame implements Game {
	

	private int time = 0;
	private ParticleSystem particles;
	private int world1;
	private SceneO scene;
	
		/* 
		 * use "Main.getMap()" to get methods of Map
		 * and all objects on Map  
		 * 
		 */	
		public void onStart() {
			PE10.initialize();
			scene = EngineMain.getScene();
			//scene.setTerrainWiredFrame(true);
			world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
			scene.getMap().createEntity("Bo", "cube", "Cube1", new Vector3f(70, 50, 70), 0, 1, 0, 8);
			scene.getMap().createParticles("Part", "cosmic", 4, true, 50, 25, 0.3f, 4, 1);
			Entity tree1 = scene.getMap().getEntities().get("tree");
			Entity tree2 = scene.getMap().getEntities().get("Tree2");
			Entity tree3 = scene.getMap().getEntities().get("Tree3");
	
			//PE10.peAttachBody(tree1, PE10.BODY_3D_SPHERE, world1);
			//PE10.peAttachBody(tree2, PE10.BODY_3D_SPHERE, world1);
			//PE10.peAttachBody(tree3, PE10.BODY_3D_SPHERE, world1);
			particles = scene.getMap().getParticles().get("Part");
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
			//PE10.peUpdateWorld(world1);
			//tree1.increasePosition(0, 0.1f, 0);
			//particles.setPosition(new Vector3f(20,50,20));
			//particles.generateParticles();			
			
		}
}
