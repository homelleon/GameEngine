package shader.entity.normal;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector4f;

import core.settings.EngineSettings;
import object.light.ILight;
import shader.ShaderProgram;
import tool.math.VMatrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3fF;

public class NormalMappedEntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = EngineSettings.SHADERS_ENTITY_NORMAL_PATH + "normalEntityVertexShader.glsl";
	private static final String FRAGMENT_FILE = EngineSettings.SHADERS_ENTITY_NORMAL_PATH + "normalEntityFragmentShader.glsl";

	public NormalMappedEntityShader() {
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
		super.bindAttribute(3, "tangent");
	}

	@Override
	protected void loadUniformLocations() {
		super.addUniform("transformationMatrix");
		super.addUniform("projectionMatrix");
		super.addUniform("viewMatrix");
		super.addUniform("shineDamper");
		super.addUniform("reflectivity");
		super.addUniform("skyColour");
		super.addUniform("numberOfRows");
		super.addUniform("offset");
		super.addUniform("plane");
		super.addUniform("modelTexture");
		super.addUniform("fogDensity");
		super.addUniform("normalMap");
		super.addUniform("toShadowMapSpace");
		super.addUniform("shadowDistance");
		super.addUniform("shadowMap");
		super.addUniform("shadowMapSize");
		super.addUniform("shadowTransitionDistance");
		super.addUniform("shadowPCFCount");
		super.addUniform("specularMap");
		super.addUniform("usesSpecularMap");
		super.addUniform("isChosen");

		super.addUniform("lightCount");
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			super.addUniform("lightPositionEyeSpace[" + i + "]");
			super.addUniform("lightColour[" + i + "]");
			super.addUniform("attenuation[" + i + "]");
		}
	}

	public void loadToShadowSpaceMatrix(VMatrix4f matrix) {
		super.loadMatrix("toShadowMapSpace", matrix);
	}

	public void loadUseSpecularMap(boolean useMap) {
		super.loadBoolean("usesSpecularMap", useMap);
	}

	public void loadFogDensity(float density) {
		super.loadFloat("fogDensity", density);
	}

	public void loadShadowVariables(float shadowDistance, float size, float transitionDistance, int pcfCount) {
		super.loadFloat("shadowDistance", shadowDistance);
		super.loadFloat("shadowMapSize", size);
		super.loadFloat("shadowTransitionDistance", transitionDistance);
		super.loadInt("shadowPCFCount", pcfCount);
	}

	public void connectTextureUnits() {
		super.loadInt("modelTexture", 0);
		super.loadInt("specularMap", 1);
		super.loadInt("normalMap", 2);
		super.loadInt("shadowMap", 5);
	}

	public void loadManipulationVariables(boolean isChosen) {
		super.loadBoolean("isChosen", isChosen);
	}

	public void loadClipPlane(Vector4f plane) {
		super.load4DVector("plane", plane);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat("numberOfRows", numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector("offset", new Vector2f(x, y));
	}

	public void loadSkyColour(float r, float g, float b) {
		super.loadVector("skyColour", new Vector3fF(r, g, b));
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat("shineDamper", damper);
		super.loadFloat("reflectivity", reflectivity);
	}

	public void loadTransformationMatrix(VMatrix4f matrix) {
		super.loadMatrix("transformationMatrix", matrix);
	}

	public void loadLights(Collection<ILight> lights, VMatrix4f viewMatrix) {
		super.loadInt("lightCount", EngineSettings.MAX_LIGHTS);
		Iterator<ILight> iterator = lights.iterator();
		for (int i = 0; i < EngineSettings.MAX_LIGHTS; i++) {
			if (iterator.hasNext()) {
				ILight light = iterator.next();
				super.loadVector("lightPositionEyeSpace[" + i + "]", getEyeSpacePosition(light, viewMatrix));
				super.loadVector("lightColour[" + i + "]", light.getColour());
				super.loadVector("attenuation[" + i + "]", light.getAttenuation());
			} else {
				super.loadVector("lightPositionEyeSpace[" + i + "]", new Vector3fF(0, 0, 0));
				super.loadVector("lightColour[" + i + "]", new Vector3fF(0, 0, 0));
				super.loadVector("attenuation[" + i + "]", new Vector3fF(1, 0, 0));
			}
		}
	}

	public void loadViewMatrix(VMatrix4f viewMatrix) {
		super.loadMatrix("viewMatrix", viewMatrix);
	}

	public void loadProjectionMatrix(VMatrix4f projection) {
		super.loadMatrix("projectionMatrix", projection);
	}

	private Vector3fF getEyeSpacePosition(ILight light, VMatrix4f viewMatrix) {
		Vector3fF position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.x, position.y, position.z, 1f);
		eyeSpacePos = viewMatrix.transform(eyeSpacePos);
		return new Vector3fF(eyeSpacePos);
	}

}
