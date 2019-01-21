package shader.entity;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.Camera;
import object.light.Light;
import shader.Shader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class EntityShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_ENTITY_TEXTURED_PATH + "entity_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_ENTITY_TEXTURED_PATH + "entity_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	private static final String ATTRIBUTE_OUT_BRIGHT_COLOR = "out_BrightColor";
	private static final String ATTRIBUTE_POSITION = "in_position";
	private static final String ATTRIBUTE_TEXTURE_COORDINATES = "in_textureCoords";
	private static final String ATTRIBUTE_NORMAL = "in_normal";
	private static final String ATTRIBUTE_TANGENT = "in_tangent";
	//----uniforms
	//matrix
	private static final String UNIFORM_TRANSFORMATION_MATRIX = "Transformation";
	private static final String UNIFORM_PROJECTION_MATRIX = "Projection";
	private static final String UNIFORM_VIEW_MATRIX = "View";
	private static final String UNIFORM_CAMERA_POSITION = "cameraPosition";
	//material
	private static final String UNIFORM_DIFFUSE_MAP = "diffuseMap";
	private static final String UNIFORM_NORMAL_MAP = "normalMap";
	private static final String UNIFORM_SPECULAR_MAP = "specularMap";
	private static final String UNIFORM_ALPHA_MAP = "alphaMap";
	private static final String UNIFORM_SHADOW_MAP = "shadowMap";
	private static final String UNIFORM_ENVIRONMENT_MAP = "enviroMap";
	//boolean
	private static final String UNIFORM_USES_FAKE_LIGHTING = "usesFakeLighting";
	private static final String UNIFORM_USES_NORMAL_MAP = "usesNormalMap";
	private static final String UNIFORM_USES_SPECULAR_MAP = "usesSpecularMap";
	private static final String UNIFORM_USES_ALPHA_MAP = "usesAlphaMap";
	private static final String UNIFORM_IS_CHOSEN = "isChosen";
	//shine variables
	private static final String UNIFORM_SHINE_DAMPER = "shineDamper";
	private static final String UNIFORM_REFLECTIVITY = "reflectivity";
	private static final String UNIFORM_REFLECTIVE_FACTOR = "reflectiveFactor";
	private static final String UNIFORM_REFRACTIVE_INDEX = "refractiveIndex";
	private static final String UNIFORM_REFRACTIVE_FACTOR = "refractiveFactor";		
	//ambient variables
	private static final String UNIFORM_SKY_COLOR = "skyColor";
	private static final String UNIFORM_FOG_DENSITY = "fogDensity";
	//texture coords variables
	private static final String UNIFORM_NUMBER_OF_ROWS = "numberOfRows";
	private static final String UNIFORM_OFFSET = "offset";
	//clip plane
	private static final String UNIFORM_CLIP_PLANE = "clipPlane";
	//shadow variables		
	private static final String UNIFORM_TO_SHADOW_MAP_SPACE = "toShadowMapSpace";		
	private static final String UNIFORM_SHADOW_DISTANCE = "shadowDistance";
	private static final String UNIFORM_SHADOW_MAP_SIZE = "shadowMapSize";
	private static final String UNIFORM_SHADOW_TRANSITION_DISTANCE = "shadowTransitionDistance";
	private static final String UNIFORM_SHADOW_PCF_COUNT = "shadowPCFCount";	
	//light
	private static final String UNIFORM_LIGHT_POSITION_EYE_SPACE = "lightPositionEyeSpace";
	private static final String UNIFORM_LIGHT_POSITION = "lightPosition";
	private static final String UNIFORM_LIGHT_COLOR = "lightColor";
	private static final String UNIFORM_ATTENUATION = "attenuation";
	
	public EntityShader() {
		super(Shader.ENTITY);
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, ATTRIBUTE_OUT_COLOR);
		super.bindFragOutput(1, ATTRIBUTE_OUT_BRIGHT_COLOR);
		super.bindAttribute(0, ATTRIBUTE_POSITION);
		super.bindAttribute(1, ATTRIBUTE_TEXTURE_COORDINATES);
		super.bindAttribute(2, ATTRIBUTE_NORMAL);
	}
	
	@Override
	protected void loadUniformLocations() {
		//matrix
		super.addUniform(UNIFORM_TRANSFORMATION_MATRIX);
		super.addUniform(UNIFORM_PROJECTION_MATRIX);
		super.addUniform(UNIFORM_VIEW_MATRIX);
		super.addUniform(UNIFORM_CAMERA_POSITION);
		//material
		super.addUniform(UNIFORM_DIFFUSE_MAP);
//		super.addUniform(UNIFORM_NORMAL_MAP);
		super.addUniform(UNIFORM_SPECULAR_MAP);
		super.addUniform(UNIFORM_ALPHA_MAP);
		super.addUniform(UNIFORM_SHADOW_MAP);
		super.addUniform(UNIFORM_ENVIRONMENT_MAP);
		//boolean
		super.addUniform(UNIFORM_USES_FAKE_LIGHTING);
		super.addUniform(UNIFORM_USES_SPECULAR_MAP);
		super.addUniform(UNIFORM_USES_ALPHA_MAP);
		super.addUniform(UNIFORM_IS_CHOSEN);
		//shine variables
		super.addUniform(UNIFORM_SHINE_DAMPER);
		super.addUniform(UNIFORM_REFLECTIVITY);
		super.addUniform(UNIFORM_REFLECTIVE_FACTOR);
		super.addUniform(UNIFORM_REFRACTIVE_INDEX);
		super.addUniform(UNIFORM_REFRACTIVE_FACTOR);		
		//ambient variables
		super.addUniform(UNIFORM_SKY_COLOR);
		super.addUniform(UNIFORM_FOG_DENSITY);
		//texture coords variables
		super.addUniform(UNIFORM_NUMBER_OF_ROWS);
		super.addUniform(UNIFORM_OFFSET);
		//clip plane
		super.addUniform(UNIFORM_CLIP_PLANE);
		//shadow variables		
		super.addUniform(UNIFORM_TO_SHADOW_MAP_SPACE);		
		super.addUniform(UNIFORM_SHADOW_DISTANCE);
		super.addUniform(UNIFORM_SHADOW_MAP_SIZE);
		super.addUniform(UNIFORM_SHADOW_TRANSITION_DISTANCE);
		super.addUniform(UNIFORM_SHADOW_PCF_COUNT);	
		//light
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			super.addUniform(UNIFORM_LIGHT_POSITION + "[" + i + "]");
			super.addUniform(UNIFORM_LIGHT_COLOR + "[" + i + "]");
			super.addUniform(UNIFORM_ATTENUATION + "[" + i + "]");
		}
	}

	public void connectTextureUnits() {
		super.loadInt(UNIFORM_DIFFUSE_MAP, 0);
//		super.loadInt(UNIFORM_NORMAL_MAP, 1);
		super.loadInt(UNIFORM_SPECULAR_MAP, 4);
		super.loadInt(UNIFORM_ALPHA_MAP, 5);
		super.loadInt(UNIFORM_SHADOW_MAP, 6);
		super.loadInt(UNIFORM_ENVIRONMENT_MAP, 7);		
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, projection);
	}

	public void loadUsesSpecularMap(boolean useMap) {
		super.loadBoolean(UNIFORM_USES_SPECULAR_MAP, useMap);
	}
	
	public void loadUsesAlphaMap(boolean useMap) {
		super.loadBoolean(UNIFORM_USES_ALPHA_MAP, useMap);
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TO_SHADOW_MAP_SPACE, matrix);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat(UNIFORM_SHADOW_DISTANCE, shadowDistance);
		super.loadFloat(UNIFORM_SHADOW_MAP_SIZE, size);
		super.loadFloat(UNIFORM_SHADOW_TRANSITION_DISTANCE, transitionDistance);
		super.loadInt(UNIFORM_SHADOW_PCF_COUNT, pcfCount);
	}

	public void loadManipulateVariables(boolean isChosen) {
		super.loadBoolean(UNIFORM_IS_CHOSEN, isChosen);
	}

	public void loadClipPlane(Vector4f plane) {
		super.load4DVector(UNIFORM_CLIP_PLANE, plane);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(UNIFORM_NUMBER_OF_ROWS, numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector(UNIFORM_OFFSET, new Vector2f(x, y));
	}

	public void loadSkyColor(Color color) {
		super.loadColor(UNIFORM_SKY_COLOR, color);
	}

	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean(UNIFORM_USES_FAKE_LIGHTING, useFake);
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(UNIFORM_SHINE_DAMPER, damper);
		super.loadFloat(UNIFORM_REFLECTIVITY, reflectivity);
	}

	public void loadReflectiveFactor(float index) {
		super.loadFloat(UNIFORM_REFLECTIVE_FACTOR, index);
	}

	public void loadRefractVariables(float index, float factor) {
		super.loadFloat(UNIFORM_REFRACTIVE_INDEX, index);
		super.loadFloat(UNIFORM_REFRACTIVE_FACTOR, factor);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(UNIFORM_FOG_DENSITY, density);
	}

	public void loadCamera(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
		super.load3DVector(UNIFORM_CAMERA_POSITION, camera.getPosition());
	}

	public void loadLights(Collection<Light> lights) {
		Iterator<Light> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				Light light = iterator.next();
				super.load3DVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", light.getPosition());
				super.loadColor(UNIFORM_LIGHT_COLOR + "[" + i + "]", light.getColor());
				super.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", light.getAttenuation());
			} else {
				super.load3DVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", new Vector3f(0, 0, 0));
				super.loadColor(UNIFORM_LIGHT_COLOR + "[" + i + "]", new Color(0, 0, 0));
				super.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", new Vector3f(1, 0, 0));
			}
		}

	}
}