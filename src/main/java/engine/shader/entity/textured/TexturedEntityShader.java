package shader.entity.textured;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class TexturedEntityShader extends ShaderProgram {
	
	//----shaders
	public static final String VERTEX_FILE = EngineSettings.SHADERS_ENTITY_TEXTURED_PATH + "texturedEntity_V_shader.glsl";
	public static final String FRAGMENT_FILE = EngineSettings.SHADERS_ENTITY_TEXTURED_PATH + "texturedEntity_F_shader.glsl";
	//----attributes
	public static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	public static final String ATTRIBUTE_OUT_BRIGHT_COLOR = "out_BrightColor";
	public static final String ATTRIBUTE_POSITION = "position";
	public static final String ATTRIBUTE_TEXTURE_COORDINATES = "textureCoordinates";
	public static final String ATTRIBUTE_NORMAL = "normal";
	//----uniforms
	//matrix
	public static final String UNIFORM_TRANSFORMATION_MATRIX = "transformationMatrix";
	public static final String UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
	public static final String UNIFORM_VIEW_MATRIX = "viewMatrix";
	public static final String UNIFORM_CAMERA_POSITION = "cameraPosition";
	//material
	public static final String UNIFORM_DIFFUSE_MAP = "diffuseMap";
	public static final String UNIFORM_SPECULAR_MAP = "specularMap";
	public static final String UNIFORM_ALPHA_MAP = "alphaMap";
	public static final String UNIFORM_SHADOW_MAP = "shadowMap";
	public static final String UNIFORM_ENVIRONMENT_MAP = "enviroMap";
	//boolean
	public static final String UNIFORM_USES_FAKE_LIGHTING = "usesFakeLighting";
	public static final String UNIFORM_USES_SPECULAR_MAP = "usesSpecularMap";
	public static final String UNIFORM_USES_ALPHA_MAP = "usesAlphaMap";
	public static final String UNIFORM_IS_CHOSEN = "isChosen";
	//shine variables
	public static final String UNIFORM_SHINE_DAMPER = "shineDamper";
	public static final String UNIFORM_REFLECTIVITY = "reflectivity";
	public static final String UNIFORM_REFLECTIVE_FACTOR = "reflectiveFactor";
	public static final String UNIFORM_REFRACTIVE_INDEX = "refractiveIndex";
	public static final String UNIFORM_REFRACTIVE_FACTOR = "refractiveFactor";		
	//ambient variables
	public static final String UNIFORM_SKY_COLOR = "skyColor";
	public static final String UNIFORM_FOG_DENSITY = "fogDensity";
	//texture coords variables
	public static final String UNIFORM_NUMBER_OF_ROWS = "numberOfRows";
	public static final String UNIFORM_OFFSET = "offset";
	//clip plane
	public static final String UNIFORM_CLIP_PLANE = "clipPlane";
	//shadow variables		
	public static final String UNIFORM_TO_SHADOW_MAP_SPACE = "toShadowMapSpace";		
	public static final String UNIFORM_SHADOW_DISTANCE = "shadowDistance";
	public static final String UNIFORM_SHADOW_MAP_SIZE = "shadowMapSize";
	public static final String UNIFORM_SHADOW_TRANSITION_DISTANCE = "shadowTransitionDistance";
	public static final String UNIFORM_SHADOW_PCF_COUNT = "shadowPCFCount";	
	//light
	public static final String UNIFORM_LIGHT_COUNT = "lightCount";
	public static final String UNIFORM_LIGHT_POSITION = "lightPosition";
	public static final String UNIFORM_LIGHT_COLOR = "lightColor";
	public static final String UNIFORM_ATTENUATION = "attenuation";
	
	public TexturedEntityShader() {
		super();
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
		super.addUniform(UNIFORM_LIGHT_COUNT);
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			super.addUniform(UNIFORM_LIGHT_POSITION + "[" + i + "]");
			super.addUniform(UNIFORM_LIGHT_COLOR + "[" + i + "]");
			super.addUniform(UNIFORM_ATTENUATION + "[" + i + "]");
		}
	}

	public void connectTextureUnits() {
		super.loadInt(UNIFORM_DIFFUSE_MAP, 0);
		super.loadInt(UNIFORM_SPECULAR_MAP, 4);
		super.loadInt(UNIFORM_ALPHA_MAP, 5);
		super.loadInt(UNIFORM_SHADOW_MAP, 6);
		super.loadInt(UNIFORM_ENVIRONMENT_MAP, 7);		
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}

	public void loadViewMatrix(ICamera camera) {
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

	public void loadSkyColour(float r, float g, float b) {
		super.load3DVector(UNIFORM_SKY_COLOR, new Vector3f(r, g, b));
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

	public void loadCamera(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
		super.load3DVector(UNIFORM_CAMERA_POSITION, camera.getPosition());
	}

	public void loadLights(Collection<ILight> lights) {
		super.loadInt(UNIFORM_LIGHT_COUNT, EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.load3DVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", light.getPosition());
				super.load3DVector(UNIFORM_LIGHT_COLOR + "[" + i + "]", light.getColor());
				super.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", light.getAttenuation());
			} else {
				super.load3DVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", new Vector3f(0, 0, 0));
				super.load3DVector(UNIFORM_LIGHT_COLOR + "[" + i + "]", new Vector3f(0, 0, 0));
				super.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", new Vector3f(1, 0, 0));
			}
		}

	}
}