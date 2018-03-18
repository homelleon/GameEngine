package shader.font;

import core.settings.EngineSettings;
import shader.Shader;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;

public class FontShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_FONT_PATH + "font_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_FONT_PATH + "font_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	private static final String ATTRIBUTE_TEXTURE_COORINATES = "in_textureCoords";
	//----uniforms
	private static final String UNIFORM_COLOR = "color";
	private static final String UNIFORM_TRANSLATION = "translation";
	private static final String UNIFORM_WIDTH = "width";
	private static final String UNIFORM_EDGE = "edge";
	private static final String UNIFORM_BORDER_WIDTH = "borderWidth";
	private static final String UNIFORM_BORDER_EDGE = "borderEdge";
	private static final String UNIFORM_OFFSET = "offset";
	private static final String UNIFORM_OUTLINE_COLOR = "outlineColor";
	
	public FontShader() {
		super(Shader.FONT);
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, ATTRIBUTE_POSITION);
		super.bindAttribute(1, ATTRIBUTE_TEXTURE_COORINATES);
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform(UNIFORM_COLOR);
		super.addUniform(UNIFORM_TRANSLATION);
		super.addUniform(UNIFORM_WIDTH);
		super.addUniform(UNIFORM_EDGE);
		super.addUniform(UNIFORM_BORDER_WIDTH);
		super.addUniform(UNIFORM_BORDER_EDGE);
		super.addUniform(UNIFORM_OFFSET);
		super.addUniform(UNIFORM_OUTLINE_COLOR);
	}

	public void loadTranslation(Vector2f translation) {
		super.load2DVector(UNIFORM_TRANSLATION, translation);
	}

	public void loadWidthAndEdge(float width, float edge) {
		super.loadFloat(UNIFORM_WIDTH, width);
		super.loadFloat(UNIFORM_EDGE, edge);
	}

	public void loadBorderWidthAndEdge(float width, float edge) {
		super.loadFloat(UNIFORM_BORDER_WIDTH, width);
		super.loadFloat(UNIFORM_BORDER_EDGE, edge);
	}

	public void loadOffset(Vector2f offset) {
		super.load2DVector(UNIFORM_OFFSET, offset);
	}

	public void loadOutLineColor(Color color) {
		super.loadColor(UNIFORM_OUTLINE_COLOR, color);
	}

	public void loadColor(Color color) {
		super.loadColor(UNIFORM_COLOR, color);
	}
}