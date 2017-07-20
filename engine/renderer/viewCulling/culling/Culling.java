package renderer.viewCulling.culling;

import object.camera.CameraInterface;
import object.entity.entity.Entity;

public interface Culling {
	
	Boolean getVisibility(Entity entity);
	void update(CameraInterface camera);
	
}
