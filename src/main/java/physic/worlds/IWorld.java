package worlds;

import object.entity.entity.IEntity;

public interface IWorld {
	
	int getID();
	boolean removeBody(int bodyID);
	boolean hasGravity();
	void setGravity(float value);
	int attachToEntity(IEntity entity, int bodyType);
	float getGravity();
	void update();
	void delete();
	
}
