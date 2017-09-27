package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import tool.math.vector.Vector3fF;

public interface IBody {
	
	float getMass();
	void setPosition(Vector3fF position);
	Vector3fF getPosition();
	void attachEntity(IEntity entity);
	IEntity getEntity();
	void doAcceleration(float value, Vector3fF direction);
	void update();
	IntersectData checkIntersection(IBody body);
	int getTypeID();
	float getSize();
	
	
}
