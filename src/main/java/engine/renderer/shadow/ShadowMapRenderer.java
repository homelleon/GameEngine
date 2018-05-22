package renderer.shadow;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import core.settings.EngineSettings;
import object.camera.Camera;
import object.entity.Entity;
import object.shadow.ShadowBox;
import object.shadow.ShadowFrameBuffer;
import object.terrain.Terrain;
import primitive.model.Model;
import primitive.texture.Texture2D;
import scene.Scene;
import shader.shadow.ShadowShader;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class ShadowMapRenderer {

	private ShadowFrameBuffer shadowFbo;
	private ShadowShader shader;
	private ShadowBox shadowBox;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f lightViewMatrix = new Matrix4f();
	private Matrix4f projectionViewMatrix = new Matrix4f();
	private Matrix4f offset = createOffset();

	private ShadowMapEntityRenderer shadowEntityRenderer;

	public ShadowMapRenderer(Camera camera) {
		shader = new ShadowShader();
		shadowBox = new ShadowBox(lightViewMatrix, camera);
		shadowFbo = new ShadowFrameBuffer(EngineSettings.SHADOW_MAP_SIZE, EngineSettings.SHADOW_MAP_SIZE);
		shadowEntityRenderer = new ShadowMapEntityRenderer(shader, projectionViewMatrix);
	}

	public void render(Map<Model, List<Entity>> entities, Map<Model, List<Entity>> normalEntities, Collection<Terrain> terrains, Scene scene) {
		shadowBox.update();
		Vector3f sunPosition = scene.getSun().getPosition();
		Vector3f lightDirection = new Vector3f(-sunPosition.x, -sunPosition.y, -sunPosition.z);
		prepare(lightDirection, shadowBox);
		shadowEntityRenderer.render(entities, scene.getCamera());
		shadowEntityRenderer.render(normalEntities, scene.getCamera());
		//shadowTerrainRenderer.render(terrains);
		finish();
	}

	public Matrix4f getToShadowMapMatrix() {
		return Matrix4f.mul(offset, projectionViewMatrix);
	}

	public void clean() {
		shader.clean();
		shadowFbo.cleanUp();
	}

	public Texture2D getShadowMap() {
		return shadowFbo.getShadowMap();
	}

	protected Matrix4f getLightSpaceTransform() {
		return lightViewMatrix;
	}
	
	private void prepare(Vector3f lightDirection, ShadowBox box) {
		updateOrthoProjectionMatrix(box.getWidth(), box.getHeight(), box.getLength());
		updateLightViewMatrix(lightDirection, box.getCenter());
		Matrix4f.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);
		shadowFbo.bindFrameBuffer();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
	}
	
	private void finish() {
		shader.stop();
		shadowFbo.unbindFrameBuffer();
	}

	private void updateLightViewMatrix(Vector3f direction, Vector3f center) {
		direction.normalize();
		center.negate();
		lightViewMatrix.setIdentity();
		float pitch = (float) Math.acos(new Vector2f(direction.x, direction.z).length());
		lightViewMatrix.rotate(pitch, new Vector3f(1, 0, 0));
		float yaw = (float) Math.toDegrees(((float) Math.atan(direction.x / direction.z)));
		yaw = direction.z > 0 ? yaw - 180 : yaw;
		lightViewMatrix.rotate((float) -Math.toRadians(yaw), new Vector3f(0, 1, 0));
		lightViewMatrix.translate(center);
	}

	private void updateOrthoProjectionMatrix(float width, float height, float length) {
		projectionMatrix.setIdentity();
		projectionMatrix.m[0][0] = 2f / width;
		projectionMatrix.m[1][1] = 2f / height;
		projectionMatrix.m[2][2] = -2f / length;
		projectionMatrix.m[3][3] = 1;
	}

	private static Matrix4f createOffset() {		
		Matrix4f offset = new Matrix4f();
		offset.translate(new Vector3f(0.5f, 0.5f, 0.5f));
		offset.scale(new Vector3f(0.5f, 0.5f, 0.5f));
		return offset;
	}
}
