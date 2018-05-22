package shader.postprocess.bloom;

import core.settings.EngineSettings;
import shader.Shader;

/*
 *  BrightFilterShader - шейдер фильтра €ркости.
 *  03.02.17
 * ------------------------------
*/

public class BrightFilterShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLOOM_PATH + "simple_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLOOM_PATH + "brightFilter_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	
	public BrightFilterShader() {
		super(Shader.POST_BRIGHT_FILTER);
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