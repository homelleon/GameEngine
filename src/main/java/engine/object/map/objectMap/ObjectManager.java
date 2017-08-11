package object.map.objectMap;

import object.audio.manager.AudioManager;
import object.audio.manager.IAudioManager;
import object.entity.manager.EntityManager;
import object.entity.manager.IEntityManager;
import object.light.ILightManager;
import object.light.LightManager;
import object.particle.manager.IParticleManager;
import object.particle.manager.ParticleManager;
import object.terrain.manager.ITerrainManager;
import object.terrain.manager.TerrainManager;

public abstract class ObjectManager {
	
	protected IEntityManager entityManager;
	protected ITerrainManager terrainManager;
	protected IParticleManager particleManager;
	protected IAudioManager audioManager;
	protected ILightManager lightManager;
	
	protected ObjectManager() {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(null);
		this.lightManager = new LightManager();		
	}

	public IEntityManager getEntities() {
		return this.entityManager;
	}

	public ITerrainManager getTerrains() {
		return this.terrainManager;
	}
	
	public IParticleManager getParticles() {
		return this.particleManager;
	}
	
	public IAudioManager getAudioSources() {
		return this.audioManager;
	}
	
	public ILightManager getLights() {
		return this.lightManager;
	}
	
	public void clean() {
		this.entityManager.clean();
		this.terrainManager.clean();
		this.particleManager.clean();
		this.audioManager.clean();
		this.lightManager.clean();
	}
	

}
