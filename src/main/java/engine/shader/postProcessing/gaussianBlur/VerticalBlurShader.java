package shader.postProcessing.gaussianBlur;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class VerticalBlurShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLUR_PATH + "verticalBlur_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLUR_PATH + "blur_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "position";
	//----uniforms
	private static final String UNIFORM_TARGET_HEIGHT = "targetHeight";
	
	protected VerticalBlurShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, ATTRIBUTE_POSITION);
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform(UNIFORM_TARGET_HEIGHT);
	}

	protected void loadTargetHeight(float height) {
		super.loadFloat(UNIFORM_TARGET_HEIGHT, height);
	}
}
