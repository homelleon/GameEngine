package core.loop;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.GameCore;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import core.settings.GameSettings;
import core.settings.parser.SettingsXMLParser;
import game.game.GameInterface;
import object.input.MouseGame;
import object.map.levelMap.LevelMapInterface;
import object.map.levelMap.LevelMapXMLParser;
import object.map.modelMap.ModelMapInterface;
import object.map.modelMap.ModelMapXMLParser;
import object.scene.manager.SceneManager;
import object.scene.manager.SceneManagerInterface;
import object.scene.scene.Scene;
import object.scene.scene.SceneInterface;
import renderer.loader.Loader;
import renderer.object.main.MainRenderer;
import renderer.scene.SceneRenderer;
import tool.xml.loader.XMLFileLoader;
import tool.xml.loader.XMLLoaderInterface;
import tool.xml.parser.ObjectParserInterface;

/**
 * Game looping system that initialize preloaded game variables and objects and
 * updates main game systems.
 * 
 * @author homelleon
 * @version 1.0
 * @see LoopInterface
 */
public class Loop implements LoopInterface {

	private static Loop instance;
	private static final String SETTINGS_NAME = "settings";

	private Loader loader;
	private MainRenderer renderer;
	private SceneRenderer sceneRenderer;

	private SceneManagerInterface sceneManager;

	private SceneInterface scene;
	private ModelMapInterface modelMap;
	private LevelMapInterface levelMap;

	private GameInterface game;

	private boolean mapIsLoaded = false;
	private boolean isPaused = false;

	private Loop() {
	}

	public static Loop getInstance() {
		if (instance == null) {
			instance = new Loop();
		}
		return instance;
	}

	/**
	 * Initilize display, load game settings and setup scene objects.
	 * 
	 * @see #prepare()
	 */
	private void initialize() {
		DisplayManager.createDisplay();
		/*--------------PRE LOAD TOOLS-------------*/
		this.loader = Loader.getInstance();

		loadGameSettings();
		if (!this.mapIsLoaded) {
			loadMap("map");
		}

		this.scene = new Scene(modelMap, levelMap);
		this.sceneRenderer = new SceneRenderer();
		this.sceneManager = new SceneManager();
		sceneManager.init(scene, loader);
	}

	@Override
	public void run() {
		prepare();
		while (!Display.isCloseRequested()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
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
		initialize();
		sceneRenderer.initialize(scene);
		MouseGame.initilize(10);
		this.game = GameCore.loadGame();
		game.__onStart();
	}

	/**
	 * Updates game events, mouse settings, display and render scene.
	 */
	private void update() {
		game.__onUpdateWithPause();
		if (!this.isPaused) {
			game.__onUpdate();
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
		scene.clean();
		loader.clean();
		sceneRenderer.clean();
		DisplayManager.closeDisplay();
	}

	/**
	 * Loads map for game objects that have to be in the scene.
	 * 
	 * @param name
	 *            String value of the file name
	 * 
	 * @see #loadGameSettings()
	 */
	private void loadMap(String name) {
		XMLLoaderInterface xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		ObjectParserInterface<ModelMapInterface> mapParser = new ModelMapXMLParser(xmlLoader.load(), name);
		this.modelMap = mapParser.parse();
		this.mapIsLoaded = true;
	}

	/**
	 * Loads object map for default editor object menu.
	 * 
	 * @param name
	 *            String value of the file name
	 * 
	 * @see #loadGameSettings()
	 */
	private void loadObjectMap(String name) {
		XMLLoaderInterface xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		ObjectParserInterface<LevelMapInterface> mapParser = new LevelMapXMLParser(xmlLoader.load(), this.modelMap);
		this.levelMap = mapParser.parse();
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
				EngineSettings.SETTINGS_GAME_PATH + SETTINGS_NAME + EngineSettings.EXTENSION_XML);
		ObjectParserInterface<GameSettings> settingsParser = new SettingsXMLParser(xmlLoader.load());
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
