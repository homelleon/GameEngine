package inputs;

import org.lwjgl.input.Keyboard;

public class KeyboardGame {
	
	public static boolean isKeyPressed(int key) {
		boolean isPressed = false;
		while(Keyboard.next()) {
			if(Keyboard.getEventKey() == key) {
				if(Keyboard.getEventKeyState()) {
					isPressed = true;
				}
			}
		}
		return isPressed;
	}
	
	public static boolean isKeyReleased(int key) {
		boolean isReleased = false;
		while(Keyboard.next()) {
			if(Keyboard.getEventKey() == key) {
				if(!Keyboard.getEventKeyState()) {
					isReleased = true;
				}
			}
		}
		return isReleased;
	}
	

}
