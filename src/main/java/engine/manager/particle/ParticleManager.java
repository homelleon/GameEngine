package manager.particle;

import java.util.ArrayList;
import java.util.List;

import core.settings.EngineSettings;
import object.particle.IParticleSystem;
import object.particle.ParticleSystem;
import object.texture.particle.ParticleMaterial;
import primitive.buffer.Loader;
import tool.manager.AbstractManager;
import tool.math.vector.Vector3f;

/**
 * Manages paricle systems.
 * 
 * @author homelleon
 * @see ParticleSystem
 * @see IParticleSystem
 */
public class ParticleManager extends AbstractManager<IParticleSystem> implements IParticleManager {

	/**
	 * Creates testing particle systems.
	 * 
	 * @return {@link List}<{@link IParticleSystem}> array of particle systems
	 */
	public static List<IParticleSystem> createParticleSystem() {
		Loader loader = Loader.getInstance();
		List<IParticleSystem> pSystem = new ArrayList<IParticleSystem>();
		ParticleMaterial cosmicPMaterial = new ParticleMaterial(
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_PARTICLE_PATH, "cosmic"), true);
		cosmicPMaterial.getTexture().setNumberOfRows(4);
		IParticleSystem cosmicParticle = new ParticleSystem("Cosmic", cosmicPMaterial, 50, 25, 0.3f, 4, 1);
		cosmicParticle.randomizeRotation();
		cosmicParticle.setDirection(new Vector3f(0, 1, 0), 0.1f);
		cosmicParticle.setLifeError(0.1f);
		cosmicParticle.setSpeedError(0.4f);
		cosmicParticle.setScaleError(0.8f);

		ParticleMaterial starPTMaterial = new ParticleMaterial(
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_PARTICLE_PATH, "particleStar"), true);
				starPTMaterial.getTexture().setNumberOfRows(1);
		IParticleSystem starParticle = new ParticleSystem("Star", starPTMaterial, 50, 25, 0.3f, 4, 1);
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
