package engine.bloom;

import engine.scene.Settings;
import engine.shaders.ShaderProgram;

public class BrightFilterShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = Settings.BLOOM_SHADER_PATH + "simpleVertex.txt";
	private static final String FRAGMENT_FILE = Settings.BLOOM_SHADER_PATH + "brightFilterFragment.txt";
	
	public BrightFilterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
