package shader.gpgpu;

import core.settings.EngineSettings;
import shader.Shader;

public class NormalMapShader extends Shader {
	
	//---shaders
	private static final String COMPUTE_FILE = EngineSettings.SHADERS_GPGPU_PATH + "normalMap_C_shader.glsl";
	//---uniforms
	// parameters
	private static final String UNIFORM_SIZE = "size";
	private static final String UNIFORM_STRENGTH = "strength";
	// materials
	private static final String UNIFORM_HEIGHT_MAP = "heightMap";
	
	public NormalMapShader() {
		super(Shader.GPGPU_NORMAL_MAP);
		addComputeShader(COMPUTE_FILE);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		addUniform(UNIFORM_SIZE);
		addUniform(UNIFORM_STRENGTH);
		addUniform(UNIFORM_HEIGHT_MAP);
	}

	@Override
	protected void bindAttributes() {}
	
	public void connectTextureUnits() {
		super.loadInt(UNIFORM_HEIGHT_MAP, 0);
	}
	
	public void updateUniforms(int size, float strength) {
		super.loadInt(UNIFORM_SIZE, size);
		super.loadFloat(UNIFORM_STRENGTH, strength);
	}

}
