package engineMain;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

import frames.Frame;
import frames.FrameEditor;
import scene.ES;

public class DisplayManager {
	
	private static long lastFrameTime;
	private static float delta;
	private static int height;
	private static int width;
	
	public static void creatDisplay() {
		
		
		ContextAttribs attribs = new ContextAttribs(3,3)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(ES.DISPLAY_WIDTH,
					ES.DISPLAY_HEIGHT));
			Display.create(new PixelFormat().withDepthBits(24), attribs);
			Display.isFullscreen();
			Display.setTitle("MyGame");
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, ES.DISPLAY_WIDTH, 
				ES.DISPLAY_HEIGHT);
		lastFrameTime = getCurrentTime();	
		
	}
	
public static void creatDisplay(int mode) {
		
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
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
		
		
	}
	
	public static void updateDisplay() {
		
		Display.sync(ES.FPS_CAP);
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
