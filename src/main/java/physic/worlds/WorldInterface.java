package worlds;

import object.entity.entity.Entity;

public interface WorldInterface {
	
	int getID();
	boolean removeBody(int bodyID);
	boolean hasGravity();
	void setGravity(float value);
	int attachToEntity(Entity entity, int bodyType);
	float getGravity();
	void update();
	void delete();
	
}
