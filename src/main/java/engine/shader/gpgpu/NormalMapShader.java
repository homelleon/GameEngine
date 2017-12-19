package shader.gpgpu;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class NormalMapShader extends ShaderProgram {
	
	//---shaders
	private static final String COMPUTE_FILE = EngineSettings.SHADERS_GPGPU_PATH + "normalMap_C_shader.glsl";
	//---uniforms
	// parameters
	private static final String UNIFORM_N = "N";
	private static final String UNIFORM_STRENGTH = "strength";
	// materials
	private static final String UNIFORM_HEIGHT_MAP = "heightMap";
	
	public NormalMapShader() {
		super();
		addComputeShader(COMPUTE_FILE);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		addUniform(UNIFORM_N);
		addUniform(UNIFORM_STRENGTH);
		addUniform(UNIFORM_HEIGHT_MAP);
	}

	@Override
	protected void bindAttributes() {}
	
	public void connectTextureUnits() {
		super.loadInt(UNIFORM_HEIGHT_MAP, 0);
	}
	
	public void updateUniforms(int n, float strength) {
		super.loadInt(UNIFORM_N, n);
		super.loadFloat(UNIFORM_STRENGTH, strength);
	}

}
