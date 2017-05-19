package particles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import scene.ES;

public class ParticleManager implements ParticleManagerInterface {
	
	Map<String, ParticleSystem> particles = new HashMap<String, ParticleSystem>();
	
	public static List<ParticleSystem> createParticleSystem(Loader loader) {
		List<ParticleSystem> pSystem = new ArrayList<ParticleSystem>();
		ParticleTexture cosmicPTexture = new ParticleTexture(loader.loadTexture(ES.PARTICLE_TEXTURE_PATH, "cosmic"), 4, true);
		ParticleSystem cosmicParticle = new ParticleSystem("Cosmic", cosmicPTexture, 50, 25, 0.3f, 4, 1);
		cosmicParticle.randomizeRotation();
		cosmicParticle.setDirection(new Vector3f(0, 1, 0), 0.1f);
		cosmicParticle.setLifeError(0.1f);
		cosmicParticle.setSpeedError(0.4f);
		cosmicParticle.setScaleError(0.8f);
		
		ParticleTexture starPTexture = new ParticleTexture(loader.loadTexture(ES.PARTICLE_TEXTURE_PATH, "particleStar"), 1, true);
		ParticleSystem starParticle = new ParticleSystem("Star", starPTexture, 50, 25, 0.3f, 4, 1);
		starParticle.randomizeRotation();
		starParticle.setDirection(new Vector3f(0, 1, 0), 0.1f);
		starParticle.setLifeError(0.1f);
		starParticle.setSpeedError(0.4f);
		starParticle.setScaleError(0.8f);
		
		pSystem.add(cosmicParticle);
		pSystem.add(starParticle);
		return pSystem;
	}

	@Override
	public void addAll(Collection<ParticleSystem> particleList) {
		if((particleList != null) && (!particleList.isEmpty())) {
			for(ParticleSystem particle : particleList) {
				this.particles.put(particle.getName(), particle);
			}
		}		
	}

	@Override
	public void add(ParticleSystem particle) {
		if(particle != null) {
			this.particles.put(particle.getName(), particle); 		
		}
	}

	@Override
	public ParticleSystem getByName(String name) {
		ParticleSystem particle = null;
		if(this.particles.containsKey(name)) {
			particle = this.particles.get(name);
		}
		return particle;
	}

	@Override
	public Collection<ParticleSystem> getAll() {
		return this.particles.values();
	}

	@Override
	public void clearAll() {
		this.particles.clear();
	}

}
