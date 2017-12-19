package shader.animation;

import core.settings.EngineSettings;
import renderer.animation.AnimatedModelRenderer;
import shader.ShaderProgram;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class AnimatedModelShader extends ShaderProgram {

	private static final int MAX_JOINTS = 50;// max number of joints in a
												// skeleton
	private static final int DIFFUSE_TEX_UNIT = 0;

	private static final String VERTEX_SHADER = EngineSettings.SHADERS_ANIMATION_PATH + "animatedEntity_V_shader.glsl";
	private static final String FRAGMENT_SHADER = EngineSettings.SHADERS_ANIMATION_PATH + "animatedEntity_F_shader.glsl";

	/**
	 * Creates the shader program for the {@link AnimatedModelRenderer} by
	 * loading up the vertex and fragment shader code files. It also gets the
	 * location of all the specified uniform variables, and also indicates that
	 * the diffuse texture will be sampled from texture unit 0.
	 */
	public AnimatedModelShader() {
		super();
		addVertexShader(VERTEX_SHADER);
		addFragmentShader(FRAGMENT_SHADER);
		compileShader();
		connectTextureUnits();
	}

	@Override
	public void loadUniformLocations() {
		super.addUniform("ProjectionViewMatrix");
		super.addUniform("lightDirection");
		super.addUniform("diffuseMap");
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			super.addUniform("jointTransforms[" + i + "]");
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
		super.loadMatrix("ProjectionViewMatrix", projectionViewMatrix);
	}

	public void loadLightDirection(Vector3f direction) {
		super.load3DVector("lightDirection", direction);
	}

	private void loadDisffuseMap() {
		super.loadInt("diffuseMap", DIFFUSE_TEX_UNIT);
	}

	public void loadJointTransforms(Matrix4f[] jointTransforms) {
		for (int i = 0; i < MAX_JOINTS; i++) {
			super.loadMatrix("jointTransforms["+i+"]", jointTransforms[i]);
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
