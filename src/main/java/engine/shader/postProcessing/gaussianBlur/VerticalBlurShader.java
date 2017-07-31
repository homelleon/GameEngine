package shader.postProcessing.gaussianBlur;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class VerticalBlurShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLUR_PATH + "verticalBlurVertex.txt";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLUR_PATH + "blurFragment.txt";

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
