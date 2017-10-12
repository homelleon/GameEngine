package shader.terrain;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class TerrainShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	private static final String ATTRIBUTE_OUT_BRIGHT_COLOR = "out_BrightColor";
	private static final String ATTRIBUTE_POSITION = "position";
	private static final String ATTRIBUTE_TEXTURE_COORDINATES = "textureCoordinates";
	private static final String ATTRIBUTE_NORMAL = "normal";
	//----uniforms
	//matrix
	private static final String UNIFORM_TRANSFORMATION_MATRIX = "transformationMatrix";
	private static final String UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
	private static final String UNIFORM_VIEW_MATRIX = "viewMatrix";
	//shine varibles
	private static final String UNIFORM_SHINE_DAMPER = "shineDamper";
	private static final String UNIFORM_REFLECTIVITY = "reflectivity";
	//ambient variables
	private static final String UNIFORM_SKY_COLOR = "skyColour";
	private static final String UNIFORM_FOG_DENSITY = "fogDensity";
	//material
	private static final String UNIFORM_BACKGROUND_TEXTURE = "backgroundTexture";
	private static final String UNIFORM_RED_TEXTURE = "rTexture";
	private static final String UNIFORM_GREEN_TEXTURE = "gTexture";
	private static final String UNIFORM_BLUE_TEXTURE = "bTexture";
	private static final String UNIFORM_BLEND_MAP = "blendMap";
	private static final String UNIFORM_SHADOW_MAP = "shadowMap";
	//clip plane
	private static final String UNIFORM_CLIP_PLANE = "clipPlane";
	//shadow variables
	private static final String UNIFORM_TO_SHADOW_MAP_SPACE = "toShadowMapSpace";		
	private static final String UNIFORM_SHADOW_DISTANCE = "shadowDistance";
	private static final String UNIFORM_SHADOW_MAP_SIZE = "shadowMapSize";
	private static final String UNIFORM_SHADOW_TRANSITION_DISTANCE = "shadowTransitionDistance";
	private static final String UNIFORM_SHADOW_PCF_COUNT = "shadowPCFCount";
	//light
	private static final String UNIFORM_LIGHT_COUNT = "lightCount";
	private static final String UNIFORM_LIGHT_POSITION = "lightPosition";
	private static final String UNIFORM_LIGHT_COLOR = "lightColor";
	private static final String UNIFORM_ATTENUATION = "attenuation";
	
	public TerrainShader() {
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
		//shine varibles
		super.addUniform(UNIFORM_SHINE_DAMPER);
		super.addUniform(UNIFORM_REFLECTIVITY);
		//ambient variables
		super.addUniform(UNIFORM_SKY_COLOR);
		super.addUniform(UNIFORM_FOG_DENSITY);
		//material
		super.addUniform(UNIFORM_BACKGROUND_TEXTURE);
		super.addUniform(UNIFORM_RED_TEXTURE);
		super.addUniform(UNIFORM_GREEN_TEXTURE);
		super.addUniform(UNIFORM_BLUE_TEXTURE);
		super.addUniform(UNIFORM_BLEND_MAP);
		super.addUniform(UNIFORM_SHADOW_MAP);
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
		super.loadInt(UNIFORM_BACKGROUND_TEXTURE, 0);
		super.loadInt(UNIFORM_RED_TEXTURE, 1);
		super.loadInt(UNIFORM_GREEN_TEXTURE, 2);
		super.loadInt(UNIFORM_BLUE_TEXTURE, 3);
		super.loadInt(UNIFORM_BLEND_MAP, 4);
		super.loadInt(UNIFORM_SHADOW_MAP, 6);
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

	public void loadClipPlane(Vector4f clipPlane) {
		super.load4DVector(UNIFORM_CLIP_PLANE, clipPlane);
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(UNIFORM_SKY_COLOR, new Vector3f(r, g, b));
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(UNIFORM_SHINE_DAMPER, damper);
		super.loadFloat(UNIFORM_REFLECTIVITY, reflectivity);
	}

	public void loadFogDensity(float density) {
		super.loadFloat(UNIFORM_FOG_DENSITY, density);
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}

	public void loadLights(Collection<ILight> lights) {
		super.loadInt(UNIFORM_LIGHT_COUNT, EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.loadVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", light.getPosition());
				super.loadVector(UNIFORM_LIGHT_COLOR + "[" + i + "]", light.getColor());
				super.loadVector(UNIFORM_ATTENUATION + "[" + i + "]", light.getAttenuation());
			} else {
				super.loadVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", new Vector3f(0, 0, 0));
				super.loadVector(UNIFORM_LIGHT_COLOR + "[" + i + "]", new Vector3f(0, 0, 0));
				super.loadVector(UNIFORM_ATTENUATION + "[" + i + "]", new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(UNIFORM_PROJECTION_MATRIX, projection);
	}

}
