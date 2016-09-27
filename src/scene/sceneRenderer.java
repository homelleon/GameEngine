package scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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

public class SceneRenderer {

	//TODO: Delete unnecessary parameters
	final static float SUN_MAX_HEIGHT = 4000; 
	final static float SUN_MIN_HEIGHT = -4000;
	final static float TIME_SPEED = 200;
	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	
	private boolean isMidday = true;
	
	//TODO: Delete unnecessary objects
	private List<GuiTexture> guis;
	private GuiRenderer guiRenderer;
	private Terrain terrain;
	private Player player;
	private Entity entity;
	private Entity cubeEntity;
	private List<Entity> grasses;
	private List<Light> lights;
	private Light sun;
	private Camera camera;
	private GameTime time;
	
	public SceneRenderer(){
		

		//*******************AUDIO*************
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
		this.ambientSource = new Source();
		int buffer = AudioMaster.loadSound("forest.wav");
		Source ambientSource = new Source();
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.2f);
		ambientSource.play(buffer);
		float xPos = 8;
		
		
		this.loader = new Loader();
		
		//***************GUI***********
		this.guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("GUI","helthBar"), new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		this.guiRenderer = new GuiRenderer(loader);
		
		//***************TERRAIN TEXTURE***********
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("terrain","grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("terrain","ground"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("terrain","floweredGrass"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("terrain","road"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap","blendMap"));
		//TerrainTexture blendMap1 = new TerrainTexture(loader.loadTexture("blendMap1"));
		this.terrain = new Terrain(0,0,loader,texturePack, blendMap, "heightMap");		
				
		//****************************************
	    ModelData data = OBJFileLoader.loadOBJ("stall");
		
		RawModel stallModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());	
	    TexturedModel staticModel = new TexturedModel(stallModel,new ModelTexture(loader.loadTexture("model","stallTexture")));
		//ModelTexture texture = staticModel.getTexture();
		staticModel.getTexture().setShineDamper(5);
		staticModel.getTexture().setReflectivity(1);
		
		ModelData dataCube = OBJFileLoader.loadOBJ("cube");
		RawModel cube = loader.loadToVAO(dataCube.getVertices(), dataCube.getTextureCoords(), dataCube.getNormals(), dataCube.getIndices());
		TexturedModel staticCube = new TexturedModel(cube, new ModelTexture(loader.loadTexture("model","cube1")));
		staticCube.getTexture().setShineDamper(5);
		staticCube.getTexture().setReflectivity(1);
		staticCube.getTexture().setHasTransparency(true);
		staticCube.getTexture().setUseFakeLighting(true);
		SceneObjects object = new SceneObjects();
		this.grasses = object.CreateGrassField(50, 50, 20, 1);
        for(int i=0;i<grasses.size();i++){
        	spreadOnHeights(grasses.get(i));
        }
					
		this.entity = new Entity(staticModel, new Vector3f(50,terrain.getHeightOfTerrain(50, 50),50),0,0,0,1);
		this.cubeEntity = new Entity(staticCube, new Vector3f(100,terrain.getHeightOfTerrain(100, 10),10),0,0,0,1);
		
			
		this.lights = new ArrayList<Light>();
		this.sun = new Light(new Vector3f(100,-2000,2000),new Vector3f(1,1,1));
		lights.add(sun);
		lights.add(new Light(new Vector3f(100,2,100),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(1, 0.01f, 0.002f)));

		


		
	//***************PLAYER*************************
		
		this.player = new Player(staticCube, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		
	//****************************************
		this.camera = new Camera(player);	
		
		this.renderer = new MasterRenderer(loader);
		this.time = new GameTime(10);
		
		
	}
	
	private void spreadOnHeights(Entity entity){
		float x = entity.getPosition().x;
		float z = entity.getPosition().z; 
		entity.setPosition(new Vector3f(x, terrain.getHeightOfTerrain(x, z), z));
	}
		
	public void render(){
		//entity.increasePosition(0, 0, -0.1f);
		//entity.increaseRotation(0, 1, 0);
		camera.move();	
		player.move(terrain);
		time.start();  
		sun.setPosition(new Vector3f(sun.getPosition().x,-2000 + time.getSunTime(),sun.getPosition().z));
		System.out.println(sun.getPosition().y);
		renderer.processEntity(player);
		renderer.processTerrain(terrain);
		//renderer.processTerrain(terrain2);
	    renderer.processEntity(entity);
	    renderer.processEntity(cubeEntity);
	    for(Integer i = 0; i < grasses.size(); i++){
	     renderer.processEntity(grasses.get(i));
	    } 
	    renderer.render(lights, camera);
	    guiRenderer.render(guis);
	}
	
	public void cleanUp(){
		ambientSource.delete();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		grasses.clear();
	}

}
