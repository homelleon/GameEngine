package engineMain;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import audio.AudioSource;
import cameras.CameraPlayer;
import entities.EntitiesManager;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.PlayerTextured;
import environmentMap.EnvironmentMapRenderer;
import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import gameMain.Game;
import gameMain.MyGame;
import guis.GuiManager;
import guis.GuiRenderer;
import guis.GuiTexture;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsTXTLoader;
import maps.MapsTXTWriter;
import maps.MapsWriter;
import models.TexturedModel;
import optimisations.MasterOptimisation;
import optimisations.Optimisation;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticlesManager;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import scene.ES;
import scene.GameTime;
import scene.Scene;
import scene.SceneGame;
import scene.SceneO;
import terrains.Terrain;
import textures.Texture;
import toolbox.MousePicker;
import toolbox.ObjectUtils;
import voxels.VoxelGrid;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class GameLoop implements Loop {
	
	private static final String SETTINGS_NAME = "settings";
	
	protected Game game = new MyGame();
	
	private boolean isScenePaused = false; 
	
	protected Loader loader;
    protected MasterRenderer renderer;
    protected EnvironmentMapRenderer enviroRenderer;
    protected AudioSource ambientSource;
	protected Texture environmentMap;
	
    private String cameraName;
    private String playerName;
	
    private Scene scene;
    protected GameMap map;
    protected GuiRenderer guiRenderer;
    protected FontType font;
    protected WaterFrameBuffers waterFBOs;
    protected Fbo multisampleFbo;	
    protected Fbo outputFbo;
    protected Fbo outputFbo2;
    protected WaterRenderer waterRenderer;	
    protected GameTime time;
    protected MousePicker picker;
    protected Optimisation optimisation;
    
    protected boolean mapIsLoaded = false;
    protected boolean isPaused = false;
	
	public GameLoop() {
		DisplayManager.creatDisplay();
	}
	
	@Override
	public void loadMap(String name) {
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = Loader.getInstance();
		/*---------------MAP-----------------------*/
		MapsLoader mapLoader = new MapsTXTLoader();
		this.map = mapLoader.loadMap(name, loader);	
		this.mapIsLoaded = true;		
	}
	
	@Override
	public void init() {
		if (!this.mapIsLoaded) {
			loadMap("map");
		}
		
		this.scene = new SceneGame(map);
		cameraName = "Main";
		playerName = "Player1";
		
		/*-------------OPTIMIZATION-------------*/
	
		
		/*-------------TERRAIN------------------*/

        /*--------------GAME OBJECTS-------------*/
		
		List<Entity> normalMapEntities = EntitiesManager.createNormalMappedEntities(loader);


		
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = ObjectUtils.loadStaticModel("cube", "cube1", loader);
		Player player1 = new PlayerTextured(playerName,cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		player1.getModel().getTexture().setReflectiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveIndex(1.33f);
		player1.getModel().getTexture().setShineDamper(5.0f);

		
		/*------------------CHUNKS-------------------*/
		VoxelGrid grid = new VoxelGrid(new Vector3f(0,0,0), 80);
		Terrain terrain = map.getTerrains().get("Terrain1");
		for(int x = 0; x<grid.getSize()-1; x++) {
				for(int z = 0; z<grid.getSize()-1; z++) {
					for(int y = 0; y<grid.getSize()-1; y++) {
						float height = terrain.getHeightOfTerrain(x, z);
						if(height>=0) {
							if(y< (int) height) {
								grid.getVoxel(x, y, z).setAir(false);
							}
						}
					}
				}
		}
		
		
		/*------------------CAMERA--------------------*/
		CameraPlayer camera = new CameraPlayer(player1, cameraName);
	
		

		
		/*------------------LIGHTS----------------*/
		Light sun = new Light("Sun", new Vector3f(-100000,150000,-100000), new Vector3f(1.3f,1.3f,1.3f));
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));
		
		
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
		GuiText text = new GuiText("Version","This is an Alfa-version of the game engine", 
				3, font, new Vector2f(0.25f, 0), 0.5f, true);
		
		text.setColour(1, 0, 0);
		
		/*--------------AUDIO----------------*/
		
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		AudioSource ambientSource = new AudioSource("birds", "forest.wav", 200);
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(10, 20, 10);			
	
		
		/*--------------GUI-----------------*/
		List<GuiTexture> guiList = GuiManager.createGui(loader);

		
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

		
		/*---------------IN GAME TOOLS--------------*/	
		this.picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		/*---------------PREPARE-------------*/
		enviroRenderer = new EnvironmentMapRenderer(renderer.getProjectionMatrix());
		game.onStart();
		
		/*---------------SCENE-------------*/
		
		scene.addVoxelGrid(grid);
		scene.addAllEntities(normalMapEntities);
		scene.addPlayer(player1);
		scene.addEntity(player1);
		scene.addCamera(camera);
		scene.addLight(sun);
		scene.addAudioSource(ambientSource);
		scene.addAllGuis(guiList);
		scene.addAllWaters(waterList);
		

		spreadEntitiesOnHeights(scene.getEntities().values());
	}
	
	@Override
	public void run() {		
		while(!Display.isCloseRequested()) {
			render();
			DisplayManager.updateDisplay(); 
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				break;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
				isScenePaused = !isScenePaused;
				setScenePaused(isScenePaused);
			}
			
		}
		
		cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
	private void render() {
		game.onUpdate();  
    	/*
    	 * need to be deleted:
    	 */
		scene.getCameras().get(cameraName).move();	
		AudioMaster.setListenerData(scene.getCameras().get(cameraName).getPosition().x, scene.getCameras().get(cameraName).getPosition().y, scene.getCameras().get(cameraName).getPosition().z);
		this.environmentMap = Texture.newEmptyCubeMap(128);
    	enviroRenderer.render(environmentMap, scene.getEntities().values(), scene.getTerrains().values(), scene.getCameras().get(cameraName));
    	scene.getPlayers().get(playerName).move(scene.getTerrains().values());
		
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
    	GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
	    renderToScreen();
	}
	
	protected void renderReflectionTexture() {
    	renderer.renderShadowMap(scene.getEntities().values(), scene.getLights().get("Sun"), scene.getCameras().get(cameraName));
    	waterFBOs.bindReflectionFrameBuffer();
		float distance = 2 * (scene.getCameras().get(cameraName).getPosition().y - scene.getWaters().get("Water").getHeight());
		scene.getCameras().get(cameraName).getPosition().y -= distance;
		scene.getCameras().get(cameraName).invertPitch();
		//renderer.processEntity(map.getPlayers().get(playerName));
		renderer.renderScene(scene.getEntities().values(), scene.getTerrains().values(), scene.getVoxelGrids().values(), scene.getLights().values(), 
				scene.getCameras().get(cameraName), new Vector4f(0, 1, 0, -scene.getWaters().get("Water").getHeight()), environmentMap);
		scene.getCameras().get(cameraName).getPosition().y += distance;
		scene.getCameras().get(cameraName).invertPitch();
    }
    
    protected void renderRefractionTexture() {
    	waterFBOs.bindRefractionFrameBuffer();
		renderer.renderScene(scene.getEntities().values(), scene.getTerrains().values(), scene.getVoxelGrids().values(), scene.getLights().values(), 
				scene.getCameras().get(cameraName), new Vector4f(0, -1, 0, scene.getWaters().get("Water").getHeight()+1f),environmentMap);
    }
    
    protected void renderToScreen() {
		waterFBOs.unbindCurrentFrameBuffer();
		multisampleFbo.bindFrameBuffer();
		optimisation.optimize(scene.getCameras().get(cameraName), scene.getEntities().values(), scene.getTerrains().values(), scene.getVoxelGrids().values());
	    renderer.renderScene(scene.getEntities().values(), scene.getTerrains().values(), scene.getVoxelGrids().values(), scene.getLights().values(), 
	    		scene.getCameras().get(cameraName), new Vector4f(0, -1, 0, 15), environmentMap);
	    waterRenderer.render(scene.getWaters().values(), scene.getCameras().get(cameraName), scene.getLights().get("Sun"));
	    ParticleMaster.renderParticles(scene.getCameras().get(cameraName));
	    multisampleFbo.unbindFrameBuffer();
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
	    multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
	    PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
	    guiRenderer.render(scene.getGuis().values());
	    renderText();
    }
    
    protected void renderParticles() {
    	scene.getParticles().get("Cosmic").setPosition(scene.getPlayers().get(playerName).getPosition());
    	scene.getParticles().get("Cosmic").generateParticles();
    	scene.getParticles().get("Star").generateParticles();
		ParticleMaster.update(scene.getCameras().get(cameraName));
    }
    
    protected GuiText createFPSText(float FPS) {
    	return new GuiText("FPS", "FPS: " + String.valueOf((int)FPS), 2, font, new Vector2f(0.65f, 0), 0.5f, true);
	}
    
    protected GuiText createPickerCoordsText(MousePicker picker) {
    	picker.update();
		String text = (String) String.valueOf(picker.getCurrentRay());
		return new GuiText("Coords", text, 1, font, new Vector2f(0.3f, 0.2f), 1f, true);    	
    }
    
    protected void renderText() {
    	GuiText fpsText = createFPSText(1 / DisplayManager.getFrameTimeSeconds());
	    fpsText.setColour(1, 0, 0);
	    GuiText coordsText = createPickerCoordsText(picker);
	    coordsText.setColour(1, 0, 0);
	    TextMaster.render();
	    fpsText.remove();
	    coordsText.remove();	
    } 
    	
    protected void spreadEntitiesOnHeights(Collection<Entity> entities) {
		if (!entities.isEmpty()) {
			for(Entity entity : entities){
				float terrainHeight = 0;
				
				for(Terrain terrain : scene.getTerrains().values()){
					terrainHeight += terrain.getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z);
				}
				entity.setPosition(new Vector3f(entity.getPosition().x, terrainHeight, entity.getPosition().z));
			}
		}
	}
    
    protected void spreadParitclesOnHeights(Collection<ParticleSystem> systems) {
		if (!systems.isEmpty()) {
			for(ParticleSystem system : systems){
				float terrainHeight = 0;
				
				for(Terrain terrain : scene.getTerrains().values()){
					terrainHeight += terrain.getHeightOfTerrain(system.getPosition().x, system.getPosition().z);
				}
				system.setPosition(new Vector3f(system.getPosition().x, terrainHeight, system.getPosition().z));
			}
		}
	}
    
    
    public void setTerrainWiredFrame(boolean value) {
		renderer.setTerrainWiredFrame(value);
	}
	
	public void setEntityWiredFrame(boolean value) {
		renderer.setEntityWiredFrame(value);
	}
	
	public void setScenePaused(boolean value) {
		isPaused = value;
	}		
	
    public GameMap getMap() {
		return map;
	}
	
	

	@Override
	public void exit() {
		Display.destroy();		
	}

	@Override
	public void loadGameSettings() {
		SettingsLoader setLoader = new SettingsTXTLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		loadMap(settings.getMapName());			
	}
	
	public void cleanUp() {
    	PostProcessing.cleanUp();
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		multisampleFbo.cleanUp();
		TextMaster.cleanUp();
		waterFBOs.cleanUp();
		guiRenderer.cleanUp();
		AudioMaster.cleanUp();
		renderer.cleanUp();
		//enviroRenderer.cleanUp;
		loader.cleanUp();
    }

	@Override
	public SceneO getScene() {
		// TODO Auto-generated method stub
		return null;
	}



}
