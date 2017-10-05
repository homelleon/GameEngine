package renderer.main;

import java.util.ArrayList;
import java.util.List;

import object.entity.entity.IEntity;
import object.scene.IScene;
import tool.math.Frustum;
import tool.math.Matrix4f;

public class FrustumEntityManager implements IFrustumEntityManager {
	
	private Frustum frustum;
	private List<IEntity> frustumHighEntities = new ArrayList<IEntity>();
	private List<IEntity> frustumLowEntities = new ArrayList<IEntity>();
	private List<IEntity> frustumShadowEntities = new ArrayList<IEntity>();
	
	public FrustumEntityManager(Frustum frustum) {
		this.frustum = frustum;
	}

	@Override
	public List<IEntity> prepareFrustumHighEntities(IScene scene, Matrix4f projectionMatrix) {
		if(scene.getCamera().isMoved() || scene.getCamera().isRotated()) {
			Matrix4f projectionViewMatrix = Matrix4f.mul(projectionMatrix, scene.getCamera().getViewMatrix());	
			this.frustum.extractFrustum(projectionViewMatrix);
			this.frustumHighEntities.clear();
			this.frustumHighEntities = scene.getEntities().updateWithFrustum(this.frustum, scene.getCamera(), false);
		}
		return this.frustumHighEntities;
	}
	
	@Override
	public List<IEntity> prepareFrustumLowEntities(IScene scene, Matrix4f projectionMatrix) {
		if(scene.getCamera().isMoved() || scene.getCamera().isRotated()) {
			Matrix4f projectionViewMatrix = Matrix4f.mul(projectionMatrix, scene.getCamera().getViewMatrix());	
			this.frustum.extractFrustum(projectionViewMatrix);
			this.frustumLowEntities.clear();
			this.frustumLowEntities = scene.getEntities().updateWithFrustum(this.frustum, scene.getCamera(), true);
		}
		return this.frustumLowEntities;
	}

	@Override
	public List<IEntity> prepareShadowFrustumEntities(IScene scene, Matrix4f shadowMapSpaceMatrix) {
		if(scene.getCamera().isMoved() || scene.getCamera().isRotated()) {
			this.frustum.extractFrustum(shadowMapSpaceMatrix);
			this.frustumShadowEntities.clear();
			this.frustumShadowEntities = scene.getEntities().updateWithFrustum(this.frustum, scene.getCamera(), false);
		}
		return this.frustumShadowEntities;
	}

	@Override
	public List<IEntity> getFrustumHighEntities() {
		return this.frustumHighEntities;
	}
	
	@Override
	public List<IEntity> getFrustumLowEntities() {
		return this.frustumLowEntities;
	}

	@Override
	public List<IEntity> getFrustumShadowEntities() {
		return this.frustumShadowEntities;
	}

	@Override
	public void clean() {
		this.frustumHighEntities.clear();
		this.frustumLowEntities.clear();
		this.frustumShadowEntities.clear();
	}

}
