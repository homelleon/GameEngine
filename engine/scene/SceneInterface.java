package scene;

import java.util.Collection;

import audio.AudioManagerInterface;
import cameras.CameraInterface;
import entities.EntityInterface;
import entities.EntityManagerInterface;
import entities.PlayerInterface;
import gui.GUIManagerInterface;
import guiTextures.GUITextureManagerInterface;
import lights.Light;
import lights.LightManager;
import particles.ParticleManagerInterface;
import particles.ParticleMaster;
import particles.ParticleSystem;
import terrains.TerrainManagerInterface;
import textures.Texture;
import toolbox.MousePicker;
import viewCulling.Frustum;
import voxels.ChunkManagerInterface;
import water.WaterManager;

/**
 * Scene interface to control all objects to use in the game.
 * 
 * @author homelleon
 * @version 1.0
 * @see Scene
 *
 */
public interface SceneInterface {
	
	/**
	 * Returns texture of current environment map.
	 * <p>NOTE: for using only one environment map in the game.
	 *  
	 * @return {@link Texture} value of environment map
	 */
	public Texture getEnvironmentMap();
	
	/**
	 * Returns object current player.
	 * 
	 * @return {@link PlayerInterface} value
	 */
	PlayerInterface getPlayer();
	
	/**
	 * Sets current player object in the scene.
	 * 
	 * @param player
	 * 				{@link PlayerInterface} value to set
	 */
	void setPlayer(PlayerInterface player);
	
	/**
	 * Returns current camera object.
	 * 
	 * @return {@link CameraInterface} value
	 */
	CameraInterface getCamera();
	
	/**
	 * Sets current camera object in the scene.
	 * 
	 * @param camera
	 * 				{@link CameraInterface} value ot set
	 */
	void setCamera(CameraInterface camera);
	
	/**
	 * Returns light object used for the Sun.  
	 * 
	 * @return {@link Light} value of current Sun object
	 */
	Light getSun();
	
	/**
	 * Sets light as Sun object for global illumination.
	 * @param sun
	 */
	void setSun(Light sun);
	
	/**
	 * Returns entity manager object to control scene entities.
	 *  
	 * @return EntityManager value
	 */
	EntityManagerInterface getEntities();
	
	/**
	 * Returns terrain manager object to control terrains.
	 * 
	 * @return {@link TerrainManagerInterface} value
	 */
	TerrainManagerInterface getTerrains();

	/**
	 * Returns water manager object to control water tiles.
	 * 
	 * @return {@link WaterManager} value
	 */
	WaterManager getWaters();
	
	/**
	 * Returns chunk manager.
	 * 
	 * @return {@link ChunkManagerInterface} value 
	 */
	ChunkManagerInterface getChunks();
	
	/**
	 * Returns partilce manager object to control particle systems.
	 * 
	 * @return {@link ParticleMaster} value
	 */
	ParticleManagerInterface getParticles();
	
	/**
	 * Returns light manager object to control lights.
	 * 
	 * @return {@link LightManager} value
	 */
	LightManager getLights();
	
	/**
	 * Returns audio manager.
	 *  
	 * @return {@link AudioManagerInterface} value
	 */
	AudioManagerInterface getAudioSources();
	
	/**
	 * Returns graphic interfaces manager.
	 * 
	 * @return {@link GUITextureManagerInterface} value of graphic
	 * 		   interfaces manager
	 */
	GUITextureManagerInterface getGuis();
	
	/**
	 * Rerturns manager to control user interface.
	 * 
	 * @return {@link GUIManagerInterface} value of user interface
	 * manager 
	 */
	GUIManagerInterface getUserInterface();
	
	/**
	 * Returns visual frustum object.
	 * 
	 * @return {@link Frustum} value of visual frustum
	 */
	Frustum getFrustum();
	
	/**
	 * Returns mouse coordinates picker object.
	 * 	
	 * @return {@link MousePicker} value
	 */
	MousePicker getPicker();
	
	/**
	 * Sets mouse coordinates picker object.
	 * 
	 * @param picker
	 * 				{@link MousePicker} value
	 */
	void setPicker(MousePicker picker);
	
	/**
	 * Spreads all entities on the surface of terrain's height.
	 */
	void spreadEntitiesOnHeights(Collection<EntityInterface> entityList);
	
	/**
	 * Spreads all particles on the surface of terrain's height.
	 */
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);
	
	/**
	 * Clear all variables and arrays of scene objects.
	 */
	void cleanUp();

}
