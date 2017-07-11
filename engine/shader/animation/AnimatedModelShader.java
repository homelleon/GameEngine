package shader.animation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.light.Light;
import renderer.object.animation.AnimatedModelRenderer;
import shader.ShaderProgram;

public class AnimatedModelShader extends ShaderProgram {

	private static final int MAX_JOINTS = 50;// max number of joints in a skeleton
	private static final int DIFFUSE_TEX_UNIT = 0;

	private static final String VERTEX_SHADER = EngineSettings.SHADERS_ANIMATION_PATH + "animatedEntityVertex.glsl";
	private static final String FRAGMENT_SHADER = EngineSettings.SHADERS_ANIMATION_PATH + "animatedEntityFragment.glsl";
	
	private int location_projectionViewMatrix;
	private int location_lightDirection;
	private int location_jointTransforms[];
	private int location_diffuseMap;

	/**
	 * Creates the shader program for the {@link AnimatedModelRenderer} by
	 * loading up the vertex and fragment shader code files. It also gets the
	 * location of all the specified uniform variables, and also indicates that
	 * the diffuse texture will be sampled from texture unit 0.
	 */
	public AnimatedModelShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);		
		connectTextureUnits();
	}
	
	@Override
	protected void getAllUniformLocations() {
		this.location_projectionViewMatrix = super.getUniformLocation("projectionViewMatrix");
		this.location_lightDirection = super.getUniformLocation("lightDirection");		
		this.location_diffuseMap = super.getUniformLocation("diffuseMap");
		for(int i=0;i<EngineSettings.MAX_LIGHTS;i++){
			this.location_jointTransforms[i] = super.getUniformLocation("jointTransforms[" + i + "]");
		}
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoordinates");
		super.bindAttribute(2, "in_normal");
		super.bindAttribute(3, "in_jointIndices");
		super.bindAttribute(4, "in_weights");
	}
	
	public void loadprojectionViewMatrix(Matrix4f projectionViewMatrix) {
		super.loadMatrix(location_projectionViewMatrix, projectionViewMatrix);
	}
	
	public void loadLightDirection(Vector3f direction) {
		super.loadVector(location_lightDirection, direction);
	}
	
	private void loadDisffuseMap() {
		super.loadInt(location_diffuseMap, DIFFUSE_TEX_UNIT);		
	}
	
	public void loadJointTransforms(Matrix4f[] jointTransforms) {
		for(int i = 0; i < MAX_JOINTS; i++) {
			super.loadMatrix(location_jointTransforms[i], jointTransforms[i]);			
		}
	}

	/**
	 * Indicates which texture unit the diffuse texture should be sampled from.
	 */
	private void connectTextureUnits() {
		super.start();
		loadDisffuseMap();
		super.stop();
	}	

}
