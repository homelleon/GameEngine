package particleEffects;

import org.lwjgl.util.vector.Vector3f;

import engineMain.Main;
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
		Main.getMap().createParticles("Part", "cosmic", 4, true, 50, 25, 0.3f, 4, 1);
	}

	protected void setPosition(Vector3f position) {
		super.generateParticles(position);
		this.center = position;
	}

	protected Vector3f getPosition() {
		return center;
	}

	protected String getType() {
		return type;
	}
	
	public String getName() {
		return super.getName();
	}

}
