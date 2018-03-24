package shader.skybox;


import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.Camera;
import shader.Shader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector3f;

public class SkyboxShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_SKYBOX_PATH + "skybox_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_SKYBOX_PATH + "skybox_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	private static final String UNIFORM_PROJECTION_MATRIX = "Projection";
	private static final String UNIFORM_VIEW_MATRIX = "View";
	private static final String UNIFORM_FOG_COLOR = "fogColor";
	private static final String UNIFORM_BLEND_FACTOR = "blendFactor";
	private static final String UNIFORM_CUBE_MAP = "cubeMap";
	private static final String UNIFORM_CUBE_MAP2 = "cubeMap2";
	//----constants
	private static final float ROTATE_SPEED = 0.2f;

	private float rotation = 0;

	public SkyboxShader() {
		super(Shader.SKYBOX);
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

	public void loadViewMatrix(Camera camera) {
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

	public void loadFogColor(Color color) {
		super.loadColor(UNIFORM_FOG_COLOR, color);
	}

	public void loadBlendFactor(float blend) {
		super.loadFloat(UNIFORM_BLEND_FACTOR, blend);
	}

}