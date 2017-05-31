package shaders.postProcessing.gaussianBlur;

import core.settings.ES;
import shaders.ShaderProgram;

public class VerticalBlurShader extends ShaderProgram {

	private static final String VERTEX_FILE = ES.BLUR_SHADER_PATH + "verticalBlurVertex.txt";
	private static final String FRAGMENT_FILE = ES.BLUR_SHADER_PATH + "blurFragment.txt";
	
	private int location_targetHeight;
	
	protected VerticalBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	protected void loadTargetHeight(float height) {
		super.loadFloat(location_targetHeight, height);
	}

	@Override
	protected void getAllUniformLocations() {	
		location_targetHeight = super.getUniformLocation("targetHeight");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
