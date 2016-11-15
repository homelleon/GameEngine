package particleEffects;

import org.lwjgl.util.vector.Vector3f;

import particles.ParticleTexture;

public class ParticleFountan extends ParticleEffectBasic implements ParticleEffect {

	private ParticleFountan(String name, ParticleTexture texture, float pps, float speed, float gravityComplient,
			float lifeLength, float scale) {
		super(name, texture, pps, speed, gravityComplient, lifeLength, scale);
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
