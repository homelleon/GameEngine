package bodies;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import intersects.IntersectData;

public interface Body {
	
	float getMass();
	void setPosition(Vector3f position);
	Vector3f getPosition();
	void attachEntity(Entity entity);
	Entity getEntity();
	void doAcceleration(float value, Vector3f direction);
	IntersectData checkIntersection(Body body);
	int getTypeID();
	float getSize();
	
	
}
