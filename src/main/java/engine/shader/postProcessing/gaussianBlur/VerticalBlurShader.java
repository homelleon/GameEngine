package shader.postProcessing.gaussianBlur;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class VerticalBlurShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLUR_PATH + "verticalBlurVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLUR_PATH + "blurFragmentShader.glsl";

	protected VerticalBlurShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("targetHeight");
	}

	protected void loadTargetHeight(float height) {
		super.loadFloat("targetHeight", height);
	}
}
