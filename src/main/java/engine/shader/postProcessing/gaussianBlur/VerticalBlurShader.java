package shader.postProcessing.gaussianBlur;

import core.settings.EngineSettings;
import shader.Shader;

public class VerticalBlurShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLUR_PATH + "verticalBlur_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLUR_PATH + "blur_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	private static final String UNIFORM_TARGET_HEIGHT = "targetHeight";
	
	public VerticalBlurShader() {
		super(Shader.POST_BLUR_V);
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
