package shader.shadow;

import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Vec2f;

public class ShadowShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_SHADOW_PATH + "shadowVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_SHADOW_PATH + "shadowFragmentShader.glsl";

	public ShadowShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("mvpMatrix");
		super.addUniform("offset");
		super.addUniform("numberOfRows");

	}

	public void loadMvpMatrix(Matrix4f mvpMatrix) {
		super.loadMatrix("mvpMatrix", mvpMatrix);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat("numberOfRows", numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector("offset", new Vec2f(x, y));
	}

}
