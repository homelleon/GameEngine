package shader.shadow;

import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;

public class ShadowShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_SHADOW_PATH + "shadowVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_SHADOW_PATH + "shadowFragmentShader.glsl";

	private static final String ATTRIBUTE_POSITION = "in_position";
	private static final String ATTRIBUTE_TEXTURE_COORDINATES = "in_textureCoords";
	
	private static final String UNIFORM_MVP_MATRIX = "mvpMatrix";
	private static final String UNIFORM_OFFSET = "offset";
	private static final String UNIFORM_NUMBER_OF_ROWS = "numberOfRows";
	
	public ShadowShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, ATTRIBUTE_POSITION);
		super.bindAttribute(1, ATTRIBUTE_TEXTURE_COORDINATES);
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform(UNIFORM_MVP_MATRIX);
		super.addUniform(UNIFORM_OFFSET);
		super.addUniform(UNIFORM_NUMBER_OF_ROWS);

	}

	public void loadMvpMatrix(Matrix4f mvpMatrix) {
		super.loadMatrix(UNIFORM_MVP_MATRIX, mvpMatrix);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(UNIFORM_NUMBER_OF_ROWS, numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector(UNIFORM_OFFSET, new Vector2f(x, y));
	}

}
