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
import audio.AudioMasterBuffered;
import audio.AudioSource;
import audio.AudioSourceSimple;
import cameras.CameraPlayer;
import entities.EntityManagerStructured;
import entities.Player;
import entities.PlayerTextured;
import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import gameMain.Game;
import gameMain.MyGame;
import guis.GuiManager;
import inputs.MouseGame;
import lights.Light;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsTXTLoader;
import maps.ObjectMap;
import models.TexturedModel;
import particles.ParticleManagerStructured;
import renderEngine.Loader;
import renderEngine.MasterRendererSimple;
import renderEngine.SceneRenderer;
import scene.ES;
import scene.Scene;
import scene.SceneGame;
import toolbox.ObjectUtils;
import water.WaterTile;

/**
 * Game looping system that initialize preloaded game variables and objects and
 * updates main game systems. 
 * 
 * @author homelleon
 * @version 1.0
 * @see Loop
 */
public class LoopGame implements Loop {
	
	/*
	 * LoopGame - ������� ������� ����
	 * 03.02.17
	 * --------------------
	 */
	private static final String SETTINGS_NAME = "settings";
		
	private Loader loader;
	private MasterRendererSimple renderer;
    private SceneRenderer sceneRenderer;

    private String cameraName;
    private String playerName;
	
    private Scene scene;
    private GameMap map;
    
    private Game game = new MyGame();

    private FontType font;
    
    private boolean mapIsLoaded = false;
    private boolean isPaused = false;
    
    /**
     * Initilize display, load game settings and setup scene objects.
     * @see #prepare() 
     */
	private void init() {
		DisplayManager.createDisplay();
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = Loader.getInstance();
		
		loadGameSettings();		
		if (!this.mapIsLoaded) {
			loadMap("map");
		}
		
		this.scene = new SceneGame(map);

		this.sceneRenderer = new SceneRenderer();

		
		/*------------------PLAYER-----------------*/
		TexturedModel cubeModel = ObjectUtils.loadStaticModel("cube", "cube1", loader);
		Player player1 = new PlayerTextured(playerName,cubeModel, new Vector3f(100, 0, 10), 0, 0, 0, 1);
		player1.getModel().getTexture().setReflectiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveFactor(1.0f);
		player1.getModel().getTexture().setRefractiveIndex(1.33f);
		player1.getModel().getTexture().setShineDamper(5.0f);

		/*----------------FONTS-----------------*/
		TextMaster.init(loader);
		this.font = 
				new FontType(loader.loadTexture(ES.FONT_PATH, "candara"),
						new File(ES.FONT_PATH + "candara.fnt"));
		GuiText text = new GuiText("Version","This is an Alfa-version of the game engine", 
				3, font, new Vector2f(0.25f, 0), 0.5f, true);
		
		text.setColour(1, 0, 0);
		
		/*--------------AUDIO----------------*/
		
		AudioMaster aduioMaster = new AudioMasterBuffered();	
		aduioMaster.init();
		aduioMaster.setListenerData(player1.getPosition());
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		AudioSource ambientSource = new AudioSourceSimple("birds", "forest.wav", 1000, aduioMaster);
		ambientSource.setLooping(true);
		ambientSource.setVolume(0.3f);
		ambientSource.play();
		ambientSource.setPosition(new Vector3f(400, 50, 400));			
			
		/*--------------WATER----------------*/

		List<WaterTile> waterList = new ArrayList<WaterTile>();
		WaterTile water = new  WaterTile("Water", 0, 0, -4, 1000);
		waterList.add(water);
		waterList.stream().forEach((i) -> i.setTilingSize(0.05f));
		waterList.stream().forEach((i) -> i.setWaterSpeed(0.7f));
		waterList.stream().forEach((i) -> i.setWaveStrength(0.1f));

		/*---------------SCENE-------------*/
		
		/*TODO: replace it by map loading system*/
		scene.setAudioMaster(aduioMaster);
		scene.setPlayer(player1);
		scene.getEntities().add(player1);
		scene.getEntities().addAll(ObjectUtils.createGrassField(500, 500, 50, 1, 0.1f, loader));
		scene.getEntities().addAll(EntityManagerStructured.createNormalMappedEntities(loader));
		scene.setCamera(new CameraPlayer(player1, cameraName));
		scene.setSun(new Light("Sun", new Vector3f(-100000,150000,-100000), new Vector3f(1.3f,1.3f,1.3f)));
		scene.getLights().add(scene.getSun());
		scene.getLights().add(new Light("Light1", new Vector3f(200,2,200),new Vector3f(10,0,0), new Vector3f(1.1f, 0.01f, 0.002f)));
		scene.getLights().add(new Light("Light2", new Vector3f(20,2,20),new Vector3f(0,5,0), new Vector3f(1, 0.01f, 0.002f)));

		scene.addAudioSource(ambientSource);
		scene.addAllGuis(GuiManager.createGui(loader));
		scene.getWaters().addAll(waterList);
		scene.getParticles().addAll(ParticleManagerStructured.createParticleSystem(loader));
		
		scene.spreadEntitiesOnHeights();
		scene.getEntities().getByName("Cuby4").getModel().getTexture().setReflectiveFactor(1.2f);
	}
	
	@Override
	public void run() {	
		prepare();
		while(!Display.isCloseRequested()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				break;
			}
			update();
		}		
		cleanUp();		
	}
	
	/**
	 * Initialize scene, rendering system, mouse settings, and game events on
	 * start. 
	 */
	private void prepare() {
		init();
		sceneRenderer.init(scene, loader);
		MouseGame.initilize(10);
		game.onStart();
	}
	
	/**
	 * Updates game events, mouse settings, display and render scene. 
	 */
	private void update() {		
		if(!this.isPaused) {
			game.onUpdate();
		}
		sceneRenderer.render(font, loader, isPaused);
		MouseGame.update();
		DisplayManager.updateDisplay();
	}
	
	/**
	 * Starts cleaning process for game looping objects and close display to
	 * exit the application. 
	 */
	private void cleanUp() {
		scene.cleanUp();
		loader.cleanUp();
		sceneRenderer.cleanUp();
		DisplayManager.closeDisplay();
    }
	
	/**
	 * Loads map for game objects that have to be in the scene.
	 * 
	 * @param name
	 * 			   String value of the file name
	 * 
	 * @see #loadGameSettings()
	 */
	private void loadMap(String name) {
		MapsLoader mapLoader = new MapsTXTLoader();
		this.map = mapLoader.loadMap(name, loader);	
		this.mapIsLoaded = true;
	}
	
	/**
	 * Loads object map for default editor object menu.
	 * 
	 * @param name
	 * 			   String value of the file name
	 * 
	 * @see #loadGameSettings()
	 */
	private void loadObjectMap(String name) {
		MapsLoader mapLoader = new MapsTXTLoader();
		ObjectMap objectMap = mapLoader.loadObjectMap(name, loader);
	}
	
	/**
	 * Loads game settings and sets name to map and object map. After that it
	 * loads map and object map using name written in the game settings file.
	 * 
	 * @see #loadMap(String)
	 * @see #loadObjectMap(String)
	 */
	private void loadGameSettings() {
		SettingsLoader setLoader = new SettingsTXTLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		loadMap(settings.getMapName());		
		loadObjectMap(settings.getObjectMapName());
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

	@Override
	public boolean getIsScenePaused() {
		return isPaused;
	}	
    
}
