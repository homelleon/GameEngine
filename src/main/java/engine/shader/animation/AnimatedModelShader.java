package shader.animation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import renderer.object.animation.AnimatedModelRenderer;
import shader.ShaderProgram;

public class AnimatedModelShader extends ShaderProgram {

	private static final int MAX_JOINTS = 50;// max number of joints in a
												// skeleton
	private static final int DIFFUSE_TEX_UNIT = 0;

	private static final String VERTEX_SHADER = EngineSettings.SHADERS_ANIMATION_PATH + "animatedEntityVertexShader.glsl";
	private static final String FRAGMENT_SHADER = EngineSettings.SHADERS_ANIMATION_PATH + "animatedEntityFragmentShader.glsl";

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
	}

	@Override
	public void getAllUniformLocations() {
		super.addUniform("projectionViewMatrix");
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
		super.loadMatrix("projectionViewMatrix", projectionViewMatrix);
	}

	public void loadLightDirection(Vector3f direction) {
		super.loadVector("lightDirection", direction);
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
