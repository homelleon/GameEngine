package shader.postProcessing.bloom;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class CombineShader extends ShaderProgram {

	/*
	 * CombineShader - шейдер для объединения других фильтров постобработки
	 * 03.02.17 ------------------------------
	 */

	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLOOM_PATH + "simpleVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLOOM_PATH + "combineFragmentShader.glsl";

	protected CombineShader() {
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
		super.addUniform("colourTexture");
		super.addUniform("highlightTexture2");
		super.addUniform("highlightTexture4");
		super.addUniform("highlightTexture8");
	}

	protected void connectTextureUnits() {
		super.loadInt("colourTexture", 0);
		super.loadInt("highlightTexture2", 1);
		super.loadInt("highlightTexture4", 2);
		super.loadInt("highlightTexture8", 3);
	}

}
