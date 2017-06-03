package core.loop;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.display.DisplayManager;
import core.settings.ES;
import core.settings.gameSettings.GameSettings;
import core.settings.gameSettings.SettingsParserInterface;
import core.settings.gameSettings.SettingsXMLParser;
import inputs.MouseGame;
import main.GameInterface;
import main.MyGame;
import maps.levelMap.LevelMapParserInterface;
import maps.levelMap.LevelMapXMLParser;
import maps.modelMap.ModelMap;
import maps.modelMap.ModelMapParserInterface;
import maps.modelMap.ModelMapXMLParser;
import renderers.Loader;
import renderers.MasterRenderer;
import renderers.scene.SceneRenderer;
import scene.Scene;
import scene.SceneInterface;
import scene.SceneManager;
import scene.SceneManagerInterface;
import toolbox.XMLUtils;
import toolbox.xmlLoader.XMLFileLoader;
import toolbox.xmlLoader.XMLLoaderInterface;

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
	 * LoopGame - ������� ������� ����
	 * 03.02.17
	 * --------------------
	 */
	private static final String SETTINGS_NAME = "settings";
		
	private Loader loader;
	private MasterRenderer renderer;
    private SceneRenderer sceneRenderer;
    
    private SceneManagerInterface sceneManager;
	
    private SceneInterface scene;
    private ModelMap map;
    
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
		XMLLoaderInterface xmlLoader = new XMLFileLoader(ES.MAP_PATH + name + XMLUtils.EXTENTION);
		ModelMapParserInterface mapParser = new ModelMapXMLParser(
				xmlLoader.load(), name, loader);
		this.map = mapParser.parseMap();	
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
		XMLLoaderInterface xmlLoader = new XMLFileLoader(ES.MAP_PATH + name + XMLUtils.EXTENTION);
		LevelMapParserInterface mapParser = new LevelMapXMLParser();
//		MapsLoaderInterface mapLoader = new MapsXMLLoader();
//		ObjectMapInterface objectMap = mapLoader.loadObjectMap(name, loader);
	}
	
	/**
	 * Loads game settings and sets name to map and object map. After that it
	 * loads map and object map using name written in the game settings file.
	 * 
	 * @see #loadMap(String)
	 * @see #loadObjectMap(String)
	 */
	private void loadGameSettings() {
		XMLLoaderInterface xmlLoader = new XMLFileLoader(
				ES.GAME_SETTINGS_PATH + SETTINGS_NAME + XMLUtils.EXTENTION);
		SettingsParserInterface settingsParser = new SettingsXMLParser(
				xmlLoader.load());
		GameSettings settings = settingsParser.parse();
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