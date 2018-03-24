package shader.bounding;


import core.settings.EngineSettings;
import object.camera.Camera;
import shader.Shader;
import tool.math.Maths;
import tool.math.Matrix4f;

public class BoundingShader extends Shader {
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BOUNDING_PATH + "bound_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BOUNDING_PATH + "bound_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	private static final String ATTRIBUTE_POSITION = "in_position";
	private static final String ATTRIBUTE_TEXTURE_COORDINATES = "in_textureCoords";
	private static final String ATTRIBUTE_NORMAL = "in_normal";
	//----uniforms
	private static final String UNIFORM_TRANSFORMATION_MATRIX = "Transformation";
	private static final String UNIFORM_PROJECTION_MATRIX = "Projection";
	private static final String UNIFORM_VIEW_MATRIX = "View";
	
	public BoundingShader() {
		super(Shader.BOUNDING);
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform(UNIFORM_TRANSFORMATION_MATRIX);
		super.addUniform(UNIFORM_PROJECTION_MATRIX);
		super.addUniform(UNIFORM_VIEW_MATRIX);
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, ATTRIBUTE_OUT_COLOR);
		super.bindAttribute(0, ATTRIBUTE_POSITION);
		super.bindAttribute(1, ATTRIBUTE_TEXTURE_COORDINATES);
		super.bindAttribute(2, ATTRIBUTE_NORMAL);
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, projection);
	}

}
