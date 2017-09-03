package shader.postProcessing.bloom;

import core.settings.EngineSettings;
import shader.ShaderProgram;

/*
 *  BrightFilterShader - шейдер фильтра €ркости.
 *  03.02.17
 * ------------------------------
*/

public class BrightFilterShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLOOM_PATH + "simpleVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLOOM_PATH + "brightFilterFragment.glsl";

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
