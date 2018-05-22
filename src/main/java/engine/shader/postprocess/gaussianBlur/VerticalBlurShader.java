package shader.postprocess.gaussianBlur;

import core.settings.EngineSettings;
import shader.Shader;

public class VerticalBlurShader extends Shader {
	
	//----shaders
	private static final String VERTEX_FILE = EngineSettings.SHADERS_BLUR_PATH + "verticalBlur_V_shader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_BLUR_PATH + "blur_F_shader.glsl";
	//----attributes
	private static final String ATTRIBUTE_POSITION = "in_position";
	//----uniforms
	private static final String UNIFORM_TARGET_HEIGHT = "targetHeight";
	private static final String UNIFORM_SIGMA_ARRAY = "sigmas";
	
	private final float[] sigmas = calculateSigmas();
	
	public VerticalBlurShader() {
		super(Shader.POST_BLUR_V);
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}
	
	private static float[] calculateSigmas() {
		return new float[] {
			0.0093f,
			0.028002f,
			0.065984f,
			0.121703f,
			0.175713f,
			0.198596f,
			0.175713f,
			0.121703f,
			0.065984f,
			0.028002f,
			0.0093f
		};
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, ATTRIBUTE_POSITION);
	}

	@Override
	protected void loadUniformLocations() {
		addUniform(UNIFORM_TARGET_HEIGHT);
		for (int i = 0; i <= 10; i++) {
			addUniform(UNIFORM_SIGMA_ARRAY + "[" + i + "]");
		}
	}

	protected void loadTargetHeight(float height) {
		loadFloat(UNIFORM_TARGET_HEIGHT, height);
		
		for (int i = 0; i <= 10; i++) {
			loadFloat(UNIFORM_SIGMA_ARRAY + "[" + i +"]", sigmas[i]);
		}
	}
}
