package core.debug;

/**
 * Class that helps to debug game engine.
 * 
 * @author homelleon
 * @version 1.0
 */
public class EngineDebug {

	private static boolean debugPermission = true;

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
		debugPermission = !debugPermission;
	}

	/**
	 * Returns if user has permission for debug.
	 * 
	 * @return true if permission is granted<br>
	 *         false if permission is denied
	 */
	public static boolean hasDebugPermission() {
		boolean isPermitted = false;
		if (debugPermission) {
			isPermitted = true;
		} else {
			if (debugInformation) {
				System.out.println("Debug mode is not permitted");
			}
		}
		return isPermitted;
	}

}
