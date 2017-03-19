package scene;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import audio.AudioMaster;
import audio.AudioSource;
import cameras.Camera;
import entities.EntityManager;
import entities.Player;
import fontMeshCreator.GuiText;
import guis.GuiTexture;
import lights.Light;
import lights.LightManager;
import particles.ParticleManager;
import particles.ParticleMaster;
import particles.ParticleSystem;
import terrains.TerrainManager;
import textures.Texture;
import toolbox.Frustum;
import toolbox.MousePicker;
import voxels.Chunk;
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
	 * Install audio master for sound control.
	 * 
	 * @param master
	 * 				 AudioMaster value
	 */
	public void setAudioMaster(AudioMaster master);
	
	/**
	 * Retunrs current audio master object.
	 * 
	 * @return AudioMaster value
	 */
	public AudioMaster getAudioMaster();
	
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
	 * Returns list of chunks.
	 * 
	 * @return {@link List}<{@link Chunk}> value of chunk list 
	 */
	List<Chunk> getChunks();
	
	/**
	 * Adds one chunk into the chunk array of voxel manager.
	 * 
	 * @param chunk
	 * 				{@link Chunk} value
	 */
	void addChunk(Chunk chunk);
	
	/**
	 * Adds list of chunks into the chunk array of voxel manager.
	 * 
	 * @param chunkList
	 * 					{@link Collection}<{@link Chunk}> value of chunk list
	 */
	void addAllChunks(Collection<Chunk> chunkList);
	
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
	 * Returns map of audio sources groupped by name.
	 *  
	 * @return {@link Map}<{@link String}, {@link AudioSource}> value of lights
	 * 		   map
	 */
	Map<String, AudioSource> getAudioSources();
	
	/**
	 * Adds one audio source into audio sources map array.
	 * 
	 * @param source
	 * 				 {@link AudioSource} value
	 */
	void addAudioSource(AudioSource source);
	
	/**
	 * Adds list of audio sources into audio sources map array.
	 * 
	 * @param sourceList
	 * 					 {@link Collection}<{@link AudioSource}> value of audio
	 * 					 source list
	 */
	void addAllAudioSources(Collection<AudioSource> sourceList);	
	
	/**
	 * Returns map of graphic interfaces groupped by name.
	 * 
	 * @return {@link Map}<{@link String}, {@link GuiTexture}> value of graphic
	 * 		   interfaces map
	 */
	Map<String, GuiTexture> getGuis();
	
	/**
	 * Adds one graphic interface into the graphic interfaces map array.
	 * 
	 * @param gui
	 * 			 {@link GuiTexture} value of graphic interface
	 */
	void addGui(GuiTexture gui);
	
	/**
	 * Adds list of graphic interfaces into the graphic interfaces map array.
	 * 
	 * @param guiList
	 * 				  {@link Collection}<{@link GuiTexture}> value of graphic
	 * 				  interfaces list
	 */
	void addAllGuis(Collection<GuiTexture> guiList);

	/**
	 * Returns map of graphic interface text groupped by name.
	 * 
	 * @return {@link Map}<{@link String}, {@link GuiText}> value of graphic
	 * 		   interfaces map
	 */
	Map<String, GuiText> getTexts();
	
	/**
	 * Adds one graphic interface into the graphic interfaces map array.
	 * 
	 * @param text
	 * 			  {@link GuiText} value of graphic interface text
	 */
	void addText(GuiText text);
	
	/**
	 * Adds list of graphic interfaces list into the graphic interfaces map 
	 * array.
	 * 
	 * @param textList
	 * 				  {@link Collection}<{@link GuiText}> value of graphic
	 * 				  interfaces list
	 */
	void addAllTexts(Collection<GuiText> textList);
	
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
