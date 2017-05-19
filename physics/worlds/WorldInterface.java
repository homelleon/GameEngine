package worlds;

import entities.EntityInterface;

public interface WorldInterface {
	
	int getID();
	boolean removeBody(int bodyID);
	boolean hasGravity();
	void setGravity(float value);
	int attachToEntity(EntityInterface entity, int bodyType);
	float getGravity();
	void update();
	void delete();
	
}
