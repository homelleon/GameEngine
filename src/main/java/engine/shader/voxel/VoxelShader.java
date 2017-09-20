package shader.voxel;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;

public class VoxelShader extends ShaderProgram {

	public static final String VERTEX_FILE = EngineSettings.SHADERS_VOXEL_PATH + "voxelVertexShader.glsl";
	public static final String FRAGMENT_FILE = EngineSettings.SHADERS_VOXEL_PATH + "voxelFragmentShader.glsl";

	public VoxelShader() {
		super();
		addVertexShader(VERTEX_FILE);
		addFragmentShader(FRAGMENT_FILE);
		compileShader();
	}

	@Override
	protected void bindAttributes() {
		super.bindFragOutput(0, "out_Color");
		super.bindFragOutput(1, "out_BrightColor");
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("transformationMatrix");
		super.addUniform("projectionMatrix");
		super.addUniform("viewMatrix");
		super.addUniform("shineDamper");
		super.addUniform("reflectivity");
		super.addUniform("useFakeLighting");
		super.addUniform("skyColour");
		super.addUniform("numberOfRows");
		super.addUniform("offset");
		super.addUniform("plane");
		super.addUniform("fogDensity");
		super.addUniform("toShadowMapSpace");
		super.addUniform("shadowMap");
		super.addUniform("shadowDistance");
		super.addUniform("shadowMapSize");
		super.addUniform("shadowTransitionDistance");
		super.addUniform("shadowPCFCount");
		super.addUniform("specularMap");
		super.addUniform("usesSpecularMap");
		super.addUniform("modelTexture");

		super.addUniform("lightCount");
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			super.addUniform("lightPosition[" + i + "]");
			super.addUniform("lightColour[" + i + "]");
			super.addUniform("attenuation[" + i + "]");
		}
	}

	public void connectTextureUnits() {
		super.loadInt("modelTexture", 0);
		super.loadInt("specularMap", 1);
		super.loadInt("shadowMap", 5);
	}

	public void loadUsesSpecularMap(boolean useMap) {
		super.loadBoolean("usesSpecularMap", useMap);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat("shadowDistance", shadowDistance);
		super.loadFloat("shadowMapSize", size);
		super.loadFloat("shadowTransitionDistance", transitionDistance);
		super.loadInt("shadowPCFCount", pcfCount);
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix("toShadowMapSpace", matrix);
	}

	public void loadClipPlane(Vector4f plane) {
		super.load4DVector("plane", plane);
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector("skyColour", new Vec3f(r, g, b));
	}

	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean("useFakeLighting", useFake);
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat("shineDamper", damper);
		super.loadFloat("reflectivity", reflectivity);
	}

	public void loadFogDensity(float density) {
		super.loadFloat("fogDensity", density);
	}

	public void loadViewMatrix(ICamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix("viewMatrix", viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix("projectionMatrix", projection);
	}

	public void loadTranformationMatrix(Matrix4f matrix) {
		super.loadMatrix("transformationMatrix", matrix);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat("numberOfRows", numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector("offset", new Vec2f(x, y));
	}

	public void loadLights(Collection<ILight> lights) {
		super.loadInt("lightCount", EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.loadVector("lightPosition[" + i + "]", light.getPosition());
				super.loadVector("lightColour[" + i + "]", light.getColour());
				super.loadVector("attenuation[" + i + "]", light.getAttenuation());
			} else {
				super.loadVector("lightPosition[" + i + "]", new Vec3f(0, 0, 0));
				super.loadVector("lightColour[" + i + "]", new Vec3f(0, 0, 0));
				super.loadVector("attenuation[" + i + "]", new Vec3f(1, 0, 0));
			}
		}

	}

}
