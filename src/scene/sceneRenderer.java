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

public class sceneRenderer {

	
	final static float SUN_MAX_HEIGHT = 4000; 
	final static float SUN_MIN_HEIGHT = -4000;
	final static float TIME_SPEED = 200;
	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	private float time = 0;
	private boolean isMidday = true;
	
	private List<GuiTexture> guis;
	private GuiRenderer guiRenderer;
	private Terrain terrain;
	private Player player;
	private Entity entity;
	private Entity cubeEntity;
	private List<Entity> grassList;
	private List<Light> lights;
	private Light sun;
	private Camera camera; 
	
	public sceneRenderer(){
		

		//*******************AUDIO*************
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
		this.ambientSource = new Source();
		int buffer = AudioMaster.loadSound("audio/birds006.wav");
		Source ambientSource = new Source();
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.2f);
		//ambientSource.play(buffer);
		float xPos = 8;
		
		
		this.loader = new Loader();
		
		//***************GUI***********
		this.guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("sign"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		this.guiRenderer = new GuiRenderer(loader);
		
		//***************TERRAIN TEXTURE***********
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("ground"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("floweredGrass"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("road"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		//TerrainTexture blendMap1 = new TerrainTexture(loader.loadTexture("blendMap1"));
		Terrain terrain = new Terrain(0,0,loader,texturePack, blendMap, "heightMap");		
				
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
		ModelTexture grassTextureAtlas = new ModelTexture(loader.loadTexture("grassObjAtlas"));
		grassTextureAtlas.setNumberOfRows(2);
		TexturedModel staticGrass = new TexturedModel(grass, grassTextureAtlas);
		//ModelTexture grassTexture = staticGrass.getTexture();
		staticGrass.getTexture().setShineDamper(1);
		staticGrass.getTexture().setReflectivity(0);
		staticGrass.getTexture().setHasTransparency(true);
		staticGrass.getTexture().setUseFakeLighting(true);
		
	
	

		
		this.grassList = new ArrayList<Entity>(); 
					
		this.entity = new Entity(staticModel, new Vector3f(50,terrain.getHeightOfTerrain(50, 50),50),0,0,0,1);
		this.cubeEntity = new Entity(staticCube, new Vector3f(100,terrain.getHeightOfTerrain(100, 10),10),0,0,0,1);
		
		for(Integer j = 0; j < 10; j++){
		for(Integer i = 0; i < 10; i++){
			Entity grassEntity = new Entity(staticGrass, 4, new Vector3f(2*i,terrain.getHeightOfTerrain(2*i, 2*j),2*j),0,0,0,1);
			Entity grassEntity1 = new Entity(staticGrass, 1, new Vector3f((float) (2*i-0.5),0,(float) (2*j + 0.5)),0,100,0,1);
			grassList.add(grassEntity);
			grassList.add(grassEntity1);
		}
		}
			
		this.lights = new ArrayList<Light>();
		this.sun = new Light(new Vector3f(100,-100,2000),new Vector3f(1,1,1));
		lights.add(sun);
		lights.add(new Light(new Vector3f(100,2,100),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(1, 0.01f, 0.002f)));

		
		this.terrain = new Terrain(0,0,loader,texturePack, blendMap, "heightMap");

		
	//***************PLAYER*************************
		
		this.player = new Player(staticCube, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		
	//****************************************
		this.camera = new Camera(player);	
		
		this.renderer = new MasterRenderer(loader);
		
	}
		
	public void render(){
		//entity.increasePosition(0, 0, -0.1f);
		//entity.increaseRotation(0, 1, 0);
		camera.move();	
		player.move(terrain);
		if(!isMidday){
			time += DisplayManager.getFrameTimeSeconds() * TIME_SPEED;
		}else{
			time -= DisplayManager.getFrameTimeSeconds() * TIME_SPEED;
		}
		if(time >= SUN_MAX_HEIGHT){
			isMidday = true;
		}
        if(time <= SUN_MIN_HEIGHT){
        	isMidday = false;
        }
		sun.setPosition(new Vector3f(sun.getPosition().x,time,sun.getPosition().z));
		System.out.println(sun.getPosition().y);
		renderer.processEntity(player);
		renderer.processTerrain(terrain);
		//renderer.processTerrain(terrain2);
	    renderer.processEntity(entity);
	    renderer.processEntity(cubeEntity);
	    for(Integer i = 0; i < 200; i++){
	    renderer.processEntity(grassList.get(i));
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
		grassList.clear();
	}

}
