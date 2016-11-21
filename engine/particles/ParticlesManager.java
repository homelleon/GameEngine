package particles;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import scene.ES;

public class ParticlesManager {
	
	public static Map<String, ParticleSystem> createParticleSystem(Loader loader) {
		Map<String, ParticleSystem> pSystem = new WeakHashMap<String, ParticleSystem>();
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
		
		pSystem.put(cosmicParticle.getName(), cosmicParticle);
		pSystem.put(starParticle.getName(), starParticle);
		return pSystem;
	}

}
