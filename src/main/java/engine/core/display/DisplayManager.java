package core.display;

import java.awt.Canvas;
import java.awt.Color;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

import core.settings.EngineSettings;
import object.input.KeyboardGame;
import tool.dataEditor.menu.DataEditorFrame;

/*
 *  Display Manager - Менеджер дисплея приложения
 *  01.02.17
 *  
 */

/**
 * Engine display manager.
 * 
 * @author homelleon
 *
 */
public class DisplayManager {

	private static long lastFrameTime; // Время прошло с запуска окна
	private static float delta; // текущее время окна
	private static int height; // высота окна
	private static int width; // ширина окна

	public static void createDisplay() {
		createDisplay(null);
	}

	/**
	 * Creates display in defined editor frame.
	 * 
	 * @param frame {@link DataEditorFrame} object to render display
	 */
	public static void createDisplay(DataEditorFrame frame) {
		
		if (frame != null) {
			// режим редактирования
			width = frame.getWidth() / 2;
			height = frame.getHeight() / 2;
			Canvas canvas = new Canvas();
			canvas.isDisplayable();
			canvas.setBackground(Color.green);
			canvas.setVisible(true);
			canvas.setSize(width, height);

			ContextAttribs attribs = new ContextAttribs(4, 3)
					.withForwardCompatible(true)
					.withProfileCore(true);

			try {
				Display.setDisplayMode(new DisplayMode(width, height));
				Display.create(new PixelFormat().withDepthBits(24), attribs);

				frame.getScreenPanel().add(canvas);

				Display.setParent(canvas);
				Display.setTitle("OutWorldMind Engine - EditMode");
			} catch (LWJGLException e) {
				e.printStackTrace();
			}

		} else if (frame == null) {
			// режим игры
			width = EngineSettings.DISPLAY_WIDTH;
			height = EngineSettings.DISPLAY_HEIGHT;
			ContextAttribs attribs = new ContextAttribs(4, 3).withForwardCompatible(true).withProfileCore(true);

			try {
				Display.setDisplayModeAndFullscreen(new DisplayMode(width, height));
				Display.create(new PixelFormat().withDepthBits(24), attribs);
				Display.setFullscreen(true);
				Display.setTitle("OutWorldMind Engine");

			} catch (LWJGLException e) {
				e.printStackTrace();
			}

		}
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
	}

	/**
	 * Updates display and keyboard.
	 */
	public static void updateDisplay() {
		Display.sync(EngineSettings.FPS_CAP);
		KeyboardGame.update();
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	/**
	 * Gets number of frames per second.
	 *  
	 * @return float value of FPS
	 */
	public static float getFrameTimeSeconds() {
		return delta;
	}

	/**
	 * Closes display.
	 */
	public static void closeDisplay() {
		Display.destroy();
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	/**
	 * Gets display height.
	 * 
	 * @return int value of display height
	 */
	public static int getHeight() {
		return height;
	}

	/**
	 * Gets display width.
	 * 
	 * @return int value of display width
	 */
	public static int getWidth() {
		return width;
	}

}
