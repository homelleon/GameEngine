package renderer.object.shadow;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.light.Light;
import object.shadow.ShadowBox;
import object.shadow.ShadowFrameBuffer;
import object.terrain.terrain.ITerrain;
import object.texture.Texture2D;
import primitive.model.Model;
import shader.shadow.ShadowShader;
import tool.math.VMatrix4f;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3fF;

/**
 * This class is in charge of using all of the classes in the shadows package to
 * carry out the shadow render pass, i.e. rendering the scene to the shadow map
 * texture. This is the only class in the shadows package which needs to be
 * referenced from outside the shadows package.
 * 
 * @author Karl
 *
 */
public class ShadowMapMasterRenderer {

	private ShadowFrameBuffer shadowFbo;
	private ShadowShader shader;
	private ShadowBox shadowBox;
	private VMatrix4f projectionMatrix = new VMatrix4f();
	private VMatrix4f lightViewMatrix = new VMatrix4f();
	private VMatrix4f projectionViewMatrix = new VMatrix4f();
	private VMatrix4f offset = createOffset();

	private ShadowMapEntityRenderer shadowEntityRenderer;
	private ShadowMapTerrainRenderer shadowTerrainRenderer;

	/**
	 * Creates instances of the important objects needed for rendering the scene
	 * to the shadow map. This includes the {@link ShadowBox} which calculates
	 * the position and size of the "view cuboid", the simple renderer and
	 * shader program that are used to render objects to the shadow map, and the
	 * {@link ShadowFrameBuffer} to which the scene is rendered. The size of the
	 * shadow map is determined here.
	 * 
	 * @param camera
	 *            - the camera being used in the scene.
	 */
	public ShadowMapMasterRenderer(ICamera camera) {
		shader = new ShadowShader();
		shadowBox = new ShadowBox(lightViewMatrix, camera);
		shadowFbo = new ShadowFrameBuffer(EngineSettings.SHADOW_MAP_SIZE, EngineSettings.SHADOW_MAP_SIZE);
		shadowEntityRenderer = new ShadowMapEntityRenderer(shader, projectionViewMatrix);
		shadowTerrainRenderer = new ShadowMapTerrainRenderer(shader, projectionViewMatrix);
	}

	/**
	 * Carries out the shadow render pass. This renders the entities to the
	 * shadow map. First the shadow box is updated to calculate the size and
	 * position of the "view cuboid". The light direction is assumed to be
	 * "-lightPosition" which will be fairly accurate assuming that the light is
	 * very far from the scene. It then prepares to render, renders the entities
	 * to the shadow map, and finishes rendering.
	 * 
	 * @param entities
	 *            - the lists of entities to be rendered. Each list is
	 *            associated with the {@link Model} that all of the
	 *            entities in that list use.
	 * @param sun
	 *            - the light acting as the sun in the scene.
	 */
	public void render(Map<Model, List<IEntity>> entities, Collection<ITerrain> terrains,
			Map<Model, List<IEntity>> normalMapEntities, Light sun, ICamera camera) {
		shadowBox.update();
		Vector3fF sunPosition = sun.getPosition();
		Vector3fF lightDirection = new Vector3fF(-sunPosition.x, -sunPosition.y, -sunPosition.z);
		prepare(lightDirection, shadowBox);
		entities.putAll(normalMapEntities);
		shadowEntityRenderer.render(entities);
		shadowTerrainRenderer.render(terrains);
		finish();
	}

	/**
	 * This biased projection-view matrix is used to convert fragments into
	 * "shadow map space" when rendering the main render pass. It converts a
	 * world space position into a 2D coordinate on the shadow map. This is
	 * needed for the second part of shadow mapping.
	 * 
	 * @return The to-shadow-map-space matrix.
	 */
	public VMatrix4f getToShadowMapSpaceMatrix() {
		return VMatrix4f.mul(offset, projectionViewMatrix);
	}

	/**
	 * Clean up the shader and FBO on closing.
	 */
	public void clean() {
		shader.clean();
		shadowFbo.cleanUp();
	}

	/**
	 * @return The ID of the shadow map texture. The ID will always stay the
	 *         same, even when the contents of the shadow map texture change
	 *         each frame.
	 */
	public Texture2D getShadowMap() {
		return shadowFbo.getShadowMap();
	}

	/**
	 * @return The light's "view" matrix.
	 */
	protected VMatrix4f getLightSpaceTransform() {
		return lightViewMatrix;
	}

	/**
	 * Prepare for the shadow render pass. This first updates the dimensions of
	 * the orthographic "view cuboid" based on the information that was
	 * calculated in the {@link SHadowBox} class. The light's "view" matrix is
	 * also calculated based on the light's direction and the center position of
	 * the "view cuboid" which was also calculated in the {@link ShadowBox}
	 * class. These two matrices are multiplied together to create the
	 * projection-view matrix. This matrix determines the size, position, and
	 * orientation of the "view cuboid" in the world. This method also binds the
	 * shadows FBO so that everything rendered after this gets rendered to the
	 * FBO. It also enables depth testing, and clears any data that is in the
	 * FBOs depth attachment from last frame. The simple shader program is also
	 * started.
	 * 
	 * @param lightDirection
	 *            - the direction of the light rays coming from the sun.
	 * @param box
	 *            - the shadow box, which contains all the info about the "view
	 *            cuboid".
	 */
	private void prepare(Vector3fF lightDirection, ShadowBox box) {
		updateOrthoProjectionMatrix(box.getWidth(), box.getHeight(), box.getLength());
		updateLightViewMatrix(lightDirection, box.getCenter());
		projectionViewMatrix = VMatrix4f.mul(projectionMatrix, lightViewMatrix);
		shadowFbo.bindFrameBuffer();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
	}

	/**
	 * Finish the shadow render pass. Stops the shader and unbinds the shadow
	 * FBO, so everything rendered after this point is rendered to the screen,
	 * rather than to the shadow FBO.
	 */
	private void finish() {
		shader.stop();
		shadowFbo.unbindFrameBuffer();
	}

	/**
	 * Updates the "view" matrix of the light. This creates a view matrix which
	 * will line up the direction of the "view cuboid" with the direction of the
	 * light. The light itself has no position, so the "view" matrix is centered
	 * at the center of the "view cuboid". The created view matrix determines
	 * where and how the "view cuboid" is positioned in the world. The size of
	 * the view cuboid, however, is determined by the projection matrix.
	 * 
	 * @param direction
	 *            - the light direction, and therefore the direction that the
	 *            "view cuboid" should be pointing.
	 * @param center
	 *            - the center of the "view cuboid" in world space.
	 */
	private void updateLightViewMatrix(Vector3fF direction, Vector3fF center) {
		direction.normalize();
		center.negate();
		lightViewMatrix.setIdentity();
		float pitch = (float) Math.acos(new Vector2f(direction.x, direction.z).length());
		lightViewMatrix.rotate(pitch, new Vector3fF(1, 0, 0));
		float yaw = (float) Math.toDegrees(((float) Math.atan(direction.x / direction.z)));
		yaw = direction.z > 0 ? yaw - 180 : yaw;
		lightViewMatrix.rotate((float) -Math.toRadians(yaw), new Vector3fF(0, 1, 0));
		lightViewMatrix.translate(center);
	}

	/**
	 * Creates the orthographic projection matrix. This projection matrix
	 * basically sets the width, length and height of the "view cuboid", based
	 * on the values that were calculated in the {@link ShadowBox} class.
	 * 
	 * @param width
	 *            - shadow box width.
	 * @param height
	 *            - shadow box height.
	 * @param length
	 *            - shadow box length.
	 */
	private void updateOrthoProjectionMatrix(float width, float height, float length) {
		projectionMatrix.setIdentity();
		projectionMatrix.m[0][0] = 2f / width;
		projectionMatrix.m[1][1] = 2f / height;
		projectionMatrix.m[2][2] = -2f / length;
		projectionMatrix.m[3][3] = 1;
	}

	/**
	 * Create the offset for part of the conversion to shadow map space. This
	 * conversion is necessary to convert from one coordinate system to the
	 * coordinate system that we can use to sample to shadow map.
	 * 
	 * @return The offset as a matrix (so that it's easy to apply to other
	 *         matrices).
	 */
	private static VMatrix4f createOffset() {
		VMatrix4f offset = new VMatrix4f();
		offset.translate(new Vector3fF(0.5f, 0.5f, 0.5f));
		offset.scale(new Vector3fF(0.5f, 0.5f, 0.5f));
		return offset;
	}
}
