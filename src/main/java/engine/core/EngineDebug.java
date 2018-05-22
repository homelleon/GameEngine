package core;

import java.util.ArrayList;
import java.util.List;

import control.KeyboardGame;
import control.MousePicker;
import core.settings.EngineSettings;
import object.gui.GUI;
import object.gui.GUIGroup;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import primitive.texture.Texture2D;
import scene.Scene;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;

/**
 * Game engine debug util.
 * 
 * @author homelleon
 * @version 1.1
 */
public class EngineDebug {

	private static final String LEVEL_SEPARATOR = "  |";
	
	/* permission variables */
	public static final int PERMISSION_NONE = 0;			// No permission status
	public static final int PERMISSION_SIMPLE = 1;			// Simple permission status
	public static final int PERMISSION_HARD = 2;			// Hard permission status
	private static int debugPermission = PERMISSION_SIMPLE;	// Debug permission status field


	/* bounding variables */
	public static final int BOUNDING_NONE = 0;				// No bounding shape is visible
	public static final int BOUNDING_BOX = 1;				// Bounding box is visible
	public static final int BOUNDING_SPHERE = 2;			// Bounding sphere is visible
	public static final int BOUNDING_BOX_AND_SPHERE = 3;	// Bounding sphere and box are visible
	private static int boundingMode = BOUNDING_NONE;		// Bounding shape visbility status field
	
	private String statusGroupName = "statusGroup";
	private Scene scene;
	GUIText fpsText;
	GUIText coordsText;
	
	public EngineDebug(Scene scene) {
		this.scene = scene;
	}
	
	public void initialize() {
		// debug text
		String fontName = "candara";
		Color fontColor = new Color(255, 0, 0);
		
		fpsText = createFPSText(Math.round(1 / DisplayManager.getFrameTimeSeconds()), fontName);
		fpsText.setColor(fontColor);
		
		coordsText = createPickerCoordsText(scene.getMousePicker(), fontName);
		coordsText.setColor(fontColor);
		
		// debug texture
		List<GUITexture> textureList = new ArrayList<GUITexture>();
		
		/* terrain */
		Texture2D heightMap = scene.getTerrains().get("Terrain1").getMaterial().getHeightMap();
		Texture2D normalMap = scene.getTerrains().get("Terrain1").getMaterial().getNormalMap();
		GUITexture debugTexture1 = new GUITexture("debugTexture1", heightMap, new Vector2f(-0.5f, 0), new Vector2f(0.3f, 0.3f));
		GUITexture debugTexture2 = new GUITexture("debugTexture2", normalMap, new Vector2f(0.5f, 0), new Vector2f(0.3f, 0.3f));
		textureList.add(debugTexture1);
		textureList.add(debugTexture2);
		List<GUIText> textList = new ArrayList<GUIText>();
		textList.add(fpsText);
		textList.add(coordsText);
		/* end */
		
		String statusGUIName = "status";
		GUI statusInterface = new GUI(statusGUIName, textureList, textList);
		scene.getUI().getGroups().createEmpty(statusGroupName);
		scene.getUI().getGroups().get(statusGroupName).add(statusInterface);
	}
	
	private GUIText createFPSText(float FPS, String fontName) {
		GUIText guiText = new GUIText("FPS", "FPS: " + String.valueOf((int) FPS), 2f, fontName, new Vector2f(0.65f, 0.9f),
				0.5f, true);
		scene.getUI().getComponent().getTexts().add(guiText);
		return guiText;
	}

	private GUIText createPickerCoordsText(MousePicker picker, String fontName) {
		String text = String.valueOf(picker.getCurrentRay());
		GUIText guiText = new GUIText("Coords", text, 1, fontName, new Vector2f(0.3f, 0.8f), 1f, true);
		scene.getUI().getComponent().getTexts().add(guiText);
		return guiText;
	}
	
	public void update() {
		checkInputs();
		float fps = Math.round(1 / DisplayManager.getFrameTimeSeconds());
		scene.getUI().getComponent().getTexts()
			.changeContent(fpsText.getName(), "FPS: " + String.valueOf((int) fps));
		String coords = String.valueOf(scene.getMousePicker().getCurrentRay());
		scene.getUI().getComponent().getTexts()
			.changeContent(coordsText.getName(), coords);
	}
	
	private void checkInputs() {
		GUIGroup statusGUIGroup = scene.getUI().getGroups().get(statusGroupName);
		
		if (EngineDebug.hasHardDebugPermission()) {
			if (!statusGUIGroup.getIsVisible())
				statusGUIGroup.show();
		} else {
			if (statusGUIGroup.getIsVisible())
				statusGUIGroup.hide();
		}
		
		if (EngineDebug.hasDebugPermission()) {
			if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_INFORMATION)) {
				EngineDebug.switchDebugPermission();
			}
			
			if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_BOUNDING_BOX)) {
				EngineDebug.switchBounding();
			}
			
			if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_WIRED_FRAME)) {
				EngineMain.switchWiredFrameMode();
			}
		}
	}
	/**
	 * Changes bounding surface mode.
	 */
	public static void switchBounding() {
		if (hasDebugPermission()) {
			if (boundingMode == BOUNDING_BOX_AND_SPHERE) {
				boundingMode = BOUNDING_NONE;
			} else {
				boundingMode++;
			}
			String mode = "Error";
			switch (boundingMode) {
				case BOUNDING_NONE:
					mode = "No bounding";
					break;
				case BOUNDING_BOX:
					mode = "Box bounding";
					break;
				case BOUNDING_SPHERE:
					mode = "Sphere bounding";
					break;
				case BOUNDING_BOX_AND_SPHERE:
					mode = "Box and Sphere bounding";
					break;
			}
			println("Bounding mode is set to '" + mode + "'");
		}
	}

	/**
	 * Swithces permission to use debug tools.
	 */
	public static void switchDebugPermission() {
		if (debugPermission == PERMISSION_HARD) {
			debugPermission = PERMISSION_SIMPLE;
		} else {
			debugPermission++;
		}
		String mode = "Error";
		switch(debugPermission) {
			case PERMISSION_NONE:
				mode = "None";
				break;
			case PERMISSION_SIMPLE:
				mode = "Simple";
				break;
			case PERMISSION_HARD:
				mode = "Hard";
				break;
		}
		println("Debug info is set to '"+ mode + "'");
	}

	/**
	 * Returns if user has permission for debug.
	 * 
	 * @return true if permission is granted<br>
	 *         false if permission is denied
	 */
	public static boolean hasDebugPermission() {
		return debugPermission != PERMISSION_NONE;
	}
	
	/**
	 * Returns if user has hard permission for debug.
	 * 
	 * @return true if hard permission is granted<br>
	 * 		   false if hard permission is deined
	 */
	public static boolean hasHardDebugPermission() {
		return debugPermission == PERMISSION_HARD;
	}
	
	public static void print(String text, int level) {
		for (int i = 0; i < level; i++) {
			print(LEVEL_SEPARATOR);
		}
		print(text);
	}
	
	public static void print(String text) {
		System.out.print(text);
	}
	
	public static void print(Object object) {
		System.out.print(object.toString());
	}
	
	public static void print(float value) {
		System.out.print(value);
	}
	
	public static void print(int value) {
		System.out.print(value);
	}
	
	public static void println() {
		System.out.println("");
	}
	
	/**
	 * Prints debug info in console.
	 * 
	 * @param text {@link String} value of debug info
	 */
	public static void println(String text) {
		System.out.println(text);
	}
	
	/**
	 * Prints debug info in console.
	 * 
	 * @param {@link Object} value of debug info
	 */
	public static void println(Object object) {
		System.out.println(object.toString());
	}
	
	/**
	 * Prints debug info in console.
	 * 
	 * @param value - float value of debug info
	 */
	public static void println(float value) {
		System.out.println(value);
	}
	
	/**
	 * Prints debug info in console.
	 * 
	 * @param value - int value of debug info
	 */
	public static void println(int value) {
		System.out.println(value);
	}
	
	/**
	 * Prints debug border in console for visual separating structure.
	 */
	public static void printBorder() {
		println("................");
	}
	
	/**
	 * Prints name with openning tags to show beginning of a debug structure in console .
	 *  
	 * @param text {@link String} value of structure name to print
	 */
	public static void printOpen(String text) {
		println("[+]" + text + "[+]");
	}
	
	/**
	 * Prints name with closing tags to show ending of a debug structure in console .
	 * 
	 * @param text {@link String} value of structure name to print
	 */
	public static void printClose(String text) {
		println("[-]"+ text + "[-]");
	}
	
	/**
	 * Prints debug info with separator before text.
	 *  
	 * @param text {@link String} value of debug info
	 * @param level int value of seprator level
	 */
	public static void println(String text, int level) {
		for (int i = 0; i < level; i++) {
			print(LEVEL_SEPARATOR);
		}
		println(text);
	}
	
	/**
	 * Prints debug info with separator before text.
	 *  
	 * @param value int value of debug info
	 * @param level int value of seprator level
	 */
	public static void println(int value, int level) {
		for (int i = 0; i < level; i++) {
			print(LEVEL_SEPARATOR);
		}
		println(value);
	}
	
	/**
	 * Prints debug info with separator before text.
	 *  
	 * @param value float value of debug info
	 * @param level int value of seprator level
	 */
	public static void println(float value, int level) {
		for (int i = 0; i < level; i++) {
			print(LEVEL_SEPARATOR);
		}
		println(value);
	}
	
	/**
	 * Prints debug error info in console.
	 * <br>Prints in red color.
	 *  
	 * @param text {@link String} value of debug info
	 */
	public static void printError(String text) {
		System.err.println(text);
	}
	
	/**
	 * Gets bounding visiblity status.
	 * 
	 * @return int value of bounding visibility status
	 */
	public static int getBoundingVisibility() {
		return boundingMode;
	}

}
