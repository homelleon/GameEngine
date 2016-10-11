package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
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
	
	public static boolean gamePaused = false;	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	
	private boolean isMidday = true;
	
	//TODO: Delete unnecessary objects
	private List<GuiTexture> guis;
	private GuiRenderer guiRenderer;
	private ParticleSystem pSystem;
	private List<Terrain> terrains;
	private List<Entity> entities;
	private List<Entity> normalMapEntities;
	private List<WaterTile> waters;
	FontType font;
	WaterFrameBuffers fbos;
	private Player player;
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
		
		//***************TERRAIN********************//

		this.terrains = new ArrayList<Terrain>();
		Terrain terrain = generator.createMultiTexTerrain("grass", "ground", "floweredGrass", "road", "blendMap", 100f, 5, 0.5f);
		//Terrain terrain = generator.createMultiTexTerrain("grass", "ground", "floweredGrass", "road", "blendMap", "heightMap");
		terrains.add(terrain);
				
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
		
		this.normalMapEntities = new ArrayList<Entity>();
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("model","barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("normalMap", "barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
	

						
		
		//************GRASS*********************//
		List<Entity> grasses = generator.createGrassField(0, 0, 400, 1, 0.2f);
        for(int i=0;i<grasses.size();i++){
        	spreadOnHeights(grasses.get(i));
        }
        
        //***********GAME OBJECTS****************//
		this.entities = new ArrayList<Entity>();
		Entity stall = new Entity(stallModel, new Vector3f(50,terrain.getHeightOfTerrain(50, 50),50),0,0,0,1);
		Entity cube = new Entity(cubeModel, new Vector3f(100,terrain.getHeightOfTerrain(100, 10),10),0,0,0,1);
		Entity barrel = new Entity(barrelModel, new Vector3f(200, terrain.getHeightOfTerrain(200, 200), 200), 0,0,0,1);
		normalMapEntities.add(barrel);
		entities.add(cube);
		entities.add(stall);

		entities.addAll(grasses);
		this.lights = new ArrayList<Light>();
		this.sun = new Light(new Vector3f(0,1500000,-1000),new Vector3f(1.3f,1.3f,1.3f));
		lights.add(sun);
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));
		
		this.player = new Player(cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		this.camera = new Camera(player);	
		this.time = new GameTime(10);
		
		this.renderer = new MasterRenderer(loader, camera);		
		
		//**************PARTICLES***************//
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("particles", "particleAtlas"), 4, true);
		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		this.pSystem = new ParticleSystem(particleTexture, 50, 25, 0.3f, 4, 1);
		pSystem.randomizeRotation();
		pSystem.setDirection(new Vector3f(0, 1, 0), 0.1f);
		pSystem.setLifeError(0.1f);
		pSystem.setSpeedError(0.4f);
		pSystem.setScaleError(0.8f);

		//*******************FONTS*************//
		TextMaster.init(loader);
		this.font = new FontType(loader.loadTexture("font", "candara"), new File(Settings.FONT_PATH + "candara.fnt"));
		GUIText text = new GUIText("This is an Alfa-version of the game engine", 3, font, new Vector2f(0.25f, 0), 0.5f, true);
		text.setColour(1, 0, 0);
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
		
		GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), 
				new Vector2f(0.5f, 0.5f), new Vector2f(0.5f,0.5f));
		//guis.add(shadowMap);

		
		this.guiRenderer = new GuiRenderer(loader);
		
		
		//**************WATER***********************//
		this.fbos = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
		this.waters = new ArrayList<WaterTile>(); 
		waters.add(new WaterTile(0, 0, -4, 1000));
		waters.stream().forEach((i) -> i.setTilingSize(0.1f));
		waters.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waters.stream().forEach((i) -> i.setWaveStrength(0.1f));
		//waters.add(new WaterTile(400, 400, 0));
		//waters.get(0).setTilingSize(0.004f);
		
		
		
	//**************IN GAME TOOLS**************************//	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		
	}
	
	private void spreadOnHeights(Entity entity){
		float x = entity.getPosition().x;
		float z = entity.getPosition().z; 
		entity.setPosition(new Vector3f(x, terrains.get(0).getHeightOfTerrain(x, z), z));
	}
	
	
		
	public void render(){
		if (gamePaused == false){
		time.start();  
		camera.move();	
		player.move(terrains.get(0));
	
		sun.setPosition(new Vector3f(sun.getPosition().x,-2000 + time.getSunTime(),sun.getPosition().z));
		}else{
			picker.update();
			System.out.println(picker.getCurrentRay());
		}
		//pSystem.generateParticles(player.getPosition());
		//pSystem.generateParticles(new Vector3f(10,10,terrains.get(0).getHeightOfTerrain(10, 10)));
		ParticleMaster.update(camera);
		
		renderer.renderShadowMap(entities, player, sun);				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		
		//render reflection texture
		fbos.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.processEntity(player);
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -waters.get(0).getHeight()));
	    camera.getPosition().y += distance;
	    camera.invertPitch();
		
	    //render refraction texture
		fbos.bindRefractionFrameBuffer();
		renderer.processEntity(player);
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, waters.get(0).getHeight()));

	    //render to screen
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
	    fbos.unbindCurrentFrameBuffer();
	    renderer.processEntity(player);
	   
	    renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 15));
	    waterRenderer.render(waters, camera, sun);
	 
	    ParticleMaster.renderParticles(camera);
	    
	    guiRenderer.render(guis);
	    TextMaster.render();
	}
	
	public void cleanUp(){
		TextMaster.cleanUp();
		ambientSource.delete();
		fbos.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
	}

}
