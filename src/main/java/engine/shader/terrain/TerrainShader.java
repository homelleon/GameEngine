package shader.terrain;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.Maths;

public class TerrainShader extends ShaderProgram {

	public static final String VERTEX_FILE = EngineSettings.SHADERS_TERRAIN_PATH + "terrainVertexShader.txt";
	public static final String FRAGMENT_FILE = EngineSettings.SHADERS_TERRAIN_PATH + "terrainFragmentShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightCount;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int loaction_shineDamper;
	private int loaction_reflectivity;
	private int location_skyColour;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_plane;
	private int location_fogDensity;
	private int location_toShadowMapSpace;
	private int location_shadowMap;
	private int location_shadowDistance;
	private int location_shadowMapSize;
	private int location_shadowTransitionDistance;
	private int location_shadowPCFCount;

	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindFragOutput(1, "out_BrightColor");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		loaction_shineDamper = super.getUniformLocation("shineDamper");
		loaction_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColour = super.getUniformLocation("skyColour");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap = super.getUniformLocation("blendMap");
		location_plane = super.getUniformLocation("plane");
		location_fogDensity = super.getUniformLocation("fogDensity");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_shadowDistance = super.getUniformLocation("shadowDistance");
		location_shadowMapSize = super.getUniformLocation("shadowMapSize");
		location_shadowTransitionDistance = super.getUniformLocation("shadowTransitionDistance");
		location_shadowPCFCount = super.getUniformLocation("shadowPCFCount");

		location_lightCount = super.getUniformLocation("lightCount");
		location_lightPosition = new int[EngineSettings.MAX_LIGHTS];
		location_lightColour = new int[EngineSettings.MAX_LIGHTS];
		location_attenuation = new int[EngineSettings.MAX_LIGHTS];
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void connectTextureUnits() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
		super.loadInt(location_shadowMap, 5);
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat(location_shadowDistance, shadowDistance);
		super.loadFloat(location_shadowMapSize, size);
		super.loadFloat(location_shadowTransitionDistance, transitionDistance);
		super.loadInt(location_shadowPCFCount, pcfCount);
	}

	public void loadClipPlane(Vector4f clipPlane) {
		super.load4DVector(location_plane, clipPlane);
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(loaction_shineDamper, damper);
		super.loadFloat(loaction_reflectivity, reflectivity);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(location_fogDensity, density);
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadLights(Collection<ILight> lights) {
		super.loadInt(location_lightCount, EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.loadVector(location_lightPosition[i], light.getPosition());
				super.loadVector(location_lightColour[i], light.getColour());
				super.loadVector(location_attenuation[i], light.getAttenuation());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
