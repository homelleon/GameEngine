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
import frame.Frame;
import frame.FrameEditor;
import object.input.KeyboardGame;

/*
 *  Display Manager - Менеджер дисплея приложения
 *  01.02.17
 *  
 */

public class DisplayManager {
	
	private static long lastFrameTime;  //Время прошло с запуска окна
	private static float delta; //текущее время окна
	private static int height; //высота окна
	private static int width;  //ширина окна
	
	public static void createDisplay() {	
		createDisplay(EngineSettings.DISPLAY_GAME_MODE);	
	}
	
	//конструктор с указанием режима приложения
	public static void createDisplay(int mode) {

		if (mode == EngineSettings.DISPLAY_EDIT_MODE) {				
			// режим редактирования
			Frame frame = new FrameEditor("Editor");
			width = frame.getWidth() / 2;
			height = frame.getHeight() / 2;
			Canvas canvas = new Canvas();
			canvas.isDisplayable();
			canvas.setBackground(Color.green);
			canvas.setVisible(true);
			canvas.setSize(width, height);
	
			ContextAttribs attribs = new ContextAttribs(3,3)
			.withForwardCompatible(true)
			.withProfileCore(true);
			
			try {
				Display.setDisplayMode(new DisplayMode(width, height));
				Display.create(new PixelFormat().withDepthBits(24), attribs);
	
				frame.getDisplayPanel().add(canvas);
			
				Display.setParent(canvas);
				Display.setTitle("EditMode");
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			
		} else if (mode == EngineSettings.DISPLAY_GAME_MODE) { 
			//режим игры
			width = EngineSettings.DISPLAY_WIDTH;
			height = EngineSettings.DISPLAY_HEIGHT;
			ContextAttribs attribs = new ContextAttribs(3,3)
			.withForwardCompatible(true)
			.withProfileCore(true);
			
			try {
				Display.setDisplayMode(new DisplayMode(width,
						height));
				Display.create(new PixelFormat().withDepthBits(24), attribs);
				Display.isFullscreen();
				Display.setTitle("MyGame");
	
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
					
		}		
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();		
	}
	
	public static void updateDisplay() {
		Display.sync(EngineSettings.FPS_CAP);
		KeyboardGame.update();
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;		
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {		
		Display.destroy();		
	}
	
	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getWidth() {
		return width;
	}	

}
