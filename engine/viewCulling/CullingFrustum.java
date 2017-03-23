package viewCulling;

import cameras.Camera;
import entities.Entity;

public class CullingFrustum implements Culling {
	
	private Frustum frustum;
	
	public CullingFrustum(Frustum frustum) {
		this.frustum = frustum;
	}

	@Override
	public Boolean getVisibility(Entity entity) {
		Boolean isVisible = false;
		isVisible = this.frustum.sphereInFrustum(entity.getPosition(), 
				entity.getSphereRadius());		
		return isVisible;
	}

	@Override
	public void update(Camera camera) {
		frustum.extractFrustum(camera, camera.getProjectionMatrix());
		
	}

}
