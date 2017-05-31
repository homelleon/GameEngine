package creatures;

import bodies.BodyInterface;
import objects.entities.EntityInterface;

public interface EntityWithPhysics extends EntityInterface {
	
	void createBody();
	BodyInterface getBody();

}
