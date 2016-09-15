package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.creatDisplay();
		Loader loader = new Loader();
				
		RawModel model = OBJLoader.loadObjModel("stall", loader);
     
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("stallTexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(5);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(50,0,50),0,0,0,1);
		Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
		Camera camera = new Camera(100,4,100);
		camera.setYaw(180);
		
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()){
			//game logic
			//entity.increasePosition(0, 0, -0.1f);
			//entity.increaseRotation(0, 1, 0);
			camera.move();	
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
		    renderer.processEntity(entity);
		    
		    renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
