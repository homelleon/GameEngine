package shader.guiTexture;


import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Vec3f;

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
	
	public void loadMixColorVariables(boolean isMixColored, Vec3f color) {
		super.loadBoolean("isMixColored", isMixColored);
		super.loadVector("mixColor", color);
	}

}