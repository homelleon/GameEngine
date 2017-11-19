package shader.gpgpu;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class HeightPositionShader extends ShaderProgram {
	
	//----shaders
	private static final String COMPUTE_SHADER = EngineSettings.SHADERS_GPGPU_PATH + "heightPosition_C_shader.glsl";
	//----attributes
	public static final String ATTRIBUTE_SSBO = "positionBuffer";
	//----uniforms
	public static final String UNIFORM_HEIGHT_MAP = "heightMap";
	
	public HeightPositionShader() {
		super();
		addComputeShader(COMPUTE_SHADER);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		addUniform(UNIFORM_HEIGHT_MAP);
		//addSSBO(ATTRIBUTE_SSBO);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, ATTRIBUTE_SSBO);
		//super.bindSSBO(0, ATTRIBUTE_SSBO);
	}
	
	public void connectTextureUnits() {
		super.loadInt(UNIFORM_HEIGHT_MAP, 1);
	}

}
