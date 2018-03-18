package worlds;

import object.entity.Entity;

public interface IWorld {
	
	int getID();
	boolean removeBody(int bodyID);
	boolean hasGravity();
	void setGravity(float value);
	int attachToEntity(Entity entity, int bodyType);
	float getGravity();
	void update();
	void delete();
	
}
