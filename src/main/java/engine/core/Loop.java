package core;

import org.lwjgl.opengl.Display;

import control.MouseGame;
import core.game.Game;
import core.game.GameCore;
import core.settings.EngineSettings;
import core.settings.GameSettings;
import core.settings.SettingsXMLParser;
import manager.RawManager;
import manager.scene.ObjectManager;
import manager.scene.SceneManager;
import manager.scene.SceneManagerImpl;
import object.audio.AudioMaster;
import primitive.buffer.Loader;
import renderer.scene.EditorSceneRenderer;
import renderer.scene.GameSceneRenderer;
import renderer.scene.SceneRenderer;
import scene.Scene;
import scene.parser.LevelMapXMLParser;
import scene.parser.ModelMapXMLParser;
import scene.parser.RawMapXMLParser;
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
public class Loop {
	
	private boolean isEditMode = false;
	private DataEditorFrame frame;

	private static Loop instance;
	private static final String SETTINGS_NAME = "Settings";
	
	private SceneRenderer sceneRenderer;

	private SceneManager sceneManager;

	private Scene scene;
	private ObjectManager modelMap;
	private ObjectManager levelMap;
	private RawManager rawMap;

	private Game game;

	private boolean mapIsLoaded = false;
	private boolean isPaused = false;
	private boolean isExit = false;

	private Loop() {}

	public static Loop getInstance() {
		if (instance == null)
			instance = new Loop();
		return instance;
	}
	
	public void setEditMode(boolean value) {
		isEditMode = value;
	}
	
	public boolean getEditMode() {
		return isEditMode;
	}
	
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
		if (!mapIsLoaded) 
			loadModelMap("defaultModelMap");
		AudioMaster audioMaster = new AudioMaster();
		scene = new Scene(levelMap, audioMaster);
		sceneRenderer = new GameSceneRenderer();
		sceneManager = new SceneManagerImpl();
		sceneManager.init(scene, EngineSettings.ENGINE_MODE_GAME);
	}
	
	private void initializeEditorMode() {
		DisplayManager.createDisplay(frame);
		frame.pack();
		/*--------------PRE LOAD TOOLS-------------*/
		scene = new Scene();
		sceneRenderer = new EditorSceneRenderer();
		sceneManager = new SceneManagerImpl();
		sceneManager.init(scene, EngineSettings.ENGINE_MODE_EDITOR);
	}

	public void run() {
		prepare();
		sceneRenderer.render(true);
		while (!Display.isCloseRequested()) {
			if (isExit) break;			
			update();
		}
		
		clean();
	}
	
	public void exit() {
		isExit = true;
	}

	/**
	 * Initialize scene, rendering system, mouse settings, and game events on
	 * start.
	 */
	private void prepare() {
		if (isEditMode) {
			initializeEditorMode();
		} else {
			initializeGameMode();
		}
		
		sceneRenderer.initialize(scene);
		MouseGame.initilize(3);
		
		if (!isEditMode) {
			MouseGame.switchCoursorVisibility();
			this.game = GameCore.loadGame();
			game.__onStart();
		}
	}

	/**
	 * Updates game events, mouse settings, display and render scene.
	 */
	private void update() {
		if (!isEditMode) {
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
		scene.clean();
		Loader.getInstance().clean();
		sceneRenderer.clean();
		
		if(!isEditMode) {
			levelMap.clean();
			modelMap.clean();
			rawMap.clean();
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
		IXMLLoader xmlLoader = new XMLFileLoader(path);
		IObjectParser<ObjectManager> mapParser = new ModelMapXMLParser(xmlLoader.load(), rawMap);
		modelMap = mapParser.parse();
		mapIsLoaded = true;
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
		
		IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		IObjectParser<ObjectManager> mapParser = new LevelMapXMLParser(xmlLoader.load(), this.modelMap);
		levelMap = mapParser.parse();
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Total loaded entities: " + levelMap.getEntities().getAll().stream().count(), 2);
			EngineDebug.println("Total loaded terrains: " + levelMap.getTerrains().getAll().stream().count(), 2);
		}
	}
	
	private void loadRawMap(String name) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Raw map");
			EngineDebug.println("Loading raws...");
		}
		
		IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		IObjectParser<RawManager> mapParser = new RawMapXMLParser(xmlLoader.load());
		rawMap = mapParser.parse();
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

	public Scene getScene() {
		return scene;
	}

	public void setScenePaused(boolean value) {
		MouseGame.centerCoursor();
		MouseGame.switchCoursorVisibility();
		isPaused = value;
	}

	public boolean getIsScenePaused() {
		return isPaused;
	}

}
