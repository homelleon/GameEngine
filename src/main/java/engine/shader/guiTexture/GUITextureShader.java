package shader.guiTexture;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class GUITextureShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTextureVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTextureFragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_mixColor;
	private int location_isMixColored;

	public GUITextureShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadMixColorVariables(boolean isMixColored, Vector3f color) {
		super.loadBoolean(location_isMixColored, isMixColored);
		super.loadVector(location_mixColor, color);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_isMixColored = super.getUniformLocation("isMixColored");
		location_mixColor = super.getUniformLocation("mixColor");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}