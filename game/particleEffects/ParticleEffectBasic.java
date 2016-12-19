package particleEffects;

import org.lwjgl.util.vector.Vector3f;

import engineMain.EngineMain;
import particles.ParticleSystem;
import particles.ParticleTexture;

public class ParticleEffectBasic extends ParticleSystem {
	
	protected Vector3f center;
	protected String type;

	protected ParticleEffectBasic(String name, ParticleTexture texture, float pps, float speed, float gravityComplient,
			float lifeLength, float scale) {
		super(name, texture, pps, speed, gravityComplient, lifeLength, scale);	
	}
	
	public void create(String name) {
		EngineMain.getScene().getMap().createParticles("Part", "cosmic", 4, true, 50, 25, 0.3f, 4, 1);
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
