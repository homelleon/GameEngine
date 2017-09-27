package shader.particle;


import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.VMatrix4f;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_PARTICLE_PATH + "particleVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_PARTICLE_PATH + "particleFragmentShader.glsl";

	public ParticleShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("numberOfRows");
		super.addUniform("projectionMatrix");
	}

	public void loadNumberOfRows(float numberOfRows) {
		super.loadFloat("numberOfRows", numberOfRows);
	}

	public void loadProjectionMatrix(VMatrix4f projectionMatrix) {
		super.loadMatrix("projectionMatrix", projectionMatrix);
	}

}
