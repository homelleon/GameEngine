package bodies;

import org.lwjgl.util.vector.Vector3f;

public interface Body {
	
	float getMass();
	void setPosition(Vector3f position);
	Vector3f getPosition();
	void doAcceleration(float value, Vector3f direction);
	int getID();
	int getTypeID();
	float getSize();
	
	
}
