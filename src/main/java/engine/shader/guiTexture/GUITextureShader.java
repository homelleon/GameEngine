package shader.guiTexture;


import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class GUITextureShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTexture_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTexture_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "position";
	//----uniforms
	private static final String UNIFORM_TRANSFORMATION_MATRIX = "transformationMatrix";
	private static final String UNIFORM_IS_MIX_COLORED = "isMixColored";
	private static final String UNIFORM_MIX_COLOR = "mixColor";
	
	public GUITextureShader() {
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
		super.addUniform(UNIFORM_TRANSFORMATION_MATRIX);
		super.addUniform(UNIFORM_IS_MIX_COLORED);
		super.addUniform(UNIFORM_MIX_COLOR);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}
	
	public void loadMixColorVariables(boolean isMixColored, Vector3f color) {
		super.loadBoolean(UNIFORM_IS_MIX_COLORED, isMixColored);
		super.loadVector(UNIFORM_MIX_COLOR, color);
	}

}