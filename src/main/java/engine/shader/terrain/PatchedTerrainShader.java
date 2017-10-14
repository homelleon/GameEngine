package shader.terrain;

import core.settings.EngineSettings;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class PatchedTerrainShader extends ShaderProgram {
	
	//----shaders
	private static final String VERTEX_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "patchedTerrain_V_shader.glsl";
	private static final String FRAGMENT_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "patchedTerrain_F_shader.glsl";
	private static final String GEOMETRY_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "patchedTerrain_G_shader.glsl";
	private static final String TESSELLATION_EVALUATION_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "patchedTerrain_TE_shader.glsl";
	private static final String TESSELLATION_CONTROL_SHADER = EngineSettings.SHADERS_TERRAIN_PATH + "patchedTerrain_TC_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_OUT_COLOR = "out_Color";
	private static final String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	private static final String UNIFORM_LOCAL_MATRIX = "localMatrix";
	private static final String UNIFORM_WORLD_MATRIX = "worldMatrix";
	private static final String UNIFORM_SCALE_Y = "scaleY";
	private static final String UNIFORM_INDEX = "index";
	private static final String UNIFORM_GAP = "gap";
	private static final String UNIFORM_LEVEL_OF_DISTANCE = "lod";
	private static final String UNIFORM_LOCATION = "location";
	private static final String UNIFORM_TESSELLATION_FACTOR = "tessellationFactor";
	private static final String UNIFORM_TESSELLATION_SLOPE = "tessellationSlope";
	private static final String UNIFORM_TESSELLATION_SHIFT = "tessellationShift";
	private static final String UNIFORM_LOD_MORPH_AREA = "lod_morph_area";
	private static final String UNIFORM_CAMERA_POSITION = "cameraPosition";
	private static final String UNIFORM_VIEW_PROJECTION_MATRIX = "m_ViewProjection";
	
	public PatchedTerrainShader() {
		super();
		this.addVertexShader(VERTEX_SHADER);
		this.addFragmentShader(FRAGMENT_SHADER);
		this.addGeometryShader(GEOMETRY_SHADER);
		this.addTessellationEvaluationShader(TESSELLATION_EVALUATION_SHADER);
		this.addTessellationControlShader(TESSELLATION_CONTROL_SHADER);
		this.compileShader();
	}

	@Override
	protected void bindAttributes() {
		this.bindFragOutput(0, ATTRIBUTE_OUT_COLOR);
		this.bindAttribute(0, ATTRIBUTE_POSITION);	
	}

	@Override
	protected void loadUniformLocations() {
		this.addUniform(UNIFORM_LOCAL_MATRIX);
		this.addUniform(UNIFORM_WORLD_MATRIX);
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
		
		this.addUniform(UNIFORM_CAMERA_POSITION);
		this.addUniform(UNIFORM_VIEW_PROJECTION_MATRIX);		
	}
	
	public void loadProjectionViewMatrix(Matrix4f projectionViewMatrix) {
		this.loadMatrix(UNIFORM_VIEW_PROJECTION_MATRIX, projectionViewMatrix);
	}
	
	public void loadCameraPosition(Vector3f position) {
		this.load3DVector(UNIFORM_CAMERA_POSITION, position);
	}
	
	public void loadPositionMatrix(Matrix4f worldMatrix, Matrix4f localMatrix) {
		this.loadMatrix(UNIFORM_WORLD_MATRIX, worldMatrix);
		this.loadMatrix(UNIFORM_LOCAL_MATRIX, localMatrix);
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

}
