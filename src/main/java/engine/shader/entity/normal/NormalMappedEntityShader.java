package shader.entity.normal;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.light.ILight;
import shader.ShaderProgram;

public class NormalMappedEntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_ENTITY_NORMAL_PATH + "normalEntityVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_ENTITY_NORMAL_PATH + "normalEntityFragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightCount;
	private int location_lightPositionEyeSpace[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	private int location_plane;
	private int location_modelTexture;
	private int location_fogDensity;
	private int location_normalMap;
	private int location_toShadowMapSpace;
	private int location_shadowDistance;
	private int location_shadowMap;
	private int location_shadowMapSize;
	private int location_shadowTransitionDistance;
	private int location_shadowPCFCount;
	private int location_specularMap;
	private int location_usesSpecularMap;
	private int location_isChosen;

	public NormalMappedEntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindFragOutput(1, "out_BrightColor");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "tangent");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColour = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_plane = super.getUniformLocation("plane");
		location_modelTexture = super.getUniformLocation("modelTexture");
		location_fogDensity = super.getUniformLocation("fogDensity");
		location_normalMap = super.getUniformLocation("normalMap");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowDistance = super.getUniformLocation("shadowDistance");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_shadowMapSize = super.getUniformLocation("shadowMapSize");
		location_shadowTransitionDistance = super.getUniformLocation("shadowTransitionDistance");
		location_shadowPCFCount = super.getUniformLocation("shadowPCFCount");
		location_specularMap = super.getUniformLocation("specularMap");
		location_usesSpecularMap = super.getUniformLocation("usesSpecularMap");
		location_isChosen = super.getUniformLocation("isChosen");

		location_lightCount = super.getUniformLocation("lightCount");
		location_lightPositionEyeSpace = new int[EngineSettings.MAX_LIGHTS];
		location_lightColour = new int[EngineSettings.MAX_LIGHTS];
		location_attenuation = new int[EngineSettings.MAX_LIGHTS];
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			location_lightPositionEyeSpace[i] = super.getUniformLocation("lightPositionEyeSpace[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}

	public void loadUseSpecularMap(boolean useMap) {
		super.loadBoolean(location_usesSpecularMap, useMap);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(location_fogDensity, density);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat(location_shadowDistance, shadowDistance);
		super.loadFloat(location_shadowMapSize, size);
		super.loadFloat(location_shadowTransitionDistance, transitionDistance);
		super.loadInt(location_shadowPCFCount, pcfCount);
	}

	public void connectTextureUnits() {
		super.loadInt(location_modelTexture, 0);
		super.loadInt(location_specularMap, 1);
		super.loadInt(location_normalMap, 2);
		super.loadInt(location_shadowMap, 5);
	}

	public void loadManipulationVariables(boolean isChosen) {
		super.loadBoolean(location_isChosen, isChosen);
	}

	public void loadClipPlane(Vector4f plane) {
		super.load4DVector(location_plane, plane);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x, y));
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadLights(Collection<ILight> lights, Matrix4f viewMatrix) {
		super.loadInt(location_lightCount, EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.loadVector(location_lightPositionEyeSpace[i], getEyeSpacePosition(light, viewMatrix));
				super.loadVector(location_lightColour[i], light.getColour());
				super.loadVector(location_attenuation[i], light.getAttenuation());
			} else {
				super.loadVector(location_lightPositionEyeSpace[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadViewMatrix(Matrix4f viewMatrix) {
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

	private Vector3f getEyeSpacePosition(ILight light, Matrix4f viewMatrix) {
		Vector3f position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.x, position.y, position.z, 1f);
		Matrix4f.transform(viewMatrix, eyeSpacePos, eyeSpacePos);
		return new Vector3f(eyeSpacePos);
	}

}
