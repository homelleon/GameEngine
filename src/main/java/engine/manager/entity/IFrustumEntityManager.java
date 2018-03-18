package manager.entity;

import java.util.Collection;
import java.util.List;

import object.camera.ICamera;
import object.entity.Entity;
import scene.Scene;
import tool.math.Frustum;
import tool.math.Matrix4f;

/**
 * Provides access to manages frustum entities for render pipeline.
 * 
 * @author homelleon
 * @see FrustumEntityManager
 */
public interface IFrustumEntityManager {
	
	void addEntityInNodes(Entity entity);
	void rebuildNodes(Collection<Entity> entities);
	List<Entity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLowDetail);
	List<Entity> processHighEntities(Scene scene, Matrix4f projectionMatrix, boolean toRebuild);
	List<Entity> processLowEntities(Scene scene, Matrix4f projectionMatrix, boolean toRebuild);
	List<Entity> prepareShadowEntities(Scene scene, Matrix4f shadowMapSpaceMatrix, boolean toRebuild);
	List<Entity> getHighEntities();
	List<Entity> getLowEntities();
	List<Entity> getShadowEntities();
	void clean();

}