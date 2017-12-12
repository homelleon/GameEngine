package shader.postProcessing.bloom;

import core.settings.EngineSettings;
import shader.ShaderProgram;

/*
 *  BrightFilterShader - ������ ������� �������.
 *  03.02.17
 * ------------------------------
*/

public class BrightFilterShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLOOM_PATH + "simple_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLOOM_PATH + "brightFilter_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	
	public BrightFilterShader() {
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
	protected void loadUniformLocations() {}

}