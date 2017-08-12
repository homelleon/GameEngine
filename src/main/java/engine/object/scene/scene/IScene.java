package object.scene.scene;

import java.util.Collection;

import object.audio.manager.IAudioManager;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.entity.manager.IEntityManager;
import object.entity.player.IPlayer;
import object.gui.manager.IGUIManager;
import object.light.ILightManager;
import object.light.Light;
import object.particle.ParticleSystem;
import object.particle.manager.IParticleManager;
import object.particle.master.ParticleMaster;
import object.scene.manager.IObjectManager;
import object.terrain.manager.ITerrainManager;
import object.texture.Texture;
import object.voxel.manager.IChunkManager;
import object.water.manager.IWaterManager;
import renderer.viewCulling.frustum.Frustum;
import tool.MousePicker;

/**
 * Scene interface to control all objects to use in the game.
 * 
 * @author homelleon
 * @version 1.0
 * @see Scene
 *
 */
public interface IScene extends IObjectManager {

	/**
	 * Returns texture of current environment map.
	 * <p>
	 * NOTE: for using only one environment map in the game.
	 * 
	 * @return {@link Texture} value of environment map
	 */
	public Texture getEnvironmentMap();

	/**
	 * Returns object current player.
	 * 
	 * @return {@link IPlayer} value
	 */
	IPlayer getPlayer();

	/**
	 * Sets current player object in the scene.
	 * 
	 * @param player
	 *            {@link IPlayer} value to set
	 */
	void setPlayer(IPlayer player);

	/**
	 * Returns current camera object.
	 * 
	 * @return {@link ICamera} value
	 */
	ICamera getCamera();

	/**
	 * Sets current camera object in the scene.
	 * 
	 * @param camera
	 *            {@link ICamera} value ot set
	 */
	void setCamera(ICamera camera);

	/**
	 * Returns light object used for the Sun.
	 * 
	 * @return {@link Light} value of current Sun object
	 */
	Light getSun();

	/**
	 * Sets light as Sun object for global illumination.
	 * 
	 * @param sun
	 */
	void setSun(Light sun);


	/**
	 * Returns chunk manager.
	 * 
	 * @return {@link IChunkManager} value
	 */
	IChunkManager getChunks();

	/**
	 * Rerturns manager to control user interface.
	 * 
	 * @return {@link IGUIManager} value of user interface manager
	 */
	IGUIManager getUserInterface();

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
	 *            {@link MousePicker} value
	 */
	void setPicker(MousePicker picker);

	/**
	 * Spreads all entities on the surface of terrain's height.
	 */
	void spreadEntitiesOnHeights(Collection<IEntity> entityList);

	/**
	 * Spreads all particles on the surface of terrain's height.
	 */
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);

}
