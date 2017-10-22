package shader.gpgpu;

import shader.ShaderProgram;

public class HeightMapShader extends ShaderProgram {
	
	//----shaders
	private static final String COMPUTE_SHADER = "heighMap_C_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "position";
	//----uniforms
	private static final String UNIFORM_SIZE = "size";
	
	public HeightMapShader() {
		super();
		addComputeShader(COMPUTE_SHADER);
		compileShader();
	}

	@Override
	protected void loadUniformLocations() {
		addUniform(UNIFORM_SIZE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, ATTRIBUTE_POSITION);
	}
	
	public void loadMapSize(int size) {
		loadInt(UNIFORM_SIZE, size);
	}

}
