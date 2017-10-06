package core.debug;

/**
 * Class to debug game engine.
 * 
 * @author homelleon
 * @version 1.1
 */
public class EngineDebug {

	private static final String LEVEL_SEPARATOR = "  |";
	
	/* permission variables */
	public static final int PERMISSION_NONE = 0;
	public static final int PERMISSION_SIMPLE = 1;
	public static final int PERMISSION_HARD = 2;
	private static int debugPermission = PERMISSION_SIMPLE;


	/* bounding variables */
	public static final int BOUNDING_NONE = 0;
	public static final int BOUNDING_BOX = 1;
	public static final int BOUNDING_SPHERE = 2;
	public static final int BOUNDING_BOX_AND_SPHERE = 3;
	private static int boundingMode = BOUNDING_NONE;

	public static int getBoundingMode() {
		return boundingMode;
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
			print("Bounding mode is set to '" + mode + "'");
		}
	}

	/**
	 * Swithces permission to use debug tools.
	 */
	public static void switchDebugPermission() {
		if (debugPermission == PERMISSION_HARD) {
			debugPermission = PERMISSION_NONE;
		} else {
			debugPermission++;
		}
		String mode = "Error";
		switch(debugPermission) {
			case PERMISSION_NONE:
				mode = "Simple";
				break;
			case PERMISSION_SIMPLE:
				mode = "Hard";
				break;
			case PERMISSION_HARD:
				mode = "None";
				break;
		}
		print("Debug info is set to '"+ mode + "'");
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
