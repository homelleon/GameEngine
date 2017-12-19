package manager.entity;

import java.util.Collection;
import java.util.List;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import scene.IScene;
import tool.math.Frustum;
import tool.math.Matrix4f;

/**
 * Provides access to manages frustum entities for render pipeline.
 * 
 * @author homelleon
 * @see FrustumEntityManager
 */
public interface IFrustumEntityManager {
	
	void addEntityInNodes(IEntity entity);
	void rebuildNodes(Collection<IEntity> entities, int size);
	List<IEntity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLowDetail);
	List<IEntity> prepareFrustumHighEntities(IScene scene, Matrix4f projectionMatrix);
	List<IEntity> prepareFrustumLowEntities(IScene scene, Matrix4f projectionMatrix);
	List<IEntity> prepareShadowFrustumEntities(IScene scene, Matrix4f shadowMapSpaceMatrix);
	List<IEntity> getFrustumHighEntities();
	List<IEntity> getFrustumLowEntities();
	List<IEntity> getFrustumShadowEntities();
	void clean();

}