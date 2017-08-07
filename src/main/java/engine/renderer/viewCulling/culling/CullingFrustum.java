package renderer.viewCulling.culling;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import renderer.viewCulling.frustum.Frustum;

public class CullingFrustum implements Culling {

	private Frustum frustum;

	public CullingFrustum(Frustum frustum) {
		this.frustum = frustum;
	}

	@Override
	public Boolean getVisibility(IEntity entity) {
		Boolean isVisible = false;
		isVisible = this.frustum.sphereInFrustum(entity.getPosition(), entity.getSphereRadius());
		return isVisible;
	}

	@Override
	public void update(ICamera camera) {
		frustum.extractFrustum(camera, camera.getProjectionMatrix());

	}

}
