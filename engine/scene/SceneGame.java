package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import cameras.CameraPlayer;
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
import guis.GuiTexture;
import maps.GameMap;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import models.TexturedModel;
import optimisations.MasterOptimisation;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticlesManager;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.MasterRenderer;
import toolbox.MousePicker;
import toolbox.ObjectUtils;
import voxels.Area;
import voxels.Chunk;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class SceneGame extends SceneManager implements Scene {
	
	public SceneGame() {
		cameraName = "Main";
		playerName = "Player1";
	}
	
	public void init() {
		super.init();
		/*-------------OPTIMIZATION-------------*/
	
		
		/*-------------TERRAIN------------------*/

        /*--------------GAME OBJECTS-------------*/
		
		List<Entity> normalMapEntities = EntitiesManager.createNormalMappedEntities(loader);

		map.setEntities(normalMapEntities);
		
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = ObjectUtils.loadStaticModel("cube", "cube1", loader);
		Player player1 = new PlayerTextured(playerName,cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		player1.getModel().getTexture().setReflectiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveIndex(1.33f);
		player1.getModel().getTexture().setShineDamper(5.0f);
		map.addPlayer(player1);
		map.addEntity(player1);
		
		/*------------------CHUNKS-------------------*/
		this.areas = new ArrayList<Area>();
		Area area = new Area(new Vector3f(50,0,50));
		areas.add(area);
		
		/*------------------CAMERA--------------------*/
		CameraPlayer camera = new CameraPlayer(player1, cameraName);
		map.addCamera(camera);
		

		
		/*------------------LIGHTS----------------*/
		Light sun = new Light("Sun", new Vector3f(-100000,150000,-100000), new Vector3f(1.3f,1.3f,1.3f));
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));
		map.addLight(sun);
		
		this.time = new GameTime(10);
		
		this.renderer = new MasterRenderer(loader, camera);
		this.optimisation = new MasterOptimisation(camera, renderer.getProjectionMatrix());
		
		/*----------------PARTICLES-----------------*/
		List<ParticleSystem> particleList = ParticlesManager.createParticleSystem(loader);
		map.setParticles(particleList);
		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());				
		/*------------------FBO-------------------*/
		
		this.multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
		this.outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		this.outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		PostProcessing.isBloomed = true;
		PostProcessing.isBlured = true;
		PostProcessing.init(loader);

		/*----------------FONTS-----------------*/
		TextMaster.init(loader);
		this.font = 
				new FontType(loader.loadTexture(ES.FONT_PATH, "candara"),
						new File(ES.FONT_PATH + "candara.fnt"));
		GUIText text = new GUIText("This is an Alfa-version of the game engine", 
				3, font, new Vector2f(0.25f, 0), 0.5f, true);
		
		text.setColour(1, 0, 0);
		
		/*--------------AUDIO----------------*/
		
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		Source ambientSource = new Source("birds", "forest.wav", 200);
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(10, 20, 10);			
		map.addAudio(ambientSource);
		
		/*--------------GUI-----------------*/
		List<GuiTexture> guiList = GuiManager.createGui(loader);
		map.setGuis(guiList);
		
		this.guiRenderer = new GuiRenderer(loader);		
		
		/*--------------WATER----------------*/
		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterFBOs);
		
		List<WaterTile> waterList = new ArrayList<WaterTile>();
		WaterTile water = new  WaterTile("Water", 0, 0, -4, 1000);
		waterList.add(water);
		waterList.stream().forEach((i) -> i.setTilingSize(0.05f));
		waterList.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waterList.stream().forEach((i) -> i.setWaveStrength(0.1f));
		map.setWaters(waterList);
		
		/*---------------IN GAME TOOLS--------------*/	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		/*---------------PREPARE-------------*/
		game.onStart();
		spreadEntitiesOnHeights(map.getEntities().values());		
	}
	
	/*Main render*/
			
	public void render() {
		super.render();
		
		map.getPlayers().get(playerName).move(map.getTerrains().values());
		
		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(map.getEntities().values());
			map.setTerrains(map.getTerrains().values());
			mapWriter.write(map);
			System.out.println("save");
		}
		
		renderParticles();				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderReflectionTexture();		
		renderRefractionTexture();
	    renderToScreen();
	}
	

}
