package shader.water;


import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.Light;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;

public class WaterShader extends ShaderProgram {

	//----shaders
	private final static String VERTEX_FILE = EngineSettings.SHADERS_WATER_PATH + "water_V_shader.glsl";
	private final static String FRAGMENT_FILE = EngineSettings.SHADERS_WATER_PATH + "water_F_shader.glsl";
	//----attributes
	private final static String ATTRIBUTE_OUT_COLOR = "out_Color";
	private final static String ATTRIBUTE_OUT_BRIGHT_COLOR = "out_BrightColor";
	private final static String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	//matrix
	private final static String UNIFORM_PROJECTION_MATRIX = "Projection";
	private final static String UNIFORM_VIEW_MATRIX = "View";
	private final static String UNIFORM_MODEL_MATRIX = "Model";
	private final static String UNIFORM_CAMERA_POSITION = "cameraPosition";	
	//material
	private final static String UNIFORM_NORMAL_MAP = "normalMap";
	private final static String UNIFORM_DUDV_MAP = "dudvMap";
	private final static String UNIFORM_DEPTH_MAP = "depthMap";
	private final static String UNIFORM_REFLECTION_TEXTURE = "reflectionTexture";
	private final static String UNIFORM_REFRACTION_TEXTURE = "refractionTexture";		
	//ambient variables
	private final static String UNIFORM_SKY_COLOR = "skyColor";
	private final static String UNIFORM_FOG_DENSITY = "fogDensity";
	//texture coords variables
	private final static String UNIFORM_TILING = "tiling";
	//wave varibales
	private final static String UNIFORM_WAVE_STRENGTH = "waveStrength";
	private final static String UNIFORM_MOVE_FACTOR = "moveFactor";	
	private final static String UNIFORM_LIGHT_COLOR = "lightColor";
	//light
	private final static String UNIFORM_LIGHT_POSITION = "lightPosition";
	
	public WaterShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, ATTRIBUTE_OUT_COLOR);
		super.bindFragOutput(1, ATTRIBUTE_OUT_BRIGHT_COLOR);
		bindAttribute(0, ATTRIBUTE_POSITION);
	}

	@Override
	protected void loadUniformLocations() {
		//matrix
		addUniform(UNIFORM_PROJECTION_MATRIX);
		addUniform(UNIFORM_VIEW_MATRIX);
		addUniform(UNIFORM_MODEL_MATRIX);
		addUniform(UNIFORM_CAMERA_POSITION);
		//material
		addUniform(UNIFORM_NORMAL_MAP);
		addUniform(UNIFORM_DUDV_MAP);
		addUniform(UNIFORM_DEPTH_MAP);
		addUniform(UNIFORM_REFLECTION_TEXTURE);
		addUniform(UNIFORM_REFRACTION_TEXTURE);	
		//ambient variables
		addUniform(UNIFORM_SKY_COLOR);
		addUniform(UNIFORM_FOG_DENSITY);
		//texture coords variables
		addUniform(UNIFORM_TILING);
		//wave varibales
		addUniform(UNIFORM_WAVE_STRENGTH);
		addUniform(UNIFORM_MOVE_FACTOR);		
		addUniform(UNIFORM_LIGHT_COLOR);
		//light
		addUniform(UNIFORM_LIGHT_POSITION);
	}

	public void connectTextureUnits() {
		super.loadInt(UNIFORM_REFLECTION_TEXTURE, 0);
		super.loadInt(UNIFORM_REFRACTION_TEXTURE, 1);
		super.loadInt(UNIFORM_DUDV_MAP, 2);
		super.loadInt(UNIFORM_NORMAL_MAP, 3);
		super.loadInt(UNIFORM_DEPTH_MAP, 4);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, projection);
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
		super.load3DVector(UNIFORM_CAMERA_POSITION, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix) {
		super.loadMatrix(UNIFORM_MODEL_MATRIX, modelMatrix);
	}

	public void loadLight(Light sun) {
		super.loadColor(UNIFORM_LIGHT_COLOR, sun.getColor());
		super.load3DVector(UNIFORM_LIGHT_POSITION, sun.getPosition());
	}

	public void loadMoveFactor(float factor) {
		super.loadFloat(UNIFORM_MOVE_FACTOR, factor);
	}

	public void loadTilingSize(float size) {
		super.loadFloat(UNIFORM_TILING, size);
	}

	public void loadWaveStrength(float strength) {
		super.loadFloat(UNIFORM_WAVE_STRENGTH, strength);
	}

	public void loadSkyColor(Color color) {
		super.loadColor(UNIFORM_SKY_COLOR, color);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(UNIFORM_FOG_DENSITY, density);
	}
}