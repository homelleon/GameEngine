package viewCulling;

import objects.cameras.CameraInterface;
import objects.entities.EntityInterface;

public interface Culling {
	
	Boolean getVisibility(EntityInterface entity);
	void update(CameraInterface camera);
	
}
