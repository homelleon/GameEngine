package engineMain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.AudioSource;
import cameras.Camera;
import cameras.CameraPlayer;
import entities.EntitiesManager;
import entities.Light;
import entities.Player;
import entities.PlayerTextured;
import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import guis.GuiManager;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsTXTLoader;
import models.TexturedModel;
import particles.ParticleSystem;
import particles.ParticlesManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.SceneRenderer;
import scene.ES;
import scene.Scene;
import scene.SceneGame;
import toolbox.ObjectUtils;
import water.WaterTile;

public class LoopGame implements Loop {
	
	private static final String SETTINGS_NAME = "settings";
	
	private boolean isScenePaused = false; 
	
	private Loader loader;
	private MasterRenderer renderer;
    private SceneRenderer sceneRenderer;

    private String cameraName;
    private String playerName;
	
    private Scene scene;
    private Camera camera;
    private Player player;
    private Light sun;
    private GameMap map;

    private FontType font;
    
    private boolean mapIsLoaded = false;
    private boolean isPaused = false;
	
	public LoopGame() {
		init();
	}

	private void init() {
		DisplayManager.creatDisplay();
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = Loader.getInstance();
		
		loadGameSettings();		
		if (!this.mapIsLoaded) {
			loadMap("map");
		}
		
		this.scene = new SceneGame(map);

		this.sceneRenderer = new SceneRenderer();
		
        /*--------------GAME OBJECTS-------------*/
		
		
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = ObjectUtils.loadStaticModel("cube", "cube1", loader);
		Player player1 = new PlayerTextured(playerName,cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		player1.getModel().getTexture().setReflectiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveIndex(1.33f);
		player1.getModel().getTexture().setShineDamper(5.0f);
		this.player = player1;

		
		/*------------------CHUNKS-------------------*/
		
		scene.createVoxelTerrain(80, new Vector3f(0,0,0));
		
		
		/*------------------CAMERA--------------------*/
		Camera camera = new CameraPlayer(player1, cameraName);
		this.camera = camera;
		

		
		/*------------------LIGHTS----------------*/
		//lights.add(new Light(new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1, 0.01f, 0.002f)));
		//lights.add(new Light(new Vector3f(20,2,20),new Vector3f(0,10,0), new Vector3f(0, 0.01f, 0.002f)));


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
			
		/*--------------WATER----------------*/

		List<WaterTile> waterList = new ArrayList<WaterTile>();
		WaterTile water = new  WaterTile("Water", 0, 0, -4, 1000);
		waterList.add(water);
		waterList.stream().forEach((i) -> i.setTilingSize(0.05f));
		waterList.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waterList.stream().forEach((i) -> i.setWaveStrength(0.1f));

		/*---------------PREPARE-------------*/

		//game.onStart();
		
		/*---------------SCENE-------------*/
		
		scene.setPlayer(player1);
		scene.addEntity(player1);
		scene.addAllEntities(EntitiesManager.createNormalMappedEntities(loader));
		scene.setCamera(camera);
		scene.setSun(new Light("Sun", new Vector3f(-100000,150000,-100000), new Vector3f(1.3f,1.3f,1.3f)));
		scene.addLight(scene.getSun());
		scene.addAudioSource(ambientSource);
		scene.addAllGuis(GuiManager.createGui(loader));
		scene.addAllWaters(waterList);
		scene.addAllParticles(ParticlesManager.createParticleSystem(loader));
		
		scene.spreadEntitiesOnHeights();
		scene.getEntities().get("Cuby4").getModel().getTexture().setReflectiveFactor(1.2f);
	}
	
	@Override
	public void run() {	
		sceneRenderer.init(scene, loader);
		while(!Display.isCloseRequested()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				break;
			}	
			sceneRenderer.render(scene, font, loader);
			DisplayManager.updateDisplay();		
		}
		
		cleanUp();
		DisplayManager.closeDisplay();
		
	}
	     
	
	private void loadMap(String name) {
		MapsLoader mapLoader = new MapsTXTLoader();
		this.map = mapLoader.loadMap(name, loader);	
		this.mapIsLoaded = true;
	}
	
	private void loadGameSettings() {
		SettingsLoader setLoader = new SettingsTXTLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		loadMap(settings.getMapName());			
	}
	
	@Override
	public Scene getScene() {
		return this.scene;
	}    	    
    
    @Override
    public void setTerrainWiredFrame(boolean value) {
		renderer.setTerrainWiredFrame(value);
	}
    
    @Override
	public void setEntityWiredFrame(boolean value) {
		renderer.setEntityWiredFrame(value);
	}
	
    @Override
	public void setScenePaused(boolean value) {
		isPaused = value;
	}		

	
	private void cleanUp() {
		scene.cleanUp();
		loader.cleanUp();
		sceneRenderer.cleanUp();
    }

	



}
