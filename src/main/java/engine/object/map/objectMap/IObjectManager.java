package object.map.objectMap;

import object.Nameable;
import object.audio.manager.IAudioManager;
import object.entity.manager.IEntityManager;
import object.light.ILightManager;
import object.particle.manager.IParticleManager;
import object.terrain.manager.ITerrainManager;

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
	
	void clean();
	
}
