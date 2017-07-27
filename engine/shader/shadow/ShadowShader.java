package shader.shadow;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class ShadowShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_SHADOW_PATH + "shadowVertexShader.txt";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_SHADOW_PATH + "shadowFragmentShader.txt";

	private int location_mvpMatrix;
	private int location_offset;
	private int location_numberOfRows;

	public ShadowShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_mvpMatrix = super.getUniformLocation("mvpMatrix");
		location_offset = super.getUniformLocation("offset");
		location_numberOfRows = super.getUniformLocation("numberOfRows");

	}

	public void loadMvpMatrix(Matrix4f mvpMatrix) {
		super.loadMatrix(location_mvpMatrix, mvpMatrix);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x, y));
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
	}

}
