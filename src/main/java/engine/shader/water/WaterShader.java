package shader.water;


import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.Light;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = EngineSettings.SHADERS_WATER_PATH + "waterVertexShader.glsl";
	private final static String FRAGMENT_FILE = EngineSettings.SHADERS_WATER_PATH + "waterFragmentShader.glsl";
	
	public WaterShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindFragOutput(1, "out_BrightColor");
		bindAttribute(0, "position");
	}

	@Override
	protected void loadUniformLocations() {
		addUniform("projectionMatrix");
		addUniform("viewMatrix");
		addUniform("modelMatrix");
		addUniform("reflectionTexture");
		addUniform("refractionTexture");
		addUniform("dudvMap");
		addUniform("moveFactor");
		addUniform("cameraPosition");
		addUniform("normalMap");
		addUniform("lightColour");
		addUniform("lightPosition");
		addUniform("depthMap");
		addUniform("tiling");
		addUniform("skyColour");
		addUniform("fogDensity");
		addUniform("waveStrength");
	}

	public void connectTextureUnits() {
		super.loadInt("reflectionTexture", 0);
		super.loadInt("refractionTexture", 1);
		super.loadInt("dudvMap", 2);
		super.loadInt("normalMap", 3);
		super.loadInt("depthMap", 4);
	}

	public void loadLight(Light sun) {
		super.loadVector("lightColour", sun.getColour());
		super.loadVector("lightPosition", sun.getPosition());
	}

	public void loadMoveFactor(float factor) {
		super.loadFloat("moveFactor", factor);
	}

	public void loadTilingSize(float size) {
		super.loadFloat("tiling", size);
	}

	public void loadWaveStrength(float strength) {
		super.loadFloat("waveStrength", strength);
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector("skyColour", new Vector3fF(r, g, b));
	}

	public void loadFogDensity(float density) {
		super.loadFloat("fogDensity", density);
	}

	public void loadProjectionMatrix(VMatrix4f projection) {
		loadMatrix("projectionMatrix", projection);
	}

	public void loadViewMatrix(ICamera camera) {
		VMatrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix("viewMatrix", viewMatrix);
		super.loadVector("cameraPosition", camera.getPosition());
	}

	public void loadModelMatrix(VMatrix4f modelMatrix) {
		loadMatrix("modelMatrix", modelMatrix);
	}

}