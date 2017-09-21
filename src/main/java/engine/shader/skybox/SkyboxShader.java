package shader.skybox;


import core.EngineMain;
import core.display.DisplayManager;
import core.settings.EngineSettings;
import object.camera.ICamera;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vec3f;

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
		matrix.m[3][0] = 0;
		matrix.m[3][1] = 0;
		matrix.m[3][2] = 0;
		if (!EngineMain.getIsEnginePaused()) {
			rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
		}
		matrix.rotate((float) Math.toRadians(rotation), new Vec3f(0, 1, 0));
		super.loadMatrix("viewMatrix", matrix);
	}

	public void loadFogColour(float r, float g, float b) {
		super.loadVector("fogColour", new Vec3f(r, g, b));
	}

	public void loadBlendFactor(float blend) {
		super.loadFloat("blendFactor", blend);
	}

}