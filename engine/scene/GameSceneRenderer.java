package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import audio.Source;
import entities.EntitiesManager;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.PlayerCamera;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gameMain.Game;
import gameMain.MyGame;
import guis.GuiManager;
import guis.GuiRenderer;
import guis.GuiTexture;
import maps.GameMap;
import maps.MapFileLoader;
import maps.MapFileWriter;
import maps.MapLoadable;
import maps.MapWriteable;
import models.TexturedModel;
import optimisations.CutOptimisation;
import optimisations.Optimisation;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticlesManager;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class GameSceneRenderer implements WorldGethable {
	
	Game game = new MyGame();
	
	private Loader loader;
	private MasterRenderer renderer;
	private Source ambientSource;
	
	//TODO: Delete unnecessary objects
	private Map<String, PlayerCamera> cameras;
	private Map<String, Player> players;
	private GameMap map;
	private List<GuiTexture> guis;
	private GuiRenderer guiRenderer;
	private List<ParticleSystem> pSystem;
	private List<Terrain> terrains;
	private List<Entity> entities;
	private List<Entity> normalMapEntities;
	private List<WaterTile> waters;
	private List<Light> lights;
	FontType font;
	WaterFrameBuffers waterFBOs;
	private Fbo multisampleFbo;	
	private Fbo outputFbo;
	private Fbo outputFbo2;
	private Light sun;
	private WaterRenderer waterRenderer;	
	private GameTime time;
	private MousePicker picker;
	private Optimisation optimisation;
	
	private static boolean isPaused = false;
	private boolean mapIsLoaded = false;
	
	@Override
	public void setScenePaused(boolean value) {
		this.isPaused = value;
	}
	
	@Override
	public void loadMap(String name) {
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = new Loader();
		/*---------------MAP-----------------------*/
		MapLoadable mapLoader = new MapFileLoader();
		this.map = mapLoader.loadMap(name, loader);	
	}
	
	public void init() {
		if (!this.mapIsLoaded) {
			loadMap("map");
		}
		/*-------------OPTIMIZATION-------------*/
		this.optimisation = new CutOptimisation();	
		
		/*-------------TERRAIN------------------*/
		this.terrains = new ArrayList<Terrain>(); 
		terrains.addAll(map.getTerrains().values());

        /*--------------GAME OBJECTS-------------*/
		
		this.entities = new ArrayList<Entity>(); 
		entities = EntitiesManager.createEntities(loader);
		this.normalMapEntities = new ArrayList<Entity>();
		normalMapEntities = EntitiesManager.createNormalMappedEntities(loader);


		entities.addAll(map.getEntities().values());
		
		/*------------------LIGHTS----------------*/
		this.lights = new ArrayList<Light>();
		this.sun = new Light("Sun", new Vector3f(100000,1500000,-100000),new Vector3f(1.3f,1.3f,1.3f));
		lights.add(sun);
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));
		
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = SceneObjectTools.loadStaticModel("cube", "cube1", loader);
		this.players = new HashMap<String, Player>();
		Player player = new Player("Player1",cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		players.put(player.getName(), player);
		this.cameras = new HashMap<String, PlayerCamera>();
		PlayerCamera camera = new PlayerCamera(player, "Player1");
		cameras.put(camera.getName(), camera);
		this.time = new GameTime(10);
		
		this.renderer = new MasterRenderer(loader, camera);		
		
		/*----------------PARTICLES-----------------*/
		this.pSystem = ParticlesManager.createParticleSystem(loader);		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		/*------------------FBO-------------------*/
		
		this.multisampleFbo = new Fbo(Display.getWidth(),Display.getHeight());
		this.outputFbo = new Fbo(Display.getWidth(),Display.getHeight(), Fbo.DEPTH_TEXTURE);
		this.outputFbo2 = new Fbo(Display.getWidth(),Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init(loader);

		/*----------------FONTS-----------------*/
		TextMaster.init(loader);
		this.font = 
				new FontType(loader.loadTexture(Settings.FONT_PATH, "candara"),
						new File(Settings.FONT_PATH + "candara.fnt"));
		GUIText text = new GUIText("This is an Alfa-version of the game engine", 
				3, font, new Vector2f(0.25f, 0), 0.5f, true);
		
		text.setColour(1, 0, 0);
		
		/*--------------AUDIO----------------*/
		
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		this.ambientSource = new Source("birds", "forest.wav", 200);
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(10, 20, 10);			
		
		/*--------------GUI-----------------*/
		this.guis = GuiManager.createGui(loader);
		this.guiRenderer = new GuiRenderer(loader);		
		
		/*--------------WATER----------------*/
		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterFBOs);
		this.waters = new ArrayList<WaterTile>();
		WaterTile water = new  WaterTile("Water",0, 0, -4, 1000);
		waters.add(water);
		waters.stream().forEach((i) -> i.setTilingSize(0.05f));
		waters.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waters.stream().forEach((i) -> i.setWaveStrength(0.1f));		
		
		/*---------------IN GAME TOOLS--------------*/	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		/*---------------PREPARE-------------*/
		game.onStart();
	}
	
	/*Main render*/
			
	public void render() {
		game.onUpdate();
		if(!isPaused) {
			time.start();
			moves();	
		}
		
		if(isPaused) {
			picker.update();
			System.out.println(picker.getCurrentRay());
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapWriteable mapWriter = new MapFileWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(entities);
			map.setTerrains(terrains);
			mapWriter.write(map);
			System.out.println("save");
		}
		
		renderParticlesToScreen();
		renderer.renderShadowMap(entities, normalMapEntities, players.get("Player1"), sun, cameras.get("Player1"));				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderReflectionTexture();		
		renderRefractionTexture();
	    renderToScreen();
	}
    
	/*Render to FBO reflection texture*/
	
    private void renderReflectionTexture() {
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (cameras.get("Player1").getPosition().y - waters.get(0).getHeight());
		cameras.get("Player1").getPosition().y -= distance;
		cameras.get("Player1").invertPitch();
		renderer.processEntity(players.get("Player1"));
		renderer.renderScene(entities, normalMapEntities, terrains, lights, 
				cameras.get("Player1"), new Vector4f(0, 1, 0, -waters.get(0).getHeight()));
		cameras.get("Player1").getPosition().y += distance;
		cameras.get("Player1").invertPitch();
    }
    
    /*Render to FBO refraction texture*/
    
    private void renderRefractionTexture() {
    	waterFBOs.bindRefractionFrameBuffer();
		renderer.processEntity(players.get("Player1"));
		renderer.renderScene(entities, normalMapEntities, terrains, lights, 
				cameras.get("Player1"), new Vector4f(0, -1, 0, waters.get(0).getHeight()+1f));
    }
    
    /*Render to Screen*/
    
    private void renderToScreen() {
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		waterFBOs.unbindCurrentFrameBuffer();
	    
		multisampleFbo.bindFrameBuffer();
		optimisation.optimize(cameras.get("Player1"), entities, terrains);
	    renderer.processEntity(players.get("Player1"));
	    renderer.renderScene(entities, normalMapEntities, terrains,	lights, 
	    		cameras.get("Player1"), new Vector4f(0, -1, 0, 15));
	    waterRenderer.render(waters, cameras.get("Player1"), sun);
	    ParticleMaster.renderParticles(cameras.get("Player1"));
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(guis);
	    renderText();	    
    }
    
    public void renderParticlesToScreen() {
		pSystem.get(0).generateParticles(players.get("Player1").getPosition());
		pSystem.get(1).generateParticles(new Vector3f(50,terrains.get(0).getHeightOfTerrain(50, 50),50));
		ParticleMaster.update(cameras.get("Player1"));
    }
    	
	public void cleanUp() {
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampleFbo.cleanUp();
		TextMaster.cleanUp();
		ambientSource.delete();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
	}
	
	private void spreadOnHeights(List<Entity> entities) {
		if (!entities.isEmpty()) {
			for(Entity entity : entities){
				float terrainHeight = 0;
				
				for(Terrain terrain : terrains){
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}
	
    private void moves() {
    	cameras.get("Player1").move();
		players.get("Player1").move(terrains);
		AudioMaster.setListenerData(cameras.get("Player1").getPosition().x, cameras.get("Player1").getPosition().y, cameras.get("Player1").getPosition().z);
    }
	
	public GUIText createFPSText(float FPS) {
		return new GUIText("FPS: " + String.valueOf((int)FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}
	
	public GUIText createPickerCoordsText(MousePicker picker) {
		picker.update();
		String text = (String) String.valueOf(picker.getCurrentRay());
		return new GUIText(text, 1, font, new Vector2f(0.3f, 0.2f), 1f, true);
	}
	
	public void renderText() {
		GUIText fpsText = createFPSText(1 / DisplayManager.getFrameTimeSeconds());
	    fpsText.setColour(1, 0, 0);
	    GUIText coordsText = createPickerCoordsText(picker);
	    coordsText.setColour(1, 0, 0);
	    TextMaster.render();
	    fpsText.remove();
	    coordsText.remove();				
	}

	@Override
	public GameMap getMap() {
		return map;
	}

}
