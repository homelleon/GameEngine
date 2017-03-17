package scene;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import audio.AudioMaster;
import audio.AudioSource;
import cameras.Camera;
import entities.EntityManager;
import entities.Light;
import entities.Player;
import fontMeshCreator.GuiText;
import guis.GuiTexture;
import particles.ParticleSystem;
import terrains.Terrain;
import textures.Texture;
import toolbox.Frustum;
import toolbox.MousePicker;
import voxels.Chunk;
import water.WaterTile;

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
	
	public void setAudioMaster(AudioMaster master);
	public AudioMaster getAudioMaster();
	
	EntityManager getEntities();
	
	Map<String, Terrain> getTerrains();
	void addTerrain(Terrain terrain);
	void addAllTerrains(Collection<Terrain> terrainList);
	
	Map<String, WaterTile> getWaters();
	void addWater(WaterTile water);
	void addAllWaters(Collection<WaterTile> waterList);	
	
	List<Chunk> getChunks();
	void addChunk(Chunk chunk);
	void addAllChunks(Collection<Chunk> chunkList);
	
	Map<String, ParticleSystem> getParticles();
	void addParticle(ParticleSystem particle);
	void addAllParticles(Collection<ParticleSystem> particleList);
	
	Map<String, Light> getLights();
	void addLight(Light light);
	void addAllLights(Collection<Light> lightList);
	
	Map<String, AudioSource> getAudioSources();
	void addAudioSource(AudioSource source);
	void addAllAudioSources(Collection<AudioSource> sourceList);	
	
	Map<String, GuiTexture> getGuis();
	void addGui(GuiTexture gui);
	void addAllGuis(Collection<GuiTexture> guiList);

	Map<String, GuiText> getTexts();
	void addText(GuiText text);
	void addAllTexts(Collection<GuiText> textList);
	
	Frustum getFrustum();
	
	MousePicker getPicker();
	void setPicker(MousePicker picker);
	
	void spreadEntitiesOnHeights();
	void spreadParitclesOnHeights(Collection<ParticleSystem> systems);
	void cleanUp();

}
