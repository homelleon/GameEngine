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
import manager.scene.SceneFactory;
import manager.scene.SceneFactoryImpl;
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
	private Scene scene;
	private Game game;

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

	public void run() {
		prepare();
		sceneRenderer.render(true);
		while (!Display.isCloseRequested()) {
			if (isExit) break;			
			update();
		}
		
		clean();
	}

	private void prepare() {
		if (isEditMode) {
			initializeEditorMode();
		} else {
			initializeGameMode();
		}
		
		sceneRenderer.initialize(scene);
		MouseGame.initilize(3);
		
		if (isEditMode) return;
		
		MouseGame.switchCoursorVisibility();
		this.game = GameCore.loadGame();
		game.__onStart();
	}

	private void initializeGameMode() {
		DisplayManager.createDisplay();
		/*--------------PRE LOAD TOOLS-------------*/		
		sceneRenderer = new GameSceneRenderer();
		SceneFactory sceneFactory = new SceneFactoryImpl();		
		scene = sceneFactory.create(loadMap(), EngineSettings.ENGINE_MODE_GAME);		
	}
	
	private ObjectManager loadMap() {
		GameSettings settings = loadGameSettings();
		RawManager rawMap = loadRawMap(settings.getRawMapName());
		ObjectManager modelMap = loadModelMap(settings.getModelMapName(), rawMap);
		ObjectManager levelMap = loadLevelMap(settings.getLevelMapName(), modelMap);
		return levelMap;
	}

	private GameSettings loadGameSettings() {
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
		
		return settings;
	}
	
	private RawManager loadRawMap(String name) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Raw map");
			EngineDebug.println("Loading raws...");
		}
		
		IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		IObjectParser<RawManager> mapParser = new RawMapXMLParser(xmlLoader.load());
		RawManager rawMap = mapParser.parse();
		
		return rawMap;
	}

	private ObjectManager loadModelMap(String name, RawManager rawMap) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Model map");
			EngineDebug.println("Loading models...");
		}
		
		String path = EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML;
		IXMLLoader xmlLoader = new XMLFileLoader(path);
		IObjectParser<ObjectManager> mapParser = new ModelMapXMLParser(xmlLoader.load(), rawMap);
		ObjectManager modelMap = mapParser.parse();
		
		return modelMap;
	}

	private ObjectManager loadLevelMap(String name, ObjectManager modelMap) {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("Level map");
			EngineDebug.println("Loading level...");
		}
		
		IXMLLoader xmlLoader = new XMLFileLoader(EngineSettings.MAP_PATH + name + EngineSettings.EXTENSION_XML);
		IObjectParser<ObjectManager> mapParser = new LevelMapXMLParser(xmlLoader.load(), modelMap);
		ObjectManager levelMap = mapParser.parse();
		
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Total loaded entities: " + levelMap.getEntities().getAll().stream().count(), 2);
			EngineDebug.println("Total loaded terrains: " + levelMap.getTerrains().getAll().stream().count(), 2);
		}
		
		return levelMap;
	}
	
	private void initializeEditorMode() {
		DisplayManager.createDisplay(frame);
		frame.pack();
		/*--------------PRE LOAD TOOLS-------------*/
		sceneRenderer = new EditorSceneRenderer();
		SceneFactory sceneFactory = new SceneFactoryImpl();
		scene = sceneFactory.create(null, EngineSettings.ENGINE_MODE_EDITOR);
	}
	
	public void exit() {
		isExit = true;
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
		DisplayManager.closeDisplay();
	}

}
