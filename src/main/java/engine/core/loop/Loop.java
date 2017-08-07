package core.loop;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.GameCore;
import core.debug.EngineDebug;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import core.settings.GameSettings;
import core.settings.parser.SettingsXMLParser;
import game.game.IGame;
import object.input.MouseGame;
import object.map.objectMap.IObjectManager;
import object.map.parser.LevelMapXMLParser;
import object.map.parser.ModelMapXMLParser;
import object.scene.manager.ISceneManager;
import object.scene.manager.SceneManager;
import object.scene.scene.IScene;
import object.scene.scene.Scene;
import renderer.loader.Loader;
import renderer.object.main.MainRenderer;
import renderer.scene.SceneRenderer;
import tool.xml.loader.IXMLLoader;
import tool.xml.loader.XMLFileLoader;
import tool.xml.parser.IObjectParser;

/**
 * Game looping system that initialize preloaded game variables and objects and
 * updates main game systems.
 * 
 * @author homelleon
 * @version 1.0
 * @see ILoop
 */
public class Loop implements ILoop {

	private static Loop instance;
	private static final String SETTINGS_NAME = "settings";

	private Loader loader;
	private MainRenderer renderer;
	private SceneRenderer sceneRenderer;

	private ISceneManager sceneManager;

	private IScene scene;
	private IObjectManager modelMap;
	private IObjectManager levelMap;

	private IGame game;

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
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading models...");
		}
		IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		IObjectParser<IObjectManager> mapParser = new ModelMapXMLParser(xmlLoader.load(), name);
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
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading objects...");
		}
		IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		IObjectParser<IObjectManager> mapParser = new LevelMapXMLParser(xmlLoader.load(), this.modelMap);
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
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading game settings...");
		}
		IXMLLoader xmlLoader = new XMLFileLoader(
				EngineSettings.SETTINGS_GAME_PATH + SETTINGS_NAME + EngineSettings.EXTENSION_XML);
		IObjectParser<GameSettings> settingsParser = new SettingsXMLParser(xmlLoader.load());
		GameSettings settings = settingsParser.parse();
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading complete...");
		}
		loadMap(settings.getMapName());
		loadObjectMap(settings.getObjectMapName());
	}

	@Override
	public IScene getScene() {
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
