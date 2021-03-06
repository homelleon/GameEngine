package object.scene;

import java.util.Collection;

import manager.audio.IAudioManager;
import manager.entity.IEntityManager;
import manager.gui.IGUIManager;
import manager.light.ILightManager;
import manager.particle.IParticleManager;
import manager.scene.IObjectManager;
import manager.terrain.ITerrainManager;
import manager.voxel.IChunkManager;
import manager.water.IWaterManager;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.entity.player.IPlayer;
import object.light.Light;
import object.particle.ParticleSystem;
import object.particle.master.ParticleMaster;
import object.texture.Texture;
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
	 * Returns mouse coordinates picker object.
	 * 
	 * @return {@link MousePicker} value
	 */
	MousePicker getMousePicker();

	/**
	 * Sets mouse coordinates picker object.
	 * 
	 * @param picker
	 *            {@link MousePicker} value
	 */
	void setMousePicker(MousePicker picker);

	/**
	 * Spreads all entities on the surface of terrain's height.
	 */
	void spreadEntitiesOnHeights(Collection<IEntity> entityList);

	/**
	 * Spreads all particles on the surface of terrain's height.
	 */
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);

}
