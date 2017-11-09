package input;

import org.lwjgl.input.Keyboard;

/**
 * Class of Keyboard control that can check if key was pressed once or released.
 * uses LWJGL class as basic.
 * 
 * @author homelleon
 * @version 1.0
 */

public class KeyboardGame {

	private static final int NOTHING = 0;
	private static final int PRESSED = 1;
	private static final int RELEASED = 2;

	private static int[] keys = new int[Keyboard.KEYBOARD_SIZE];

	/**
	 * Method that clear keys massive to set it as not pressed and not released.
	 * After that it checks if there was any key event and set event key as
	 * 'pressed' or 'released'.
	 * <p>
	 * <i>note: </i>use that method before LWJGL Display.update() method.
	 * 
	 * @see #isKeyPressed(int)
	 * @see #isKeyReleased(int)
	 */
	public static void update() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = NOTHING;
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				keys[Keyboard.getEventKey()] = PRESSED;
			} else {
				keys[Keyboard.getEventKey()] = RELEASED;
			}
		}
	}

	/**
	 * Method that checks if chosen key was pressed until the next keyboard
	 * {@link #update()}.
	 * 
	 * @param key
	 *            int value of key to check for (use LWJGL Keyboard constants
	 *            for that value)
	 * @return true if key was pressed until next display update</br>
	 *         false if key wasn't pressed until next display update
	 * @see #isKeyReleased(int)
	 * @see #update()
	 */
	public static boolean isKeyPressed(int key) {
		boolean isPressed = false;
		if (keys[key] == PRESSED) {
			isPressed = true;
		}
		return isPressed;
	}

	/**
	 * Method that checks if chosen key was released until the next keyboard
	 * {@link #update()}.
	 * 
	 * @param key
	 *            int value of key to check for (use LWJGL Keyboard constants
	 *            for that value)
	 * @return true if key was released until next display update</br>
	 *         false if key wasn't released until next display update
	 * @see #isKeyPressed(int)
	 * @see #update()
	 */
	public static boolean isKeyReleased(int key) {
		boolean isReleased = false;
		if (keys[key] == RELEASED) {
			isReleased = true;
		}
		return isReleased;
	}

	/**
	 * Method that repeat LWJGL method isKeyDown(int key) and check if key is
	 * pressed down.
	 * <p>
	 * Unlike {@link #isKeyPressed(int)} return true if key is holding down.
	 * 
	 * @param key
	 *            int value of key to check for (use LWJGL Keyboard constants
	 *            for that value)
	 * @return true if key was pressed or holding down
	 * 
	 * @see #isKeyPressed(int)
	 * 
	 */
	public static boolean isKeyDown(int key) {
		boolean isDown = false;
		if (Keyboard.isKeyDown(key)) {
			isDown = true;
		}
		return isDown;
	}

}
