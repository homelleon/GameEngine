package shader.postProcessing.bloom;

import core.settings.EngineSettings;
import shader.Shader;

public class CombineShader extends Shader {

	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLOOM_PATH + "simple_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLOOM_PATH + "combine_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	private static final String UNIFORM_COLOR_MAP = "colorMap";
	private static final String UNIFORM_HIGHLIGHT_TEXTURE2 = "highlightMap2";
	private static final String UNIFORM_HIGHLIGHT_TEXTURE4 = "highlightMap4";
	private static final String UNIFORM_HIGHLIGHT_TEXTURE8 = "highlightMap8";
	
	public CombineShader() {
		super(Shader.POST_COMBINE);
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, ATTRIBUTE_POSITION);
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform(UNIFORM_COLOR_MAP);
		super.addUniform(UNIFORM_HIGHLIGHT_TEXTURE2);
		super.addUniform(UNIFORM_HIGHLIGHT_TEXTURE4);
		super.addUniform(UNIFORM_HIGHLIGHT_TEXTURE8);
	}

	protected void connectTextureUnits() {
		super.loadInt(UNIFORM_COLOR_MAP, 0);
		super.loadInt(UNIFORM_HIGHLIGHT_TEXTURE2, 1);
		super.loadInt(UNIFORM_HIGHLIGHT_TEXTURE4, 2);
		super.loadInt(UNIFORM_HIGHLIGHT_TEXTURE8, 3);
	}

}
