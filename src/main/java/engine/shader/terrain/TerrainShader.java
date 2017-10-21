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
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class TerrainShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_F_shader.glsl";
	private static final String GEOMETRY_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_G_shader.glsl";
	private static final String TESSELLATION_EVALUATION_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_TE_shader.glsl";
	private static final String TESSELLATION_CONTROL_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "terrain_TC_shader.glsl";
	
	//----attributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	private static final String ATTRIBUTE_OUT_BRIGHT_COLOR = "out_BrightColor";
	private static final String ATTRIBUTE_POSITION = "in_position";
	private static final String ATTRIBUTE_TEXTURE_COORDINATES = "textureCoordinates";
	private static final String ATTRIBUTE_NORMAL = "normal";
	//----uniforms
	//matrix
	private static final String UNIFORM_TRANSFORMATION_MATRIX = "transformationMatrix";
	private static final String UNIFORM_PROJECTION_MATRIX = "projectionMatrix";
	private static final String UNIFORM_VIEW_MATRIX = "viewMatrix";	
	private static final String UNIFORM_LOCAL_MATRIX = "localMatrix";
	private static final String UNIFORM_WORLD_MATRIX = "worldMatrix";
	private static final String UNIFORM_CAMERA_POSITION = "cameraPosition";
	//tessellation
	private static final String UNIFORM_SCALE_Y = "scaleY";
	private static final String UNIFORM_INDEX = "index";
	private static final String UNIFORM_GAP = "gap";
	private static final String UNIFORM_LEVEL_OF_DISTANCE = "lod";
	private static final String UNIFORM_LOCATION = "location";
	private static final String UNIFORM_TESSELLATION_FACTOR = "tessellationFactor";
	private static final String UNIFORM_TESSELLATION_SLOPE = "tessellationSlope";
	private static final String UNIFORM_TESSELLATION_SHIFT = "tessellationShift";
	private static final String UNIFORM_LOD_MORPH_AREA = "lod_morph_area";
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
	private static final String UNIFORM_HEIGHT_MAP = "heightMap";
	private static final String UNIFORM_NORMAL_MAP = "normalMap";
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
		this.addVertexShader(VERTEX_FILE);
		this.addFragmentShader(FRAGMENT_FILE);
		this.addGeometryShader(GEOMETRY_SHADER);
		this.addTessellationEvaluationShader(TESSELLATION_EVALUATION_SHADER);
		this.addTessellationControlShader(TESSELLATION_CONTROL_SHADER);
		this.compileShader();
	}

	@Override
	protected void bindAttributes() {
		this.bindFragOutput(0, ATTRIBUTE_OUT_COLOR);
		this.bindFragOutput(1, ATTRIBUTE_OUT_BRIGHT_COLOR);
		this.bindAttribute(0, ATTRIBUTE_POSITION);
		this.bindAttribute(1, ATTRIBUTE_TEXTURE_COORDINATES);
		this.bindAttribute(2, ATTRIBUTE_NORMAL);
	}

	@Override
	protected void loadUniformLocations() {
		//matrix
		this.addUniform(UNIFORM_TRANSFORMATION_MATRIX);
		this.addUniform(UNIFORM_PROJECTION_MATRIX);
		this.addUniform(UNIFORM_VIEW_MATRIX);
		this.addUniform(UNIFORM_LOCAL_MATRIX);
		this.addUniform(UNIFORM_WORLD_MATRIX);
		this.addUniform(UNIFORM_CAMERA_POSITION);
		//tessellation
		this.addUniform(UNIFORM_SCALE_Y);
		this.addUniform(UNIFORM_INDEX);
		this.addUniform(UNIFORM_GAP);
		this.addUniform(UNIFORM_LEVEL_OF_DISTANCE);
		this.addUniform(UNIFORM_LOCATION);
		this.addUniform(UNIFORM_TESSELLATION_FACTOR);
		this.addUniform(UNIFORM_TESSELLATION_SLOPE);
		this.addUniform(UNIFORM_TESSELLATION_SHIFT);
		for(int i = 0; i < 8; i++) {
			this.addUniform(UNIFORM_LOD_MORPH_AREA + "[" + i + "]");
		}	
		//shine varibles
		this.addUniform(UNIFORM_SHINE_DAMPER);
		this.addUniform(UNIFORM_REFLECTIVITY);
		//ambient variables
		this.addUniform(UNIFORM_SKY_COLOR);
		this.addUniform(UNIFORM_FOG_DENSITY);
		//material
		this.addUniform(UNIFORM_BACKGROUND_TEXTURE);
		this.addUniform(UNIFORM_RED_TEXTURE);
		this.addUniform(UNIFORM_GREEN_TEXTURE);
		this.addUniform(UNIFORM_BLUE_TEXTURE);
		this.addUniform(UNIFORM_BLEND_MAP);
		this.addUniform(UNIFORM_SHADOW_MAP);
		this.addUniform(UNIFORM_HEIGHT_MAP);
		this.addUniform(UNIFORM_NORMAL_MAP);
		//clip plane
		this.addUniform(UNIFORM_CLIP_PLANE);
		//shadow variables
		this.addUniform(UNIFORM_TO_SHADOW_MAP_SPACE);		
		this.addUniform(UNIFORM_SHADOW_DISTANCE);
		this.addUniform(UNIFORM_SHADOW_MAP_SIZE);
		this.addUniform(UNIFORM_SHADOW_TRANSITION_DISTANCE);
		this.addUniform(UNIFORM_SHADOW_PCF_COUNT);
		//light
		this.addUniform(UNIFORM_LIGHT_COUNT);
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			this.addUniform(UNIFORM_LIGHT_POSITION + "[" + i + "]");
			this.addUniform(UNIFORM_LIGHT_COLOR + "[" + i + "]");
			this.addUniform(UNIFORM_ATTENUATION + "[" + i + "]");
		}
	}

	public void connectTextureUnits() {
		this.loadInt(UNIFORM_BACKGROUND_TEXTURE, 0);
		this.loadInt(UNIFORM_RED_TEXTURE, 1);
		this.loadInt(UNIFORM_GREEN_TEXTURE, 2);
		this.loadInt(UNIFORM_BLUE_TEXTURE, 3);
		this.loadInt(UNIFORM_BLEND_MAP, 4);
		this.loadInt(UNIFORM_SHADOW_MAP, 6);
		this.loadInt(UNIFORM_HEIGHT_MAP, 7);
		this.loadInt(UNIFORM_NORMAL_MAP, 8);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		this.loadMatrix(UNIFORM_PROJECTION_MATRIX, projection);
	}

	public void loadViewMatrix(Matrix4f viewMatrix) {
		this.loadMatrix(UNIFORM_VIEW_MATRIX, viewMatrix);
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		this.loadMatrix(UNIFORM_TRANSFORMATION_MATRIX, matrix);
	}
	
	public void loadWorldMatrix(Matrix4f worldMatrix) {
		this.loadMatrix(UNIFORM_WORLD_MATRIX, worldMatrix);
	}
	
	public void loadLocalMatrix(Matrix4f localMatrix) {
		this.loadMatrix(UNIFORM_LOCAL_MATRIX, localMatrix);
	}
	
	public void loadCameraPosition(Vector3f position) {
		this.load3DVector(UNIFORM_CAMERA_POSITION, position);
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		this.loadMatrix(UNIFORM_TO_SHADOW_MAP_SPACE, matrix);
	}

	public void loadClipPlane(Vector4f clipPlane) {
		this.load4DVector(UNIFORM_CLIP_PLANE, clipPlane);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		this.loadFloat(UNIFORM_SHADOW_DISTANCE, shadowDistance);
		this.loadFloat(UNIFORM_SHADOW_MAP_SIZE, size);
		this.loadFloat(UNIFORM_SHADOW_TRANSITION_DISTANCE, transitionDistance);
		this.loadInt(UNIFORM_SHADOW_PCF_COUNT, pcfCount);
	}

	public void loadSkyColour(float r, float g, float b) {
		this.load3DVector(UNIFORM_SKY_COLOR, new Vector3f(r, g, b));
	}

	public void loadShineVariables(float damper, float reflectivity) {
		this.loadFloat(UNIFORM_SHINE_DAMPER, damper);
		this.loadFloat(UNIFORM_REFLECTIVITY, reflectivity);
	}

	public void loadFogDensity(float density) {
		this.loadFloat(UNIFORM_FOG_DENSITY, density);
	}
	
	public void loadScale(float scaleY){
		this.loadFloat(UNIFORM_SCALE_Y, scaleY);
	}
	
	public void loadLoDVariables(int lod, Vector2f index, float gap, Vector2f location) {
		this.loadInt(UNIFORM_LEVEL_OF_DISTANCE, lod);
		this.load2DVector(UNIFORM_INDEX, index);
		this.loadFloat(UNIFORM_GAP, gap);
		this.load2DVector(UNIFORM_LOCATION, location);
	}
	
	public void loadLodMorphAreaArray(int[] lodMorphAreas) {
		for(int i = 0; i < 8; i++) {
			this.loadInt(UNIFORM_LOD_MORPH_AREA + "[" + i + "]", lodMorphAreas[i]);
		}
	}
	
	public void loadTessellationVariables(int factor, float slope, float shift) {
		this.loadInt(UNIFORM_TESSELLATION_FACTOR, factor);
		this.loadFloat(UNIFORM_TESSELLATION_SLOPE, slope);
		this.loadFloat(UNIFORM_TESSELLATION_SHIFT, shift);
	}

	public void loadLights(Collection<ILight> lights) {
		this.loadInt(UNIFORM_LIGHT_COUNT, EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				this.load3DVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", light.getPosition());
				this.load3DVector(UNIFORM_LIGHT_COLOR + "[" + i + "]", light.getColor());
				this.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", light.getAttenuation());
			} else {
				this.load3DVector(UNIFORM_LIGHT_POSITION + "[" + i + "]", new Vector3f(0, 0, 0));
				this.load3DVector(UNIFORM_LIGHT_COLOR + "[" + i + "]", new Vector3f(0, 0, 0));
				this.load3DVector(UNIFORM_ATTENUATION + "[" + i + "]", new Vector3f(1, 0, 0));
			}
		}
	}
	
}
