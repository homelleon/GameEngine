package core.debug;

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
		for(int i = 0; i < level; i++) {
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
		for(int i = 0; i < level; i++) {
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
		for(int i = 0; i < level; i++) {
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
		for(int i = 0; i < level; i++) {
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
