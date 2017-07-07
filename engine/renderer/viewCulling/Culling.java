package renderer.viewCulling;

import object.camera.CameraInterface;
import object.entity.entity.EntityInterface;

public interface Culling {
	
	Boolean getVisibility(EntityInterface entity);
	void update(CameraInterface camera);
	
}
