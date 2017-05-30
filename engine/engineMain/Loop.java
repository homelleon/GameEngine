package engineMain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import gameMain.GameInterface;
import gameMain.MyGame;
import inputs.MouseGame;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsXMLLoader;
import maps.ObjectMapInterface;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.SceneRenderer;
import scene.Scene;
import scene.SceneInterface;
import scene.SceneManager;
import scene.SceneManagerInterface;

/**
 * Game looping system that initialize preloaded game variables and objects and
 * updates main game systems. 
 * 
 * @author homelleon
 * @version 1.0
 * @see LoopInterface
 */
public class Loop implements LoopInterface {
	
	/*
	 * LoopGame - главный игровой цикл
	 * 03.02.17
	 * --------------------
	 */
	private static final String SETTINGS_NAME = "settings";
		
	private Loader loader;
	private MasterRenderer renderer;
    private SceneRenderer sceneRenderer;
    
    private SceneManagerInterface sceneManager;
	
    private SceneInterface scene;
    private GameMap map;
    
    private GameInterface game = new MyGame();
    
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
		
		this.scene = new Scene(map);		
		this.sceneRenderer = new SceneRenderer();
		this.sceneManager = new SceneManager();	
		sceneManager.init(scene, loader);
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
		sceneRenderer.render(loader, isPaused);
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
		MapsLoader mapLoader = new MapsXMLLoader();
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
		MapsLoader mapLoader = new MapsXMLLoader();
		ObjectMapInterface objectMap = mapLoader.loadObjectMap(name, loader);
	}
	
	/**
	 * Loads game settings and sets name to map and object map. After that it
	 * loads map and object map using name written in the game settings file.
	 * 
	 * @see #loadMap(String)
	 * @see #loadObjectMap(String)
	 */
	private void loadGameSettings() {
		SettingsLoaderInterface setLoader = new SettingsXMLLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		loadMap(settings.getMapName());
		loadObjectMap(settings.getObjectMapName());
	}
	
	@Override
	public SceneInterface getScene() {
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
