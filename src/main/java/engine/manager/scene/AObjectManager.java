package manager.scene;

import manager.audio.AudioManager;
import manager.audio.IAudioManager;
import manager.entity.EntityManager;
import manager.entity.IEntityManager;
import manager.light.ILightManager;
import manager.light.LightManager;
import manager.particle.IParticleManager;
import manager.particle.ParticleManager;
import manager.terrain.ITerrainManager;
import manager.terrain.TerrainManager;
import manager.water.IWaterManager;
import manager.water.WaterManager;
import object.audio.master.IAudioMaster;
import object.terrain.terrain.ITerrain;

/**
 * Abstact class of engine objects manager.
 * 
 * @author homelleon
 * 
 */
public abstract class AObjectManager {
	
	protected IEntityManager entityManager;
	protected ITerrainManager terrainManager;
	protected IParticleManager particleManager;
	protected IAudioManager audioManager;
	protected ILightManager lightManager;
	protected IWaterManager waterManager;
	
	protected AObjectManager() {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(null);
		this.lightManager = new LightManager();	
		this.waterManager = new WaterManager();
	}
	
	protected AObjectManager(IAudioMaster master) {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(master);
		this.lightManager = new LightManager();	
		this.waterManager = new WaterManager();
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
	
	public IWaterManager getWaters() {
		return this.waterManager;
	}
	
	public void clean() {
		this.entityManager.clean();
		this.terrainManager.clean();
		this.particleManager.clean();
		this.audioManager.clean();
		this.lightManager.clean();
		this.waterManager.clean();
	}
	

}
