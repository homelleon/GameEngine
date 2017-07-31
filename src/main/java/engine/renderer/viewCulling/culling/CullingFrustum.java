package renderer.viewCulling.culling;

import object.camera.CameraInterface;
import object.entity.entity.Entity;
import renderer.viewCulling.frustum.Frustum;

public class CullingFrustum implements Culling {

	private Frustum frustum;

	public CullingFrustum(Frustum frustum) {
		this.frustum = frustum;
	}

	@Override
	public Boolean getVisibility(Entity entity) {
		Boolean isVisible = false;
		isVisible = this.frustum.sphereInFrustum(entity.getPosition(), entity.getSphereRadius());
		return isVisible;
	}

	@Override
	public void update(CameraInterface camera) {
		frustum.extractFrustum(camera, camera.getProjectionMatrix());

	}

}
