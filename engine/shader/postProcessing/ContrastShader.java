package shader.postProcessing;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_POST_PROCESSING_PATH + "contrastVertex.txt";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_POST_PROCESSING_PATH + "contrastFragment.txt";

	private int location_contrast;

	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_contrast = super.getUniformLocation("contrast");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadDisplayContrast(float value) {
		super.loadFloat(location_contrast, value);
	}

}
