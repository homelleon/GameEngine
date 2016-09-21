package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
				
		DisplayManager.creatDisplay();
		//*******************AUDIO*************
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
		
		int buffer = AudioMaster.loadSound("audio/birds006.wav");
		Source ambientSource = new Source();
		ambientSource.setLooping(true);
		ambientSource.play(buffer);
		float xPos = 8;
		//***********************************
		Loader loader = new Loader();
		
		//***************TERRAIN TEXTURE***********
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("ground"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("floweredGrass"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("road"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		TerrainTexture blendMap1 = new TerrainTexture(loader.loadTexture("blendMap1"));
				
		
		//****************************************
				
	
		ModelData data = OBJFileLoader.loadOBJ("stall");
	
		
		RawModel stallModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());	
	    TexturedModel staticModel = new TexturedModel(stallModel,new ModelTexture(loader.loadTexture("stallTexture")));
		//ModelTexture texture = staticModel.getTexture();
		staticModel.getTexture().setShineDamper(5);
		staticModel.getTexture().setReflectivity(1);
		
		
		ModelData dataCube = OBJFileLoader.loadOBJ("cube");
		RawModel cube = loader.loadToVAO(dataCube.getVertices(), dataCube.getTextureCoords(), dataCube.getNormals(), dataCube.getIndices());
		TexturedModel staticCube = new TexturedModel(cube, new ModelTexture(loader.loadTexture("cube1")));
		staticCube.getTexture().setShineDamper(5);
		staticCube.getTexture().setReflectivity(1);
		staticCube.getTexture().setHasTransparency(true);
		staticCube.getTexture().setUseFakeLighting(true);
		
		
		ModelData dataGrass = OBJFileLoader.loadOBJ("grassObject");
		
		RawModel grass = loader.loadToVAO(dataGrass.getVertices(), dataGrass.getTextureCoords(), dataGrass.getNormals(), dataGrass.getIndices());
		TexturedModel staticGrass = new TexturedModel(grass, new ModelTexture(loader.loadTexture("grassObject")));
		//ModelTexture grassTexture = staticGrass.getTexture();
		staticGrass.getTexture().setShineDamper(1);
		staticGrass.getTexture().setReflectivity(0);
		staticGrass.getTexture().setHasTransparency(true);
		staticGrass.getTexture().setUseFakeLighting(true);
		
		List<Entity> grassList = new ArrayList<Entity>(); 
					
		Entity entity = new Entity(staticModel, new Vector3f(50,0,50),0,0,0,1);
		Entity cubeEntity = new Entity(staticCube, new Vector3f(100,1,10),0,0,0,1);
		
		for(Integer j = 0; j < 10; j++){
		for(Integer i = 0; i < 10; i++){
			Entity grassEntity = new Entity(staticGrass, new Vector3f(2*i,0,2*j),0,0,0,1);
			Entity grassEntity1 = new Entity(staticGrass, new Vector3f((float) (2*i-0.5),0,(float) (2*j + 0.5)),0,100,0,1);
			grassList.add(grassEntity);
			grassList.add(grassEntity1);
		}
		}
		Light light = new Light(new Vector3f(100,2000,100),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,0,loader,texturePack, blendMap);
		Terrain terrain2 = new Terrain(1,0,loader,texturePack, blendMap1);
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
		    renderer.processEntity(cubeEntity);
		    for(Integer i = 0; i < 200; i++){
		    renderer.processEntity(grassList.get(i));
		    }
		    
		    renderer.render(light, camera);
			DisplayManager.updateDisplay();
		    
			
		}
		
		ambientSource.delete();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		grassList.clear();
		DisplayManager.closeDisplay();

	}

}
