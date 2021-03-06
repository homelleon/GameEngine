package core.loop;

import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.GameCore;
import core.debug.EngineDebug;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import core.settings.GameSettings;
import core.settings.parser.SettingsXMLParser;
import game.game.IGame;
import manager.scene.IObjectManager;
import manager.scene.ISceneManager;
import manager.scene.SceneManager;
import map.parser.LevelMapXMLParser;
import map.parser.ModelMapXMLParser;
import map.parser.RawMapXMLParser;
import map.raw.IRawManager;
import object.audio.master.AudioMaster;
import object.audio.master.IAudioMaster;
import object.input.MouseGame;
import object.scene.IScene;
import object.scene.Scene;
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
	private IRawManager rawMap;

	private IGame game;

	private boolean mapIsLoaded = false;
	private boolean isPaused = false;
	private boolean isExit = false;

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
			loadModelMap("defaultModelMap");
		}
		IAudioMaster audioMaster = new AudioMaster();
		this.scene = new Scene(levelMap, audioMaster);
		this.sceneRenderer = new SceneRenderer();
		this.sceneManager = new SceneManager();
		sceneManager.init(scene, loader);
	}

	@Override
	public void run() {
		prepare();
		while (!Display.isCloseRequested()) {
			if (this.isExit) {
				break;
			}
			update();
		}
		clean();
	}
	
	@Override
	public void exit() {
		this.isExit = true;
	}

	/**
	 * Initialize scene, rendering system, mouse settings, and game events on
	 * start.
	 */
	private void prepare() {
		initialize();
		sceneRenderer.initialize(scene);
		MouseGame.initilize(10);
		MouseGame.switchCoursorVisibility();
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
	private void clean() {
		this.scene.clean();
		this.loader.clean();
		this.sceneRenderer.clean();
		this.levelMap.clean();
		this.modelMap.clean();
		this.rawMap.clean();
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
	private void loadModelMap(String name) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading models...");
		}
		String path = EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML;
		if(new File(path).exists()) {
			IXMLLoader xmlLoader = new XMLFileLoader(path);
			IObjectParser<IObjectManager> mapParser = new ModelMapXMLParser(xmlLoader.load(), rawMap);
			this.modelMap = mapParser.parse();
			this.mapIsLoaded = true;
		} else {
			throw new NullPointerException("File " + path + " is not existed! Can't load model map!");
		}
	}

	/**
	 * Loads object map for default editor object menu.
	 * 
	 * @param name
	 *            String value of the file name
	 * 
	 * @see #loadGameSettings()
	 */
	private void loadLevelMap(String name) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading objects...");
		}
		String path = EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML;
		if(new File(path).exists()) {
			IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
			IObjectParser<IObjectManager> mapParser = new LevelMapXMLParser(xmlLoader.load(), this.modelMap);
			this.levelMap = mapParser.parse();
		} else {
			throw new NullPointerException("File " + path + " is not existed! Can't load level map!");
		}
	}
	
	private void loadRawMap(String name) {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading raws...");
		}
		String path = EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML;
		if(new File(path).exists()) {
			IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
			IObjectParser<IRawManager> mapParser = new RawMapXMLParser(xmlLoader.load());
			this.rawMap = mapParser.parse();
		} else {
			throw new NullPointerException("File " + path + " is not existed! Can't load raw map!");
		}
	}

	/**
	 * Loads game settings and sets name to map and object map. After that it
	 * loads map and object map using name written in the game settings file.
	 * 
	 * @see #loadModelMap(String)
	 * @see #loadLevelMap(String)
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
			System.out.println("> " + settings.getRawMapName());
			System.out.println("> " + settings.getModelMapName());
			System.out.println("> " + settings.getLevelMapName());
		}
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading complete...");
		}
		loadRawMap(settings.getRawMapName());
		loadModelMap(settings.getModelMapName());
		loadLevelMap(settings.getLevelMapName());
	}

	@Override
	public IScene getScene() {
		return this.scene;
	}

	@Override
	public void setTerrainWiredFrame(boolean value) {
		this.renderer.setTerrainWiredFrame(value);
	}

	@Override
	public void setEntityWiredFrame(boolean value) {
		this.renderer.setEntityWiredFrame(value);
	}

	@Override
	public void setScenePaused(boolean value) {
		MouseGame.centerCoursor();
		MouseGame.switchCoursorVisibility();
		this.isPaused = value;
	}

	@Override
	public boolean getIsScenePaused() {
		return this.isPaused;
	}

}
