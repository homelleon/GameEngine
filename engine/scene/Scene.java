package scene;

import java.util.Collection;

import audio.AudioManager;
import cameras.Camera;
import entities.EntityManager;
import entities.Player;
import guis.GuiManager;
import lights.Light;
import lights.LightManager;
import particles.ParticleManager;
import particles.ParticleMaster;
import particles.ParticleSystem;
import terrains.TerrainManager;
import texts.TextManager;
import textures.Texture;
import toolbox.Frustum;
import toolbox.MousePicker;
import voxels.ChunkManager;
import water.WaterManager;

/**
 * Scene interface to control all objects to use in the game.
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public interface Scene {
	
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
	 * @return {@link Player} value
	 */
	Player getPlayer();
	
	/**
	 * Sets current player object in the scene.
	 * 
	 * @param player
	 * 				{@link Player} value to set
	 */
	void setPlayer(Player player);
	
	/**
	 * Returns current camera object.
	 * 
	 * @return {@link Camera} value
	 */
	Camera getCamera();
	
	/**
	 * Sets current camera object in the scene.
	 * 
	 * @param camera
	 * 				{@link Camera} value ot set
	 */
	void setCamera(Camera camera);
	
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
	EntityManager getEntities();
	
	/**
	 * Returns terrain manager object to control terrains.
	 * 
	 * @return {@link TerrainManager} value
	 */
	TerrainManager getTerrains();

	/**
	 * Returns water manager object to control water tiles.
	 * 
	 * @return {@link WaterManager} value
	 */
	WaterManager getWaters();
	
	/**
	 * Returns chunk manager.
	 * 
	 * @return {@link ChunkManager} value 
	 */
	ChunkManager getChunks();
	
	/**
	 * Returns partilce manager object to control particle systems.
	 * 
	 * @return {@link ParticleMaster} value
	 */
	ParticleManager getParticles();
	
	/**
	 * Returns light manager object to control lights.
	 * 
	 * @return {@link LightManager} value
	 */
	LightManager getLights();
	
	/**
	 * Returns audio manager.
	 *  
	 * @return {@link AudioManager} value
	 */
	AudioManager getAudioSources();
	
	/**
	 * Returns graphic interfaces manager.
	 * 
	 * @return {@link GuiManager} value of graphic
	 * 		   interfaces manager
	 */
	GuiManager getGuis();

	/**
	 * Returns manager of graphic interface texta.
	 * 
	 * @return {@link TextManager}> value of graphic
	 * 		   interfaces texts manager
	 */
	TextManager getTexts();
	
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
	void spreadEntitiesOnHeights();
	
	/**
	 * Spreads all particles on the surface of terrain's height.
	 */
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);
	
	/**
	 * Clear all variables and arrays of scene objects.
	 */
	void cleanUp();

}
