package bodies;

import org.lwjgl.util.vector.Vector3f;

public interface Body {
	
	float getMass();
	Vector3f getPosition();
	void doAcceleration(float value, Vector3f direction);
	int getID();
	
}
