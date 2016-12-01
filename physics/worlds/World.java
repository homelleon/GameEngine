package worlds;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;

public interface World {
	
	int getID();
	boolean removeBody(int bodyID);
	boolean hasGravity();
	void setGravity(float value);
	int attachToEntity(Entity entity, int bodyType);
	float getGravity();
	void update();
	void delete();
	
}
