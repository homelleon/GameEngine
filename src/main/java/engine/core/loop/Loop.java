package core.loop;

import java.io.File;

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
import primitive.buffer.Loader;
import renderer.main.MainRenderer;
import renderer.scene.EditorSceneRenderer;
import renderer.scene.GameSceneRenderer;
import renderer.scene.ISceneRenderer;
import tool.dataEditor.menu.DataEditorFrame;
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
	
	private boolean isEditMode = false;
	private DataEditorFrame frame;

	private static Loop instance;
	private static final String SETTINGS_NAME = "settings";

	private MainRenderer renderer;
	private ISceneRenderer sceneRenderer;

	private ISceneManager sceneManager;

	private IScene scene;
	private IObjectManager modelMap;
	private IObjectManager levelMap;
	private IRawManager rawMap;

	private IGame game;

	private boolean mapIsLoaded = false;
	private boolean isPaused = false;
	private boolean isExit = false;

	private Loop() {}

	public static Loop getInstance() {
		if (instance == null) {
			instance = new Loop();
		}
		return instance;
	}
	
	@Override
	public void setEditMode(boolean value) {
		this.isEditMode = value;
	}
	
	@Override
	public boolean getEditMode() {
		return this.isEditMode;
	}
	
	@Override
	public void setDisplayFrame(DataEditorFrame frame) {
		this.frame = frame;
	}

	/**
	 * Initilize display, load game settings and setup scene objects.
	 * 
	 * @see #prepare()
	 */
	private void initializeGameMode() {
		DisplayManager.createDisplay();
		/*--------------PRE LOAD TOOLS-------------*/
		loadGameSettings();
		if (!this.mapIsLoaded) {
			loadModelMap("defaultModelMap");
		}
		IAudioMaster audioMaster = new AudioMaster();
		this.scene = new Scene(levelMap, audioMaster);
		this.sceneRenderer = new GameSceneRenderer();
		this.sceneManager = new SceneManager();
		sceneManager.init(scene, EngineSettings.ENGINE_MODE_GAME);
	}
	
	private void initializeEditorMode() {
		DisplayManager.createDisplay(frame);
		frame.pack();
		/*--------------PRE LOAD TOOLS-------------*/
		this.scene = new Scene();
		this.sceneRenderer = new EditorSceneRenderer();
		this.sceneManager = new SceneManager();
		sceneManager.init(scene, EngineSettings.ENGINE_MODE_EDITOR);
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
		if(isEditMode) {
			initializeEditorMode();
		} else {
			initializeGameMode();
		}
		sceneRenderer.initialize(scene);
		MouseGame.initilize(3);
		if(!isEditMode) {
			MouseGame.switchCoursorVisibility();
			this.game = GameCore.loadGame();
			game.__onStart();
		}
	}

	/**
	 * Updates game events, mouse settings, display and render scene.
	 */
	private void update() {
		if(!isEditMode) {
			game.__onUpdateWithPause();
			if (!this.isPaused) {
				game.__onUpdate();
			}
		}
		sceneRenderer.render(isPaused);
		MouseGame.update();
		DisplayManager.updateDisplay();
	}

	/**
	 * Starts cleaning process for game looping objects and close display to
	 * exit the application.
	 */
	private void clean() {
		this.scene.clean();
		Loader.getInstance().clean();
		this.sceneRenderer.clean();
		if(!isEditMode) {
			this.levelMap.clean();
			this.modelMap.clean();
			this.rawMap.clean();
		}
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
			EngineDebug.printBorder();
			EngineDebug.printOpen("Model map");
			EngineDebug.println("Loading models...");
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
			EngineDebug.printBorder();
			EngineDebug.printOpen("Level map");
			EngineDebug.println("Loading level...");
		}
		String path = EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML;
		if(new File(path).exists()) {
			IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
			IObjectParser<IObjectManager> mapParser = new LevelMapXMLParser(xmlLoader.load(), this.modelMap);
			this.levelMap = mapParser.parse();
			if (EngineDebug.hasDebugPermission()) {
				EngineDebug.println("Total loaded entities: " + this.levelMap.getEntities().getAll().stream().count(), 2);
				EngineDebug.println("Total loaded terrains: " + this.levelMap.getTerrains().getAll().stream().count(), 2);
			}
		} else {
			throw new NullPointerException("File " + path + " is not existed! Can't load level map!");
		}
	}
	
	private void loadRawMap(String name) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Raw map");
			EngineDebug.println("Loading raws...");
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
			EngineDebug.printBorder();
			EngineDebug.printOpen("Game Settings");
			EngineDebug.println("Loading game settings...");
		}
		IXMLLoader xmlLoader = new XMLFileLoader(
				EngineSettings.SETTINGS_GAME_PATH + SETTINGS_NAME + EngineSettings.EXTENSION_XML);
		IObjectParser<GameSettings> settingsParser = new SettingsXMLParser(xmlLoader.load());
		GameSettings settings = settingsParser.parse();
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println(settings.getRawMapName(), 1);
			EngineDebug.println(settings.getModelMapName(), 1);
			EngineDebug.println(settings.getLevelMapName(), 1);
			EngineDebug.println("Loading complete...");
			EngineDebug.printClose("Game Settings");
			EngineDebug.printBorder();
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
