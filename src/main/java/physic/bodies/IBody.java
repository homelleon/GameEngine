package bodies;

import intersects.IntersectData;
import object.entity.Entity;
import tool.math.vector.Vector3f;

public interface IBody {
	
	float getMass();
	void setPosition(Vector3f position);
	Vector3f getPosition();
	void attachEntity(Entity entity);
	Entity getEntity();
	void doAcceleration(float value, Vector3f direction);
	void update();
	IntersectData checkIntersection(IBody body);
	int getTypeID();
	float getSize();
	
	
}
