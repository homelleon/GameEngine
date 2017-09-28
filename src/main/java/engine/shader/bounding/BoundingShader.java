package shader.bounding;


import core.settings.EngineSettings;
import object.camera.ICamera;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;

public class BoundingShader extends ShaderProgram {

	public static final String VERTEX_FILE = EngineSettings.SHADERS_BOUNDING_PATH + "boundVertexShader.glsl";
	public static final String FRAGMENT_FILE = EngineSettings.SHADERS_BOUNDING_PATH + "boundFragmentShader.glsl";

	public BoundingShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("transformationMatrix");
		super.addUniform("projectionMatrix");
		super.addUniform("viewMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix("transformationMatrix", matrix);
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix("viewMatrix", viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix("projectionMatrix", projection);
	}

}
