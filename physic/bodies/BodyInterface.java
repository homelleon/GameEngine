package bodies;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import object.entity.entity.Entity;

public interface BodyInterface {
	
	float getMass();
	void setPosition(Vector3f position);
	Vector3f getPosition();
	void attachEntity(Entity entity);
	Entity getEntity();
	void doAcceleration(float value, Vector3f direction);
	void update();
	IntersectData checkIntersection(BodyInterface body);
	int getTypeID();
	float getSize();
	
	
}
