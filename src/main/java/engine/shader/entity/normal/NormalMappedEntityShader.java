package shader.entity.normal;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class NormalMappedEntityShader extends ShaderProgram {
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_ENTITY_NORMAL_PATH + "normalEntity_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_ENTITY_NORMAL_PATH + "normalEntity_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	private static final String ATTRIBUTE_OUT_BRIGHT_COLOR = "out_BrightColor";
	private static final String ATTRIBUTE_POSITION= "in_position";
	private static final String ATTRIBUTE_TEXTURE_COORDINATES = "in_textureCoords";
	private static final String ATTRIBUTE_NORMAL = "in_normal";
	private static final String ATTRIBUTE_TANGENT = "in_tangent";
	//----uniforms
	//matrix
	private static final String UNIFORM_TRANSFORMATION_MATRIX = "Transformation";
	private static final String UNIFORM_PROJECTION_MATRIX = "Projection";
	private static final String UNIFORM_VIEW_MATRIX = "View";		
	//material
	private static final String UNIFORM_DIFFUSE_MAP = "diffuseMap";
	private static final String UNIFORM_NORMAL_MAP = "normalMap";
	private static final String UNIFORM_SPECULAR_MAP = "specularMap";
	private static final String UNIFORM_ALPHA_MAP = "alphaMap";
	private static final String UNIFORM_SHADOW_MAP = "shadowMap";
	//boolean
	private static final String UNIFORM_USES_SPECULAR_MAP = "usesSpecularMap";
	private static final String UNIFORM_USES_ALPHA_MAP = "usesAlphaMap";
	private static final String UNIFORM_USES_FAKE_LIGHTING = "usesFakeLighting";		
	private static final String UNIFORM_IS_CHOSEN = "isChosen";
	//shadow varialbes
	private static final String UNIFORM_TO_SHADOW_MAP_SPACE = "toShadowMapSpace";
	private static final String UNIFORM_SHADOW_DISTANCE = "shadowDistance";		
	private static final String UNIFORM_SHADOW_MAP_SIZE = "shadowMapSize";
	private static final String UNIFORM_SHADOW_TRANSITION_DISTANCE = "shadowTransitionDistance";
	private static final String UNIFORM_SHADOW_PCF_COUNT = "shadowPCFCount";	
	//shine variables
	private static final String UNIFORM_SHINE_DAMPER = "shineDamper";		
	private static final String UNIFORM_REFLECTIVITY = "reflectivity";	
	//ambient variables
	private static final String UNIFORM_SKY_COLOR = "skyColor";
	private static final String UNIFORM_FOG_DENSITY = "fogDensity";	
	//texture coords variables
	private static final String UNIFORM_NUMBER_OF_ROWS = "numberOfRows";
	private static final String UNIFORM_OFFSET = "offset";
	//clip plane
	private static final String UNIFORM_CLIP_PLANE = "clipPlane";			
	//light
	private static final String UNIFORM_LIGHT_POSITION_EYE_SPACE = "lightPositionEyeSpace";
	private static final String UNIFORM_LIGHT_COLOR = "lightColor";
	private static final String UNIFORM_ATTENUATION = "attenuation";
	
	public NormalMappedEntityShader() {
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
		super.bindAttribute(3, ATTRIBUTE_TANGENT);
	}

	@Override
	protected void loadUniformLocations() {
		//matrix
		super.addUniform(UNIFORM_TRANSFORMATION_MATRIX);
		super.addUniform(UNIFORM_PROJECTION_MATRIX);
		super.addUniform(UNIFORM_VIEW_MATRIX);
		//material
		super.addUniform(UNIFORM_DIFFUSE_MAP);
		super.addUniform(UNIFORM_NORMAL_MAP);
		super.addUniform(UNIFORM_SPECULAR_MAP);
		super.addUniform(UNIFORM_ALPHA_MAP);
		super.addUniform(UNIFORM_SHADOW_MAP);
		//boolean
		super.addUniform(UNIFORM_USES_SPECULAR_MAP);
		super.addUniform(UNIFORM_USES_ALPHA_MAP);
		super.addUniform(UNIFORM_USES_FAKE_LIGHTING);	
		super.addUniform(UNIFORM_IS_CHOSEN);
		//shadow varialbes
		super.addUniform(UNIFORM_TO_SHADOW_MAP_SPACE);
		super.addUniform(UNIFORM_SHADOW_DISTANCE);		
		super.addUniform(UNIFORM_SHADOW_MAP_SIZE);
		super.addUniform(UNIFORM_SHADOW_TRANSITION_DISTANCE);
		super.addUniform(UNIFORM_SHADOW_PCF_COUNT);	
		//shine variables
		super.addUniform(UNIFORM_SHINE_DAMPER);		
		super.addUniform(UNIFORM_REFLECTIVITY);	
		//ambient variables
		super.addUniform(UNIFORM_SKY_COLOR);
		super.addUniform(UNIFORM_FOG_DENSITY);	
		//texture coords variables
		super.addUniform(UNIFORM_NUMBER_OF_ROWS);
		super.addUniform(UNIFORM_OFFSET);
		//clip plane
		super.addUniform(UNIFORM_CLIP_PLANE);			
		//light
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			super.addUniform(UNIFORM_LIGHT_POSITION_EYE_SPACE + "[" + i + "]");
			super.addUniform(UNIFORM_LIGHT_COLOR + "[" + i + "]");
			super.addUniform(UNIFORM_ATTENUATION + "[" + i + "]");
		}
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TO_SHADOW_MAP_SPACE, matrix);
	}

	public void loadUseSpecularMap(boolean useMap) {
		super.loadBoolean(UNIFORM_USES_SPECULAR_MAP, useMap);
	}
	
	public void loadUseAlphaMap(boolean useMap) {
		super.loadBoolean(UNIFORM_USES_ALPHA_MAP, useMap);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(UNIFORM_FOG_DENSITY, density);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat(UNIFORM_SHADOW_DISTANCE, shadowDistance);
		super.loadFloat(UNIFORM_SHADOW_MAP_SIZE, size);
		super.loadFloat(UNIFORM_SHADOW_TRANSITION_DISTANCE, transitionDistance);
		super.loadInt(UNIFORM_SHADOW_PCF_COUNT, pcfCount);
	}

	public void connectTextureUnits() {
		super.loadInt(UNIFORM_DIFFUSE_MAP, 0);		
		super.loadInt(UNIFORM_NORMAL_MAP, 1);
		super.loadInt(UNIFORM_SPECULAR_MAP, 4);
		super.loadInt(UNIFORM_ALPHA_MAP, 5);
		super.loadInt(UNIFORM_SHADOW_MAP, 6);
	}

	public void loadViewMatrix(Matrix4f viewMatrix) {
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, projection);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}

	public void loadManipulationVariables(boolean isChosen) {
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

	public void loadLights(Collection<ILight> lights, Matrix4f viewMatrix) {
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.load3DVector(UNIFORM_LIGHT_POSITION_EYE_SPACE + "[" + i + "]", getEyeSpacePosition(light, viewMatrix));
				super.loadColor(UNIFORM_LIGHT_COLOR + "[" + i + "]", light.getColor());
				super.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", light.getAttenuation());
			} else {
				super.load3DVector(UNIFORM_LIGHT_POSITION_EYE_SPACE + "[" + i + "]", new Vector3f(0, 0, 0));
				super.loadColor(UNIFORM_LIGHT_COLOR + "[" + i + "]", new Color(0, 0, 0));
				super.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", new Vector3f(1, 0, 0));
			}
		}
	}

	private Vector3f getEyeSpacePosition(ILight light, Matrix4f viewMatrix) {
		Vector3f position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.x, position.y, position.z, 1f);
		eyeSpacePos = viewMatrix.transform(eyeSpacePos);
		return new Vector3f(eyeSpacePos);
	}

}
