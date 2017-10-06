package manager.scene;

import manager.audio.IAudioManager;
import manager.entity.IEntityManager;
import manager.light.ILightManager;
import manager.particle.IParticleManager;
import manager.terrain.ITerrainManager;
import manager.water.IWaterManager;
import map.objectMap.ObjectMapManager;

/**
 * Provide access to manages game objects through its managers.
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see ObjectMapManager
 */
public interface IObjectManager {
	
	IEntityManager getEntities();

	ITerrainManager getTerrains();
	
	IParticleManager getParticles();
	
	IAudioManager getAudioSources();	
	
	ILightManager getLights();
	
	IWaterManager getWaters();
	
	void clean();
	
}
