package manager.scene;

import manager.AudioManager;
import manager.LightManager;
import manager.ParticleManager;
import manager.TerrainManager;
import manager.WaterManager;
import manager.entity.EntityManager;
import object.audio.AudioMaster;

/**
 * Abstact class of engine objects manager.
 * 
 * @author homelleon
 * 
 */
public abstract class AObjectManager {
	
	protected EntityManager entityManager;
	protected TerrainManager terrainManager;
	protected ParticleManager particleManager;
	protected AudioManager audioManager;
	protected LightManager lightManager;
	protected WaterManager waterManager;
	
	protected AObjectManager() {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(null);
		this.lightManager = new LightManager();	
		this.waterManager = new WaterManager();
	}
	
	protected AObjectManager(AudioMaster master) {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(master);
		this.lightManager = new LightManager();	
		this.waterManager = new WaterManager();
	}

	public EntityManager getEntities() {
		return this.entityManager;
	}

	public TerrainManager getTerrains() {
		return this.terrainManager;
	}
	
	public ParticleManager getParticles() {
		return this.particleManager;
	}
	
	public AudioManager getAudioSources() {
		return this.audioManager;
	}
	
	public LightManager getLights() {
		return this.lightManager;
	}
	
	public WaterManager getWaters() {
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
