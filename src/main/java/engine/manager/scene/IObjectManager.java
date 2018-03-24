package manager.scene;

import manager.AudioManager;
import manager.LightManager;
import manager.ObjectMapManager;
import manager.ParticleManager;
import manager.TerrainManager;
import manager.WaterManager;
import manager.entity.EntityManager;

/**
 * Provide access to manages game objects through its managers.
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see ObjectMapManager
 */
public interface IObjectManager {
	
	EntityManager getEntities();

	TerrainManager getTerrains();
	
	ParticleManager getParticles();
	
	AudioManager getAudioSources();	
	
	LightManager getLights();
	
	WaterManager getWaters();
	
	void clean();
	
}
