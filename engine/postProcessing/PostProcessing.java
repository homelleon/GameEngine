package postProcessing;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import bloom.BrightFilter;
import bloom.CombineFilter;
import gaussianBlur.HorizontalBlur;
import gaussianBlur.VerticalBlur;
import models.RawModel;
import renderEngine.Loader;

public class PostProcessing {
	
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static RawModel quad;
	private static ContrastChanger contrastChanger;
	private static BrightFilter brightFilter;
	private static HorizontalBlur hBlur2;
	private static VerticalBlur vBlur2;
	private static HorizontalBlur hBlur4;
	private static VerticalBlur vBlur4;
	private static HorizontalBlur hBlur8;
	private static VerticalBlur vBlur8;
	private static CombineFilter combineFilter;
	
	public static boolean isBlured = false;
	public static boolean isBloomed = false;

	public static void init(Loader loader) {
		quad = loader.loadToVAO(POSITIONS, 2);
		contrastChanger = new ContrastChanger();
		hBlur2 = new HorizontalBlur(Display.getWidth()/2, Display.getHeight()/2);
		vBlur2 = new VerticalBlur(Display.getWidth()/2, Display.getHeight()/2);
		hBlur4 = new HorizontalBlur(Display.getWidth()/4, Display.getHeight()/4);
		vBlur4 = new VerticalBlur(Display.getWidth()/4, Display.getHeight()/4);
		hBlur8 = new HorizontalBlur(Display.getWidth()/8, Display.getHeight()/8);
		vBlur8 = new VerticalBlur(Display.getWidth()/8, Display.getHeight()/8);
		brightFilter = new BrightFilter(Display.getWidth()/2, Display.getHeight()/2);
		combineFilter = new CombineFilter();		
	}
	
	public static void doPostProcessing(int colourTexture, int brightTexture) {
		start();
		if(isBloomed && isBlured) {
			//brightFilter.render(colourTexture);
			hBlur2.render(brightTexture);
			vBlur2.render(hBlur2.getOutputTexture());
			hBlur4.render(brightFilter.getOutputTexture());
			vBlur4.render(hBlur4.getOutputTexture());
			hBlur8.render(brightFilter.getOutputTexture());
			vBlur8.render(hBlur8.getOutputTexture());
			combineFilter.render(colourTexture, vBlur2.getOutputTexture(), vBlur4.getOutputTexture(), vBlur8.getOutputTexture());
		}else if(isBlured){
			doBlur(colourTexture);
		}else if(isBloomed){
			brightFilter.render(colourTexture);
			contrastChanger.render(brightFilter.getOutputTexture());
		}else{
			doContrast(colourTexture);
		}		
		end();
	}
	
	private static void doBlur(int colourTexture) {
		hBlur2.render(colourTexture);
		vBlur2.render(hBlur2.getOutputTexture());
		hBlur4.render(vBlur2.getOutputTexture());
		vBlur4.render(hBlur4.getOutputTexture());
		contrastChanger.render(vBlur4.getOutputTexture());	
	}
	
	private static void doContrast(int colourTexture) {
		contrastChanger.render(colourTexture);
	}
	
	public static void cleanUp() {
		contrastChanger.cleanUp();
		brightFilter.cleanUp();
		hBlur2.cleanUp();
		vBlur2.cleanUp();
		hBlur4.cleanUp();
		vBlur4.cleanUp();
		hBlur8.cleanUp();
		vBlur8.cleanUp();
		combineFilter.cleanUp();
	}
	
	private static void start() {
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}


}
