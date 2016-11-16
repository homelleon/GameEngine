package creatures;

import bodies.Body;
import entities.Entity;

public interface EntityWithPhysics extends Entity {
	
	void createBody();
	Body getBody();

}
