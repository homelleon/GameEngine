package voxels;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import cameras.Camera;
import entities.Light;
import scene.ES;
import shaders.ShaderProgram;
import toolbox.Maths;

public class VoxelShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = ES.VOXEL_SHADER_PATH + "voxelVertexShader.txt";
	public static final String FRAGMENT_FILE = ES.VOXEL_SHADER_PATH + "voxelFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_modelTexture;
	private int location_numberOfRows;
	private int location_offset;
	private int location_lightCount;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];

	public VoxelShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_modelTexture = super.getUniformLocation("modelTexture");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_lightCount = super.getUniformLocation("lightCount");
		
		location_lightCount = super.getUniformLocation("lightCount");
		location_lightPosition = new int[ES.MAX_LIGHTS];
		location_lightColour = new int[ES.MAX_LIGHTS];
		location_attenuation = new int[ES.MAX_LIGHTS];
		for(int i = 0; i < ES.MAX_LIGHTS; i++){
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");			
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_modelTexture, 0);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	public void loadOffset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x,y));
	}
	
	public void loadLights(Collection<Light> lights) {
		super.loadInt(location_lightCount, ES.MAX_LIGHTS);
		Iterator iterator = lights.iterator();
		for(int i=0; i<ES.MAX_LIGHTS; i++) {
			if(iterator.hasNext()) {
				Light light = (Light) iterator.next();
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

}
