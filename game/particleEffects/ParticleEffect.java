package particleEffects;

import org.lwjgl.util.vector.Vector3f;

public interface ParticleEffect {
	
	void cleanUp();
	void setPosition(Vector3f position);
	Vector3f getPosition();
	String getType();
	String getName();
		
}
