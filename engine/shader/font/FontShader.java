package shader.font;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_FONT_PATH + "fontVertex.txt";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_FONT_PATH + "fontFragment.txt";
	
	private int location_colour;
	private int location_translation;
	private int location_width;
	private int location_edge;
	private int location_borderWidth;
	private int location_borderEdge;
	private int location_offset;
	private int location_outlineColour;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
		location_translation = super.getUniformLocation("translation");
		location_width = super.getUniformLocation("width");
		location_edge = super.getUniformLocation("edge");
		location_borderWidth = super.getUniformLocation("borderWidth");
		location_borderEdge = super.getUniformLocation("borderEdge");
		location_offset = super.getUniformLocation("offset");
		location_outlineColour = super.getUniformLocation("outlineColour");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	public void loadWidthAndEdge(float width, float edge) {
		super.loadFloat(location_width, width);
		super.loadFloat(location_edge, edge);
	}
	
	public void loadBorderWidthAndEdge(float width, float edge) {
		super.loadFloat(location_borderWidth, width);
		super.loadFloat(location_borderEdge, edge);
	}
	
	public void loadOffset(Vector2f offset) {
		super.load2DVector(location_offset, offset);
	}
	
	public void loadOutLineColour(Vector3f colour) {
		super.loadVector(location_outlineColour, colour);
	}
	
	public void loadColour(Vector3f colour) {
		super.loadVector(location_colour, colour);
	}
	
	public void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}


}
