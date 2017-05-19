package creatures;

import bodies.BodyInterface;
import entities.EntityInterface;

public interface EntityWithPhysics extends EntityInterface {
	
	void createBody();
	BodyInterface getBody();

}
