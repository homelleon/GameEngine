package engineMain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import gameMain.Game;
import gameMain.MyGame;
import inputs.MouseGame;
import maps.GameMap;
import maps.MapsLoader;
import maps.MapsTXTLoader;
import maps.ObjectMap;
import renderEngine.Loader;
import renderEngine.MasterRendererSimple;
import renderEngine.SceneRenderer;
import scene.Scene;
import scene.SceneGame;
import scene.SceneManager;
import scene.SceneManagerDefault;

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
	 * LoopGame - главный игровой цикл
	 * 03.02.17
	 * --------------------
	 */
	private static final String SETTINGS_NAME = "settings";
		
	private Loader loader;
	private MasterRendererSimple renderer;
    private SceneRenderer sceneRenderer;
    
    private SceneManager sceneManager;
	
    private Scene scene;
    private GameMap map;
    
    private Game game = new MyGame();
    
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
		
		this.scene = new SceneGame(map, loader);		
		this.sceneRenderer = new SceneRenderer();
		this.sceneManager = new SceneManagerDefault();
		scene.getTexts().getMaster().init(loader);
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
