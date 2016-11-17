package worlds;

import org.lwjgl.util.vector.Vector3f;

public interface World {
	
	int getID();
	boolean hasGravity();
	void setGravity(float value);
	float getGravity();
	void update();
	void delete();
	
}
