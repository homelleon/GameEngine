package manager.entity;

import java.util.Collection;
import java.util.List;

import object.camera.ICamera;
import object.entity.entity.IEntity;
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
	
	void addEntityInNodes(IEntity entity);
	void rebuildNodes(Collection<IEntity> entities);
	List<IEntity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLowDetail);
	List<IEntity> processHighEntities(Scene scene, Matrix4f projectionMatrix, boolean toRebuild);
	List<IEntity> processLowEntities(Scene scene, Matrix4f projectionMatrix, boolean toRebuild);
	List<IEntity> prepareShadowEntities(Scene scene, Matrix4f shadowMapSpaceMatrix, boolean toRebuild);
	List<IEntity> getHighEntities();
	List<IEntity> getLowEntities();
	List<IEntity> getShadowEntities();
	void clean();

}