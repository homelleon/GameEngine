package voxels;

import org.lwjgl.util.vector.Matrix4f;

import cameras.Camera;
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

	public VoxelShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_modelTexture = super.getUniformLocation("modelTexture");
	}

	@Override
	protected void bindAttributes() {
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

}
