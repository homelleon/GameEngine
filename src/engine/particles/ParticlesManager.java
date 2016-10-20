package engine.particles;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import engine.renderEngine.Loader;
import engine.scene.Settings;

public class ParticlesManager {
	
	public static List<ParticleSystem> createParticleSystem(Loader loader){
		List<ParticleSystem> pSystem = new ArrayList<ParticleSystem>();
		ParticleTexture cosmicPTexture = new ParticleTexture(loader.loadTexture(Settings.PARTICLE_TEXTURE_PATH, "cosmic"), 4, true);
		ParticleSystem cosmicParticle = new ParticleSystem(cosmicPTexture, 50, 25, 0.3f, 4, 1);
		cosmicParticle.randomizeRotation();
		cosmicParticle.setDirection(new Vector3f(0, 1, 0), 0.1f);
		cosmicParticle.setLifeError(0.1f);
		cosmicParticle.setSpeedError(0.4f);
		cosmicParticle.setScaleError(0.8f);
		
		ParticleTexture starPTexture = new ParticleTexture(loader.loadTexture(Settings.PARTICLE_TEXTURE_PATH, "particleStar"), 1, true);
		ParticleSystem starParticle = new ParticleSystem(starPTexture, 50, 25, 0.3f, 4, 1);
		starParticle.randomizeRotation();
		starParticle.setDirection(new Vector3f(0, 1, 0), 0.1f);
		starParticle.setLifeError(0.1f);
		starParticle.setSpeedError(0.4f);
		starParticle.setScaleError(0.8f);
		
		pSystem.add(cosmicParticle);
		pSystem.add(starParticle);
		return pSystem;
	}

}
