package shader.guiTexture;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import shader.ShaderProgram;

public class GUITextureShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTextureVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_GUI_PATH + "guiTextureFragmentShader.glsl";

	public GUITextureShader() {
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
		super.addUniform("transformationMatrix");
		super.addUniform("isMixColored");
		super.addUniform("mixColor");
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix("transformationMatrix", matrix);
	}
	
	public void loadMixColorVariables(boolean isMixColored, Vector3f color) {
		super.loadBoolean("isMixColored", isMixColored);
		super.loadVector("mixColor", color);
	}

}