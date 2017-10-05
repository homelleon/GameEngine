package renderer.main;

import java.util.List;

import object.entity.entity.IEntity;
import object.scene.IScene;
import tool.math.Matrix4f;

public interface IFrustumEntityManager {
	
	List<IEntity> prepareFrustumHighEntities(IScene scene, Matrix4f projectionMatrix);
	List<IEntity> prepareFrustumLowEntities(IScene scene, Matrix4f projectionMatrix);
	List<IEntity> prepareShadowFrustumEntities(IScene scene, Matrix4f shadowMapSpaceMatrix);
	List<IEntity> getFrustumHighEntities();
	List<IEntity> getFrustumLowEntities();
	List<IEntity> getFrustumShadowEntities();
	void clean();

}