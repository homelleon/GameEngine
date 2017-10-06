package core.debug;

/**
 * Class to debug game engine.
 * 
 * @author homelleon
 * @version 1.1
 */
public class EngineDebug {

	private static enum Permission { NONE, SIMPLE, HARD };
	private static Permission debugPermission = Permission.SIMPLE;
	private static final String LEVEL_SEPARATOR = " ";

	/* bounding variables */
	public static final int BOUNDING_NONE = 0;
	public static final int BOUNDING_BOX = 1;
	public static final int BOUNDING_SPHERE = 2;
	public static final int BOUNDING_BOX_AND_SPHERE = 3;
	public static int boundingMode = BOUNDING_NONE;

	public static boolean debugInformation = false;

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
			String mode = "error";
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
			default:
				mode = "error";
				break;
			}
			System.out.println("Bounding mode is set to '" + mode + "'");
		}
	}

	/**
	 * Switches display of debug information.
	 */
	public static void switchDebugInformation() {
		if (hasDebugPermission()) {
			debugInformation = !debugInformation;
		}
	}

	/**
	 * Swithces permission to use debug tools.
	 */
	public static void switchDebugPermission() {
		switch(debugPermission) {
			case NONE:
				debugPermission = Permission.SIMPLE;
				break;
			case SIMPLE:
				debugPermission = Permission.HARD;
				break;
			case HARD:
				debugPermission = Permission.NONE;
				break;
		}
	}

	/**
	 * Returns if user has permission for debug.
	 * 
	 * @return true if permission is granted<br>
	 *         false if permission is denied
	 */
	public static boolean hasDebugPermission() {
		if (debugPermission != Permission.NONE) {
			return true;
		} else {
			if (debugInformation) {
				print("Debug mode is not permitted");
			}
			return false;
		}		
	}
	
	/**
	 * Prints debug info in console.
	 * 
	 * @param text {@link String} value of debug info
	 */
	public static void print(String text) {
		System.out.println(text);
	}
	
	/**
	 * Prints debug border in console for visual separating structure.
	 */
	public static void printBorder() {
		print("................");
	}
	
	/**
	 * Prints name with openning tags to show beginning of a debug structure in console .
	 *  
	 * @param text {@link String} value of structure name to print
	 */
	public static void printOpen(String text) {
		print("[+]" + text + "[+]");
	}
	
	/**
	 * Prints name with closing tags to show ending of a debug structure in console .
	 * 
	 * @param text {@link String} value of structure name to print
	 */
	public static void printClose(String text) {
		print("[-]"+ text + "[-]");
	}
	
	/**
	 * Prints debug info with separator before text.
	 *  
	 * @param text {@link String} value of debug info
	 * @param level int value of seprator level
	 */
	public static void print(String text, int level) {
		for(int i = 0; i < level; i++) {
			System.out.print(LEVEL_SEPARATOR);
		}
		print(text);
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

}
