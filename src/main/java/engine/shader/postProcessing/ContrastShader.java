package shader.postProcessing;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_POST_PROCESSING_PATH + "contrastVertex.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_POST_PROCESSING_PATH + "contrastFragment.glsl";

	public ContrastShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("contrast");
	}

	public void loadDisplayContrast(float value) {
		super.loadFloat("contrast", value);
	}

}
