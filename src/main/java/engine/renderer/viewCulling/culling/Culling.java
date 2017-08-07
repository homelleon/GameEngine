package renderer.viewCulling.culling;

import object.camera.ICamera;
import object.entity.entity.IEntity;

public interface Culling {

	Boolean getVisibility(IEntity entity);

	void update(ICamera camera);

}
