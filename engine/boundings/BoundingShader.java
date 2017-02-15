package boundings;

import org.lwjgl.util.vector.Matrix4f;

import cameras.Camera;
import scene.ES;
import shaders.ShaderProgram;
import toolbox.Maths;

public class BoundingShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = ES.BOUNDING_SHADER_PATH + "boundVertexShader.txt";
	public static final String FRAGMENT_FILE = ES.BOUNDING_SHADER_PATH + "boundFragmentShader.txt";
	
	private int location_transformationMatrix; //матрица трансформации
	private int location_projectionMatrix; //проективная матрица
	private int location_viewMatrix; //видовая матрица
	
	public BoundingShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");		
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");		
	}
	
	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
