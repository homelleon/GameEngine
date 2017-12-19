package control;

import org.lwjgl.input.Mouse;

import core.settings.EngineSettings;

public class MouseGame {

	public static final int LEFT_CLICK = 0;
	public static final int RIGHT_CLICK = 1;
	public static final int MIDDLE_CLICK = 2;
	public static final int WHEEL_UP = 3;
	public static final int WHEEL_DOWN = 4;
	public static final int MOVE_LEFT = 5;
	public static final int MOVE_RIGHT = 6;
	public static final int MOVE_UP = 7;
	public static final int MOVE_DOWN = 8;

	private static boolean[] isUsed = new boolean[8];
	private static int[] timer = new int[8];
	private static int echo;
	private static boolean isCoursorVisible = true;
	private static boolean isDebugged = false;

	public MouseGame(int echoValue) {
		for (int type = 0; type < 8; type++) {
			isUsed[type] = false;
			timer[type] = 0;
		}
		echo = echoValue;
	}

	public static void initilize(int echoLength) {
		for (int type = 0; type < 8; type++) {
			isUsed[type] = false;
			timer[type] = 0;
		}
		echo = echoLength;
	}

	public static void initilize(int echoLength, boolean isDebug) {
		for (int type = 0; type < 8; type++) {
			isUsed[type] = false;
			timer[type] = 0;
		}

		echo = echoLength;
		isDebugged = isDebug;
		if (isDebugged) {
			System.out.println("Mouse has been initilazed!");
		}
	}

	public static boolean isOncePressed(int type) {
		boolean isWorked = false;
		if (!isUsed[type] && Mouse.isButtonDown(type)) {
			isWorked = true;
			isUsed[type] = true;
		}
		return isWorked;
	}

	public static boolean isPressed(int type) {
		boolean isWorked = false;
		if (Mouse.isButtonDown(type)) {
			isWorked = true;
			if (isDebugged) {
				System.out.println("Mouse has been pressed!");
			}
		}
		return isWorked;
	}
	
	public static void centerCoursor() {
		Mouse.setCursorPosition(EngineSettings.DISPLAY_WIDTH / 2, EngineSettings.DISPLAY_HEIGHT / 2);
	}
	
	public static void switchCoursorVisibility() {
		if(isCoursorVisible) {
			isCoursorVisible = false;
			Mouse.setGrabbed(true);
		} else {
			isCoursorVisible = true;
			Mouse.setGrabbed(false);
		}
	}

	public static void update() {
		for (int type = 0; type < 8; type++) {
			if (isUsed[type]) {
				timer[type] += 1;
				if (timer[type] >= echo) {
					timer[type] = 0;
					isUsed[type] = false;
				}
			}
		}
	}

}
