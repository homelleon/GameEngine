package shader.debug;

import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;

public class DebugShader extends ShaderProgram {
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_DEBUG + "debug_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_DEBUG + "debug_F_shader.glsl";
	//----atributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_color";
	private static final String ATTRIBUTE_POSITION = "position";
	private static final String ATTRIBUTE_ATTR1 = "attribute1";
	private static final String ATTRIBUTE_ATTR2 = "attribute2";
	//----uniforms
	private static final String UNIFORM_UN0 = "uniform0";
	private static final String UNIFORM_UN1 = "uniform1";
	
	public DebugShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}
	
	@Override
	protected void loadUniformLocations() {
//		addUniform(UNIFORM_UN0);
//		addUniform(UNIFORM_UN1);
	}

	@Override
	protected void bindAttributes() {
		bindFragOutput(0, ATTRIBUTE_OUT_COLOR);
		bindAttribute(0, ATTRIBUTE_POSITION);
		bindAttribute(1, ATTRIBUTE_ATTR1);
	}
	
	public void connectTextureUnit0() {
		super.loadInt(UNIFORM_UN0, 0);
	}
	
	public void connectTextureUnit1() {
		super.loadInt(UNIFORM_UN1, 1);
	}
	
	public void loadUniform0(float value) {
		loadFloat(UNIFORM_UN0, value);
	}
	
	public void loadUniform1(float value) {
		loadFloat(UNIFORM_UN1, value);
	}

}
