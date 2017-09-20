package shader.font;

import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_FONT_PATH + "fontVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_FONT_PATH + "fontFragmentShader.glsl";

	public FontShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("colour");
		super.addUniform("translation");
		super.addUniform("width");
		super.addUniform("edge");
		super.addUniform("borderWidth");
		super.addUniform("borderEdge");
		super.addUniform("offset");
		super.addUniform("outlineColour");
	}

	public void loadWidthAndEdge(float width, float edge) {
		super.loadFloat("width", width);
		super.loadFloat("edge", edge);
	}

	public void loadBorderWidthAndEdge(float width, float edge) {
		super.loadFloat("borderWidth", width);
		super.loadFloat("borderEdge", edge);
	}

	public void loadOffset(Vec2f offset) {
		super.load2DVector("offset", offset);
	}

	public void loadOutLineColour(Vec3f colour) {
		super.loadVector("outlineColour", colour);
	}

	public void loadColour(Vec3f colour) {
		super.loadVector("colour", colour);
	}

	public void loadTranslation(Vec2f translation) {
		super.load2DVector("translation", translation);
	}

}
