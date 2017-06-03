package renderers.viewCulling;

import objects.cameras.CameraInterface;
import objects.entities.EntityInterface;

public class CullingFrustum implements Culling {
	
	private Frustum frustum;
	
	public CullingFrustum(Frustum frustum) {
		this.frustum = frustum;
	}

	@Override
	public Boolean getVisibility(EntityInterface entity) {
		Boolean isVisible = false;
		isVisible = this.frustum.sphereInFrustum(entity.getPosition(), 
				entity.getSphereRadius());		
		return isVisible;
	}

	@Override
	public void update(CameraInterface camera) {
		frustum.extractFrustum(camera, camera.getProjectionMatrix());
		
	}

}