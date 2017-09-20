package shader.postProcessing.gaussianBlur;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class HorizontalBlurShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLUR_PATH + "horizontalBlurVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLUR_PATH + "blurFragmentShader.glsl";

	protected HorizontalBlurShader() {
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
		super.addUniform("targetWidth");
	}

	protected void loadTargetWidth(float width) {
		super.loadFloat("targetWidth", width);
	}

}
