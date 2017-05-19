package particleEffects;

import org.lwjgl.util.vector.Vector3f;

import particles.ParticleSystem;
import particles.ParticleTexture;

public class ParticleEffect extends ParticleSystem {
	
	protected Vector3f center;
	protected String type;

	protected ParticleEffect(String name, ParticleTexture texture, float pps, float speed, float gravityComplient,
			float lifeLength, float scale) {
		super(name, texture, pps, speed, gravityComplient, lifeLength, scale);	
	}
	
	public void create(String name) {
		
	}
	
	public void generateParticles() {
		super.generateParticles();
	}

	public void setPosition(Vector3f position) {
		super.setPosition(position);
	}

	public Vector3f getPosition() {
		return super.getPosition();
	}

	public String getType() {
		return type;
	}

	
	public String getName() {
		return super.getName();
	}

}
