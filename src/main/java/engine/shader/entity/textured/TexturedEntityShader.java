package shader.entity.textured;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.Maths;

/*
 * EntityShader - шейдер объектов
 * 03.02.17
 * ------------
 */

public class TexturedEntityShader extends ShaderProgram {

	public static final String VERTEX_FILE = EngineSettings.SHADERS_ENTITY_TEXTURED_PATH + "texturedEntityVertexShader.glsl";
	public static final String FRAGMENT_FILE = EngineSettings.SHADERS_ENTITY_TEXTURED_PATH + "texturedEntityFragmentShader.glsl";

	private int location_transformationMatrix; // матрица трансформации
	private int location_projectionMatrix; // проективная матрица
	private int location_viewMatrix; // видовая матрица
	private int location_cameraPosition; // поиция камеры
	private int location_lightCount; // число источников света
	private int location_lightPosition[]; // позиция света
	private int location_lightColour[]; // цвет света
	private int location_attenuation[]; // затухание света
	private int location_shineDamper; // регулятор блеска
	private int location_reflectivity; // отражение
	private int location_reflectiveFactor; // степень отражения
	private int location_refractiveIndex; // индекс преломления
	private int location_refractiveFactor; // степень преломления
	private int location_enviroMap; // карта окружения
	private int location_useFakeLighting; // использовать искусственное
											// освещение
	private int location_skyColour; // цвет неба
	private int location_numberOfRows; // число строк в текстуре
	private int location_offset; // смещение
	private int location_plane; // плоскость отсечения
	private int location_fogDensity; // плотность тумана
	private int location_toShadowMapSpace;
	private int location_shadowMap; // карта теней
	private int location_shadowDistance; // дистанция теней
	private int location_shadowMapSize; // размер карты теней
	private int location_shadowTransitionDistance; // расстояние транзиции теней
	private int location_shadowPCFCount; // число PCF теней
	private int location_specularMap; // карта бликов
	private int location_usesSpecularMap; // использовать карту бликов
	private int location_modelTexture; // текстура модели
	private int location_isChosen; // Выбранный объект

	public TexturedEntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	// связать атрибуты
	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindFragOutput(1, "out_BrightColor");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	public void connectTextureUnits() {
		super.loadInt(location_modelTexture, 0);
		super.loadInt(location_specularMap, 1);
		super.loadInt(location_enviroMap, 3);
		super.loadInt(location_shadowMap, 5);
	}

	public void loadUsesSpecularMap(boolean useMap) {
		super.loadBoolean(location_usesSpecularMap, useMap);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_cameraPosition = super.getUniformLocation("cameraPosition");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_reflectiveFactor = super.getUniformLocation("reflectiveFactor");
		location_refractiveIndex = super.getUniformLocation("refractiveIndex");
		location_refractiveFactor = super.getUniformLocation("refractiveFactor");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_plane = super.getUniformLocation("plane");
		location_fogDensity = super.getUniformLocation("fogDensity");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_shadowDistance = super.getUniformLocation("shadowDistance");
		location_shadowMapSize = super.getUniformLocation("shadowMapSize");
		location_shadowTransitionDistance = super.getUniformLocation("shadowTransitionDistance");
		location_shadowPCFCount = super.getUniformLocation("shadowPCFCount");
		location_specularMap = super.getUniformLocation("specularMap");
		location_usesSpecularMap = super.getUniformLocation("usesSpecularMap");
		location_modelTexture = super.getUniformLocation("modelTexture");
		location_enviroMap = super.getUniformLocation("enviroMap");
		location_isChosen = super.getUniformLocation("isChosen");

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

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat(location_shadowDistance, shadowDistance);
		super.loadFloat(location_shadowMapSize, size);
		super.loadFloat(location_shadowTransitionDistance, transitionDistance);
		super.loadInt(location_shadowPCFCount, pcfCount);
	}

	public void loadManipulateVariables(boolean isChosen) {
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

	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	public void loadReflectiveFactor(float index) {
		super.loadFloat(location_reflectiveFactor, index);
	}

	public void loadRefractVariables(float index, float factor) {
		super.loadFloat(location_refractiveIndex, index);
		super.loadFloat(location_refractiveFactor, factor);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(location_fogDensity, density);
	}

	public void loadCamera(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
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
