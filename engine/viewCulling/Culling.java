package viewCulling;

import cameras.Camera;
import entities.Entity;

public interface Culling {
	
	Boolean getVisibility(Entity entity);
	void update(Camera camera);
	
}
