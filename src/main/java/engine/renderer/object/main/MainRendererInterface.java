package renderer.object.main;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.light.Light;
import object.model.TexturedModel;
import object.scene.scene.SceneInterface;
import object.terrain.terrain.Terrain;
import renderer.object.entity.EntityRenderer;
import renderer.viewCulling.frustum.Frustum;

/**
 * Main interface of render Engine that contatins scene, environment and shadow
 * rendering system to control them and send scene objects in it.
 * 
 * @author homelleon
 * @version 1.0
 * @see MainRenderer
 *
 */
public interface MainRendererInterface {

	/**
	 * Rendering all objects of scene.
	 * <p>
	 * Can define if scene is rendering using low or hight quality objects by
	 * 'isLowDistance' parameter.
	 * 
	 * @param scene
	 *            {@link SceneInterface} value
	 * @param clipPlane
	 *            {@link Vector4f} value of clipping plane matrix
	 * @param isLowDistance
	 *            {@link Boolean} value to define if low quality scene need to
	 *            be rendered
	 */
	public void renderScene(SceneInterface scene, Vector4f clipPlane, boolean isLowDistance);

	/**
	 * Method renders all object of the scene.
	 * 
	 * @param scene
	 *            {@link SceneInterface} value
	 * @param clipPlane
	 *            {@link Vector4f} value of clipping plane matrix
	 */
	public void renderScene(SceneInterface scene, Vector4f clipPlane);

	/**
	 * 
	 * @param scene
	 *            {@link SceneInterface} value
	 */
	public void renderShadowMap(SceneInterface scene);

	/**
	 * Method cleans all shaders and rendering objects.
	 */
	public void clean();

	/**
	 * 
	 * @return
	 */
	public EntityRenderer getEntityRenderer();

	/**
	 * 
	 * @param entitiyWiredFrame
	 */
	public void setEntityWiredFrame(boolean entitiyWiredFrame);

	/**
	 * 
	 * @param terrainWiredFrame
	 */
	public void setTerrainWiredFrame(boolean terrainWiredFrame);

	/**
	 * 
	 * @param entities
	 * @param terrains
	 * @param lights
	 * @param camera
	 */
	void renderLowQualityScene(Map<TexturedModel, List<Entity>> entities, Collection<Terrain> terrains,
			Collection<Light> lights, CameraInterface camera);

	/**
	 * 
	 * @return
	 */
	Matrix4f getProjectionMatrix();

	/**
	 * 
	 * @param scene
	 * @return
	 */
	Collection<Entity> createFrustumEntities(SceneInterface scene);

	/**
	 * 
	 * @return
	 */
	Frustum getFrustum();

}
