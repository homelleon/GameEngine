package shader.skybox;


import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.ICamera;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class SkyboxShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_SKYBOX_PATH + "skybox_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_SKYBOX_PATH + "skybox_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "position";
	//----uniforms
	private static final String UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
	private static final String UNIFORM_VIEW_MATRIX = "viewMatrix";
	private static final String UNIFORM_FOG_COLOR = "fogColor";
	private static final String UNIFORM_BLEND_FACTOR = "blendFactor";
	private static final String UNIFORM_CUBE_MAP = "cubeMap";
	private static final String UNIFORM_CUBE_MAP2 = "cubeMap2";
	//----constants
	private static final float ROTATE_SPEED = 0.2f;

	private float rotation = 0;

	public SkyboxShader() {
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
		super.addUniform(UNIFORM_PROJECTION_MATRIX);
		super.addUniform(UNIFORM_VIEW_MATRIX);
		super.addUniform(UNIFORM_FOG_COLOR);
		super.addUniform(UNIFORM_BLEND_FACTOR);
		super.addUniform(UNIFORM_CUBE_MAP);
		super.addUniform(UNIFORM_CUBE_MAP2);
	}

	public void connectTextureUnits() {
		super.loadInt(UNIFORM_CUBE_MAP, 0);
		super.loadInt(UNIFORM_CUBE_MAP2, 1);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, matrix);
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m[3][0] = 0;
		matrix.m[3][1] = 0;
		matrix.m[3][2] = 0;
		if (!EngineMain.getIsEnginePaused()) {
			rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
		}
		matrix.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0));
		super.loadMatrix(UNIFORM_VIEW_MATRIX, matrix);
	}

	public void loadFogColour(float r, float g, float b) {
		super.loadVector(UNIFORM_FOG_COLOR, new Vector3f(r, g, b));
	}

	public void loadBlendFactor(float blend) {
		super.loadFloat(UNIFORM_BLEND_FACTOR, blend);
	}

}