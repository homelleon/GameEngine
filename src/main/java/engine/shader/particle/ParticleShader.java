package shader.particle;


import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;

public class ParticleShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_PARTICLE_PATH + "particle_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_PARTICLE_PATH + "particle_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	private static final String ATTRIBUTE_MODEL_VIEW_MATRIX = "in_ModelView";
	private static final String ATTRIBUTE_TEX_OFFSETS = "in_texOffsets";
	private static final String ATTRIBUTE_BLEND_FACTOR = "in_blendFactor";
	//----uniforms
	private static final String UNIFORM_PROJECTION_MATRIX = "Projection";
	private static final String UNIFORM_NUMBER_OF_ROWS = "numberOfRows";
	
	public ParticleShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, ATTRIBUTE_POSITION);
		super.bindAttribute(1, ATTRIBUTE_MODEL_VIEW_MATRIX);
		super.bindAttribute(5, ATTRIBUTE_TEX_OFFSETS);
		super.bindAttribute(6, ATTRIBUTE_BLEND_FACTOR);
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform(UNIFORM_NUMBER_OF_ROWS);
		super.addUniform(UNIFORM_PROJECTION_MATRIX);
	}

	public void loadNumberOfRows(float numberOfRows) {
		super.loadFloat(UNIFORM_NUMBER_OF_ROWS, numberOfRows);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, projectionMatrix);
	}

}
