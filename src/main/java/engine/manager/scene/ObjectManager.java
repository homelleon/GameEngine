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
public abstract class ObjectManager {
	
	protected EntityManager entityManager;
	protected TerrainManager terrainManager;
	protected ParticleManager particleManager;
	protected AudioManager audioManager;
	protected LightManager lightManager;
	protected WaterManager waterManager;
	
	protected ObjectManager() {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(null);
		this.lightManager = new LightManager();	
		this.waterManager = new WaterManager();
	}
	
	protected ObjectManager(AudioMaster master) {
		this.entityManager = new EntityManager();
		this.terrainManager = new TerrainManager();
		this.particleManager = new ParticleManager();
		this.audioManager = new AudioManager(master);
		this.lightManager = new LightManager();	
		this.waterManager = new WaterManager();
	}

	public EntityManager getEntities() {
		return entityManager;
	}

	public TerrainManager getTerrains() {
		return terrainManager;
	}
	
	public ParticleManager getParticles() {
		return particleManager;
	}
	
	public AudioManager getAudioSources() {
		return audioManager;
	}
	
	public LightManager getLights() {
		return lightManager;
	}
	
	public WaterManager getWaters() {
		return waterManager;
	}
	
	public void clean() {
		entityManager.clean();
		terrainManager.clean();
		particleManager.clean();
		audioManager.clean();
		lightManager.clean();
		waterManager.clean();
	}
	

}
