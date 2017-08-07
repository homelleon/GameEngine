package bodies;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import object.entity.entity.IEntity;

public interface IBody {
	
	float getMass();
	void setPosition(Vector3f position);
	Vector3f getPosition();
	void attachEntity(IEntity entity);
	IEntity getEntity();
	void doAcceleration(float value, Vector3f direction);
	void update();
	IntersectData checkIntersection(IBody body);
	int getTypeID();
	float getSize();
	
	
}
