package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import tool.math.vector.Vec3f;

public interface IBody {
	
	float getMass();
	void setPosition(Vec3f position);
	Vec3f getPosition();
	void attachEntity(IEntity entity);
	IEntity getEntity();
	void doAcceleration(float value, Vec3f direction);
	void update();
	IntersectData checkIntersection(IBody body);
	int getTypeID();
	float getSize();
	
	
}
