package particleEffects;

import org.lwjgl.util.vector.Vector3f;

import particles.ParticleTexture;

public class ParticleFountain extends ParticleEffectBasic implements ParticleEffect {
	
	private ParticleFountain self;

	private ParticleFountain(String name, ParticleTexture texture, float pps, float speed, float gravityComplient,
			float lifeLength, float scale) {
		super(name, texture, pps, speed, gravityComplient, lifeLength, scale);
	}
	
	
	public void create(String name, String texture) {
		ParticleTexture pTexture = new ParticleTexture();
		self = new ParticleFountain(name, pTexture, 1, 1, 1, 1, 1);
	}

	@Override
	public void cleanUp() {

	}
	
	@Override
	public Vector3f getPosition() {
		return center;
	}
	
	public void setPosition(Vector3f position) {
		super.setPosition(position);
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public String getName() {
		return super.getName();
	}

}
