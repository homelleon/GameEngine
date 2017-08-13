package manager.particle;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.particle.IParticleSystem;
import object.particle.ParticleSystem;
import object.texture.particle.ParticleTexture;
import renderer.loader.Loader;
import tool.manager.AbstractManager;

public class ParticleManager extends AbstractManager<IParticleSystem> implements IParticleManager {

	public static List<IParticleSystem> createParticleSystem(Loader loader) {
		List<IParticleSystem> pSystem = new ArrayList<IParticleSystem>();
		ParticleTexture cosmicPTexture = new ParticleTexture(
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_PARTICLE_PATH, "cosmic"), 4, true);
		IParticleSystem cosmicParticle = new ParticleSystem("Cosmic", cosmicPTexture, 50, 25, 0.3f, 4, 1);
		cosmicParticle.randomizeRotation();
		cosmicParticle.setDirection(new Vector3f(0, 1, 0), 0.1f);
		cosmicParticle.setLifeError(0.1f);
		cosmicParticle.setSpeedError(0.4f);
		cosmicParticle.setScaleError(0.8f);

		ParticleTexture starPTexture = new ParticleTexture(
				loader.getTextureLoader().loadTexture(EngineSettings.TEXTURE_PARTICLE_PATH, "particleStar"), 1, true);
		IParticleSystem starParticle = new ParticleSystem("Star", starPTexture, 50, 25, 0.3f, 4, 1);
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
