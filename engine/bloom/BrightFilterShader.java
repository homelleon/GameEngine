package bloom;

import scene.ES;
import shaders.ShaderProgram;

/*
 *  BrightFilterShader - шейдер фильтра €ркости.
 *  03.02.17
 * ------------------------------
*/

public class BrightFilterShader extends ShaderProgram { 
	
	private static final String VERTEX_FILE = ES.BLOOM_SHADER_PATH + "simpleVertex.txt";
	private static final String FRAGMENT_FILE = ES.BLOOM_SHADER_PATH + "brightFilterFragment.txt";
	
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
