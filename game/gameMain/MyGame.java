package gameMain;

import org.lwjgl.opengl.GL11;

import engineMain.EngineMain;
import entities.Entity;
import scene.Scene;

public class MyGame implements Game {
	

	private int world1;
	private Scene scene;
	Entity cube7;
	
		/* 
		 * use "Main.getMap()" to get methods of Map
		 * and all objects on Map  
		 * 
		 */	
		public void onStart() {
			//PE10.initialize();
			scene = EngineMain.getScene();
			System.out.println(GL11.glGetString(GL11.GL_VENDOR));
			System.out.println(GL11.glGetString(GL11.GL_RENDERER));
			System.out.println(GL11.glGetString(GL11.GL_VERSION));
			//scene.setTerrainWiredFrame(true);
			//world1 = PE10.peCreateWorld(new Vector3f(0,0,0), new Vector3f(0,0,0));
			cube7 = scene.getEntities().getByName("Cuby4");
			cube7.increasePosition(0, 10, 0);
			//PE10.peAttachBody(tree1, PE10.BODY_3D_SPHERE, world1);
			//PE10.peAttachBody(tree2, PE10.BODY_3D_SPHERE, world1);
			//PE10.peAttachBody(tree3, PE10.BODY_3D_SPHERE, world1);			
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
			//cube7.increasePosition(0, 0.1f, 0);
			//PE10.peUpdateWorld(world1);
			//tree1.increasePosition(0, 0.1f, 0);
			
			
		}
}
