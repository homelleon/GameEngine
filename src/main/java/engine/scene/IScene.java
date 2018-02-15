package scene;

import java.util.Collection;

import control.MousePicker;
import manager.entity.IFrustumEntityManager;
import manager.gui.IGUIManager;
import manager.scene.IObjectManager;
import manager.voxel.IChunkManager;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import object.entity.player.IPlayer;
import object.light.Light;
import object.particle.ParticleSystem;
import primitive.texture.Texture;
import tool.math.Frustum;

/**
 * Scene interface to control all objects to use in the game.
 * 
 * @author homelleon
 * @version 1.0
 * @see Scene
 *
 */
public interface IScene extends IObjectManager {
	
	IFrustumEntityManager getFrustumEntities();
	/**
	 * Returns texture of current environment map.
	 * <p>
	 * NOTE: for using only one environment map in the game.
	 * 
	 * @return {@link Texture} value of environment map
	 */
	Texture getEnvironmentMap();

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
	IScene setPlayer(IPlayer player);

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
	IScene setCamera(ICamera camera);

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
	IScene setSun(Light sun);


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
	IScene setMousePicker(MousePicker picker);
	
	IScene setFrustum(Frustum frustum);
	
	Frustum getFrustum();

	/**
	 * Spreads all entities on the surface of terrain's height.
	 */
	IScene spreadEntitiesOnHeights(Collection<IEntity> entityList);

	/**
	 * Spreads all particles on the surface of terrain's height.
	 */
	IScene spreadParitclesOnHeights(Collection<ParticleSystem> particleSystems);

}
