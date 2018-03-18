package shader.postProcessing;

import core.settings.EngineSettings;
import shader.Shader;

public class ContrastShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_POST_PROCESSING_PATH + "contrast_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_POST_PROCESSING_PATH + "contrast_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	private static final String UNIFORM_CONTRAST = "contrast";
	
	public ContrastShader() {
		super(Shader.POST_CONTRAST);
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
		super.addUniform(UNIFORM_CONTRAST);
	}

	public void loadDisplayContrast(float value) {
		super.loadFloat(UNIFORM_CONTRAST, value);
	}

}
