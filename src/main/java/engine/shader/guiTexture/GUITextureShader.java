package shader.guiTexture;

import org.lwjgl.util.vector.Matrix4f;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class GUITextureShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTextureVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTextureFragmentShader.glsl";

	private int location_transformationMatrix;

	public GUITextureShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}