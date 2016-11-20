package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.CameraFree;
import entities.EntitiesManager;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.PlayerTextured;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiManager;
import guis.GuiRenderer;
import maps.GameMap;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import models.TexturedModel;
import optimisations.CutOptimisation;
import particles.ParticleMaster;
import particles.ParticlesManager;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class SceneEditor extends SceneManager implements Scene {
	
	private boolean isPaused = false;

	@Override
	public void setScenePaused(boolean value) {
		this.isPaused = value;
	}
	
	@Override
	public void loadMap(String name) {
		super.loadMap(name);
	}
	
	@Override
	public GameMap getMap() {
		return super.getMap();
	}
	
	public void init() {	
		super.init();
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
		PlayerTextured player = new PlayerTextured(playerName, cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		players.put(player.getName(), player);
		this.cameras = new HashMap<String, Camera>();
		Camera camera = new CameraFree(cameraName, 20, 10, 20);
		cameras.put(camera.getName(), camera);
		this.time = new GameTime(10);
		
		this.renderer = new MasterRenderer(loader, camera);		
		
		/*----------------PARTICLES-----------------*/
		this.pSystem = ParticlesManager.createParticleSystem(loader);	

		
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
				new FontType(loader.loadTexture(ES.FONT_PATH, "candara"),
						new File(ES.FONT_PATH + "candara.fnt"));
		GUIText text = new GUIText("Edit mode", 
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
		
		spreadOnHeights(entities);
		spreadOnHeights(normalMapEntities);
		game.onStart();
		pSystem.addAll(map.getParticles().values());
		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
	}
	
	/*Main render*/
			
	public void render() {
		super.render();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(entities);
			map.setTerrains(terrains);
			mapWriter.write(map);
			System.out.println("save");
		}
		
		renderParticles();
		renderer.renderShadowMap(entities, normalMapEntities, players.get(playerName), sun, cameras.get(cameraName));				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderReflectionTexture();		
		renderRefractionTexture();
	    renderToScreen();
	}   
    	
	public void cleanUp() {
		super.cleanUp();
	}
		



}