package shader.postprocess;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import primitive.buffer.Loader;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import primitive.texture.Texture2D;
import shader.postprocess.bloom.BrightFilter;
import shader.postprocess.bloom.CombineFilter;
import shader.postprocess.gaussianBlur.BlurPass;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static Mesh quad;
	
	private static Fbo outputFbo;
	private static Fbo outputFbo2;
	private static ContrastChanger contrastChanger;
	private static BrightFilter brightFilter;
	private static BlurPass blur2;
	private static BlurPass blur4;
	private static BlurPass blur8;
	private static CombineFilter combineFilter;

	public static boolean isBlured = false;
	public static boolean isBloomed = false;

	public static void init() {
		quad = Loader.getInstance().getVertexLoader().loadToVAO(POSITIONS, 2);
		contrastChanger = new ContrastChanger();
		int width = Display.getWidth();
		int height = Display.getHeight();
		blur2 = new BlurPass(width / 2, height / 2);
		blur4 = new BlurPass(width / 4, height / 4);
		blur8 = new BlurPass(width / 8, height / 8);
		brightFilter = new BrightFilter(width / 2, height / 2);
		combineFilter = new CombineFilter();
		outputFbo = new Fbo(Fbo.DEPTH_TEXTURE).setSize(width, height).initialize();
		outputFbo2 = new Fbo(Fbo.DEPTH_TEXTURE).setSize(width, height).initialize();
	}

	public static void doPostProcessing(Fbo fbo) {
		fbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
		fbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
		Texture2D colourTexture = outputFbo.getColorTexture();
		Texture2D brightTexture = outputFbo2.getColorTexture();
		
		start();
		if (isBloomed && isBlured) {
			// brightFilter.render(colourTexture);
			blur2.render(brightTexture);
			blur4.render(blur2.getBlurredTexture());
			blur8.render(blur4.getBlurredTexture());
			combineFilter.render(colourTexture, blur2.getBlurredTexture(), blur4.getBlurredTexture(),
					blur8.getBlurredTexture());
		} else if (isBlured) {
			doBlur(colourTexture);
		} else if (isBloomed) {
			brightFilter.render(colourTexture);
			contrastChanger.render(brightFilter.getOutputTexture());
		} else {
			doContrast(colourTexture);
		}
		end();
	}

	private static void doBlur(Texture2D colourTexture) {
		blur2.render(colourTexture);
		blur4.render(blur2.getBlurredTexture());
		contrastChanger.render(blur4.getBlurredTexture());
	}

	private static void doContrast(Texture2D colourTexture) {
		contrastChanger.render(colourTexture);
	}

	public static void clean() {
		contrastChanger.clean();
		brightFilter.clean();
		blur2.clean();
		blur4.clean();
		blur8.clean();
		combineFilter.clean();
		outputFbo.clean();
		outputFbo2.clean();
	}

	private static void start() {
		VAO vao = quad.getVAO();
		vao.bind(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private static void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		VAO.unbind(0);
	}

}
