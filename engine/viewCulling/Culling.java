package viewCulling;

import cameras.CameraInterface;
import entities.EntityInterface;

public interface Culling {
	
	Boolean getVisibility(EntityInterface entity);
	void update(CameraInterface camera);
	
}
