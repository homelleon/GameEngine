package object.scene.manager;

import object.audio.manager.IAudioManager;
import object.entity.manager.IEntityManager;
import object.light.ILightManager;
import object.map.objectMap.ObjectMapManager;
import object.particle.manager.IParticleManager;
import object.terrain.manager.ITerrainManager;
import object.water.manager.IWaterManager;

/**
 * Contains lists of objects.
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
