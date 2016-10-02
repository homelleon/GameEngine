package scene;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
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
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class SceneRenderer {

	//TODO: Delete unnecessary parameters
	final static float SUN_MAX_HEIGHT = 4000; 
	final static float SUN_MIN_HEIGHT = -4000;
	final static float TIME_SPEED = 200;
	
	private boolean gamePaused = false;	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	
	private boolean isMidday = true;
	
	//TODO: Delete unnecessary objects
	private List<GuiTexture> guis;
	private GuiRenderer guiRenderer;
	private List<Terrain> terrains;
	private List<WaterTile> waters;
	WaterFrameBuffers fbos;
	private Player player;
	private Entity stall;
	private Entity cube;
	private List<Entity> grasses;
	private List<Light> lights;
	private Light sun;
	private WaterRenderer waterRenderer;
	private Camera camera;
	private GameTime time;
	private MousePicker picker;
	
	public SceneRenderer(){
		
		//***************PRE LOAD TOOLS*************//
		ObjectGenerator generator = new ObjectGenerator();
		this.loader = new Loader();
		this.renderer = new MasterRenderer(loader);
		
		
		//*******************AUDIO*************//
		
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
				
		
		//***************GUI***********//
		this.guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("GUI","helthBar"), new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);

		
		this.guiRenderer = new GuiRenderer(loader);
		
		//***************TERRAIN********************//

		this.terrains = new ArrayList<Terrain>();
		Terrain terrain = generator.createMultiTexTerrain("grass", "ground", "floweredGrass", "road", "blendMap", "heightMap");
		terrains.add(terrain);
		
		//**************WATER***********************//
		this.waters = new ArrayList<WaterTile>();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
		waters.add(new WaterTile(100, 120, -5));
		this.fbos = new WaterFrameBuffers();
		
		GuiTexture waterGui = new GuiTexture(fbos.getReflectionTexture(), new Vector2f(-0.5f,0.5f), new Vector2f(0.5f, 0.5f));
		guis.add(waterGui);
		
		
		//**************TEXTURED MODELS***************//
		//Stall//
	    TexturedModel stallModel = generator.loadStaticModel("stall", "stallTexture");
	    stallModel.getTexture().setShineDamper(5);
	    stallModel.getTexture().setReflectivity(1);
		//Cube//
		TexturedModel cubeModel = generator.loadStaticModel("cube", "cube1");		
		cubeModel.getTexture().setShineDamper(5);
		cubeModel.getTexture().setReflectivity(1);
		cubeModel.getTexture().setHasTransparency(true);
		cubeModel.getTexture().setUseFakeLighting(true);
		
		//************GRASS*********************//
		this.grasses = generator.createGrassField(50, 50, 200, 1, (float) 0.3);
        for(int i=0;i<grasses.size();i++){
        	spreadOnHeights(grasses.get(i));
        }
        
        //***********GAME OBJECTS****************//
					
		this.stall = new Entity(stallModel, new Vector3f(50,terrain.getHeightOfTerrain(50, 50),50),0,0,0,1);
		this.cube = new Entity(cubeModel, new Vector3f(100,terrain.getHeightOfTerrain(100, 10),10),0,0,0,1);
		
			
		this.lights = new ArrayList<Light>();
		this.sun = new Light(new Vector3f(100,-2000,2000),new Vector3f(1,1,1));
		lights.add(sun);
		lights.add(new Light(new Vector3f(100,2,100),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(1, 0.01f, 0.002f)));
		
	//***************PLAYER*************************//
		
		this.player = new Player(cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		
	//****************************************//
		this.camera = new Camera(player);	
		

		this.time = new GameTime(10);
		
	//**************IN GAME TOOLS**************************//	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		
	}
	
	private void spreadOnHeights(Entity entity){
		float x = entity.getPosition().x;
		float z = entity.getPosition().z; 
		entity.setPosition(new Vector3f(x, terrains.get(0).getHeightOfTerrain(x, z), z));
	}
		
	public void render(){
		if (Keyboard.isKeyDown(Keyboard.KEY_P)){
			gamePaused = !gamePaused;
		}
		if (!gamePaused){
		camera.move();	
		player.move(terrains.get(0));
		
		time.start();  
		
		sun.setPosition(new Vector3f(sun.getPosition().x,-2000 + time.getSunTime(),sun.getPosition().z));
		}else{
			picker.update();
			System.out.println(picker.getCurrentRay());
		}
		
		fbos.bindReflectionFrameBuffer();
		
		renderer.processEntity(player);
		renderer.processTerrain(terrains.get(0));
	    renderer.processEntity(stall);
	    renderer.processEntity(cube);
	    for(Integer i = 0; i < grasses.size(); i++){
	     renderer.processEntity(grasses.get(i));
	    } 
	    renderer.render(lights, camera);
	    
	    fbos.unbindCurrentFrameBuffer();
	    
	    renderer.processEntity(player);
		renderer.processTerrain(terrains.get(0));
	    renderer.processEntity(stall);
	    renderer.processEntity(cube);
	    for(Integer i = 0; i < grasses.size(); i++){
	     renderer.processEntity(grasses.get(i));
	    } 
	    renderer.render(lights, camera);
	    
	    waterRenderer.render(waters, camera);
	    guiRenderer.render(guis);
	}
	
	public void cleanUp(){
		ambientSource.delete();
		fbos.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		grasses.clear();
	}

}
