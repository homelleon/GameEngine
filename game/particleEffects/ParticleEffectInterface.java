package particleEffects;

import org.lwjgl.util.vector.Vector3f;

public interface ParticleEffectInterface {
	
	void cleanUp();
	void create(String name, String texture);
	void setPosition(Vector3f position);
	Vector3f getPosition();
	String getType();
	String getName();
	
		
}
