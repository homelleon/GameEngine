package scene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

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
import cameras.Camera;
import cameras.CameraFree;
import entities.EntitiesManager;
import entities.Entity;
import entities.Light;
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
import optimisations.CutOptimisation;
import particles.ParticleMaster;
import particles.ParticleSystem;
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


	public void init() {	
		super.init();
		/*-------------OPTIMIZATION-------------*/
		this.optimisation = new CutOptimisation();	
		
		/*-------------TERRAIN------------------*/

        /*--------------GAME OBJECTS-------------*/
		
		List<Entity> entities = new ArrayList<Entity>(); 
		entities = EntitiesManager.createEntities(loader);
		
		List<Entity> normalMapEntities = EntitiesManager.createNormalMappedEntities(loader);
		
		map.setEntities(entities);
		map.setEntities(normalMapEntities);
		
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = SceneObjectTools.loadStaticModel("cube", "cube1", loader);
		PlayerTextured player1 = new PlayerTextured(playerName, cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		map.addPlayer(player1);
		
		/*------------------CAMERA----------------*/
		Camera camera = new CameraFree(cameraName, 20, 10, 20);
		map.addCamera(camera);

		this.time = new GameTime(10);
		
		this.renderer = new MasterRenderer(loader, camera);		
		
		/*------------------LIGHTS----------------*/
		Light sun = new Light("Sun", new Vector3f(100000,1500000,-100000),new Vector3f(1.3f,1.3f,1.3f));
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));
		map.addLight(sun);
		
		/*----------------PARTICLES-----------------*/
		List<ParticleSystem> particleList = ParticlesManager.createParticleSystem(loader);
		map.setParticles(particleList);

		
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
		Source ambientSource = new Source("birds", "forest.wav", 200);
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f); 
		ambientSource.play();
		ambientSource.setPosition(10, 20, 10);
		map.addAudio(ambientSource);
		
		/*--------------GUI-----------------*/
		List<GuiTexture> guis = GuiManager.createGui(loader);
		map.setGuis(guis);
		
		this.guiRenderer = new GuiRenderer(loader);		
		
		/*--------------WATER----------------*/
		this.waterFBOs = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterFBOs);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new  WaterTile("Water",0, 0, -4, 1000);
		waters.add(water);
		waters.stream().forEach((i) -> i.setTilingSize(0.05f));
		waters.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waters.stream().forEach((i) -> i.setWaveStrength(0.1f));
		
		map.setWaters(waters);
		
		/*---------------IN GAME TOOLS--------------*/	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		/*---------------PREPARE-------------*/
		
		spreadEntitiesOnHeights(map.getEntities().values());
		game.onStart();
		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
	}
	
	/*Main render*/
			
	public void render() {
		super.render();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
			MapsWriter mapWriter = new MapsTXTWriter();
			GameMap map = new GameMap("newMap", loader);
			map.setEntities(map.getEntities().values());
			map.setTerrains(map.getTerrains().values());
			mapWriter.write(map);
			System.out.println("save");
		}
		
		renderParticles();
		renderer.renderShadowMap(map.getEntities().values(), map.getLights().get("Sun"), map.getCameras().get(cameraName));				
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		renderReflectionTexture();		
		renderRefractionTexture();
	    renderToScreen();
	}
 
	
}
