package shader.gpgpu;

import core.settings.EngineSettings;
import shader.Shader;

public class HeightMapShader extends Shader {
	
	//----shaders
	private static final String COMPUTE_SHADER = EngineSettings.SHADERS_GPGPU_PATH + "heightMap_C_shader.glsl";
	//----uniforms
	private static final String UNIFORM_SIZE = "size";
	public static final String UNIFORM_POSITION_MAP = "positionMap";
	
	public HeightMapShader() {
		super(Shader.GPGPU_HEIGHT_MAP);
		addComputeShader(COMPUTE_SHADER);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		addUniform(UNIFORM_SIZE);
	}

	@Override
	protected void bindAttributes() {}
	
	public void loadMapSize(int size) {
		loadInt(UNIFORM_SIZE, size);
	}
	
	public void connectTextureUnits() {
		super.loadInt(UNIFORM_POSITION_MAP, 1);
	}	

}
