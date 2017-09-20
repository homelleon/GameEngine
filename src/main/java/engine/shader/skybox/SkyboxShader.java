package shader.skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.ICamera;
import shader.ShaderProgram;
import tool.math.Maths;

public class SkyboxShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_SKYBOX_PATH + "skyboxVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_SKYBOX_PATH + "skyboxFragmentShader.glsl";

	private static final float ROTATE_SPEED = 1f;

	private float rotation = 0;

	public SkyboxShader() {
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
		super.addUniform("projectionMatrix");
		super.addUniform("viewMatrix");
		super.addUniform("fogColour");
		super.addUniform("blendFactor");
		super.addUniform("cubeMap");
		super.addUniform("cubeMap2");
	}

	public void connectTextureUnits() {
		super.loadInt("cubeMap", 0);
		super.loadInt("cubeMap2", 1);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix("projectionMatrix", matrix);
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		if (!EngineMain.getIsEnginePaused()) {
			rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
		}
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
		super.loadMatrix("viewMatrix", matrix);
	}

	public void loadFogColour(float r, float g, float b) {
		super.loadVector("fogColour", new Vector3f(r, g, b));
	}

	public void loadBlendFactor(float blend) {
		super.loadFloat("blendFactor", blend);
	}

}