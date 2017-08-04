package object.map.modelMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import object.audio.source.AudioSourceInterface;
import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.entity.player.PlayerInterface;
import object.gui.texture.GUITexture;
import object.light.Light;
import object.particle.ParticleSystem;
import object.terrain.terrain.TerrainInterface;
import object.trigger.Trigger;
import object.water.WaterTile;

/**
 * 
 * @author homelleon
 * @see ModelMap
 */
public interface ModelMapInterface {
	
	public String getName();

	/*
	 * Entities
	 * 
	 */

	public Map<String, Entity> getEntities();

	public void setEntities(Collection<Entity> entities);

	public void addEntity(Entity entity);

	/*
	 * Terrains
	 * 
	 */

	public Map<String, TerrainInterface> getTerrains();

	public void setTerrains(Collection<TerrainInterface> terrainList);

	public void addTerrian(TerrainInterface terrain);

	public void createTerrain(String terrainName, Vector2f position, String baseTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, float amplitude, int octave,
			float roughness);

	public void createTerrain(String terrainName, Vector2f position, String baseTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, String heightMap);

	/*
	 * AduioSources
	 * 
	 */

	public Map<String, AudioSourceInterface> getAudioSources();

	public void setAudioSources(List<AudioSourceInterface> audioList);

	public void addAudio(AudioSourceInterface auido);

	/*
	 * Triggers
	 * 
	 */

	public Map<String, Trigger> getTriggers();

	public void setTriggers(List<Trigger> triggerList);

	public void addTrigger(Trigger trigger);

	/*
	 * Particles
	 * 
	 */

	public Map<String, ParticleSystem> getParticles();

	public void setParticles(List<ParticleSystem> particleList);

	public void addParticle(ParticleSystem particle);

	/*
	 * Guis
	 * 
	 */

	public Map<String, GUITexture> getGuis();

	public void setGuis(List<GUITexture> guiList);

	public void addGui(GUITexture gui);

	/*
	 * Cameras
	 * 
	 */

	public Map<String, CameraInterface> getCameras();

	public void setCameras(List<CameraInterface> cameraList);

	public void addCamera(CameraInterface camera);

	/*
	 * Players
	 * 
	 */

	public Map<String, PlayerInterface> getPlayers();

	public void setPlayers(List<PlayerInterface> playerList);

	public void addPlayer(PlayerInterface player);

	/*
	 * Waters
	 * 
	 */

	public Map<String, WaterTile> getWaters();

	public void setWaters(List<WaterTile> waterList);

	public void addWater(WaterTile water);

	/*
	 * Lights
	 * 
	 */

	public Map<String, Light> getLights();

	public void setLights(List<Light> lightList);

	public void addLight(Light light);

	public void createEntity(String name, String model, String texName, Vector3f position, Vector3f rotation,
			float scale);

	public void createEntity(String name, String model, String texName, String normal, String specular,
			Vector3f position, float rotX, float rotY, float rotZ, float scale, float shine, float reflectivity);

	public void createParticles(String name, String texName, int texDimentions, boolean additive, float pps,
			float speed, float gravityComplient, float lifeLength, float scale);

	public void createAudioSource(String name, String path, int maxDistance, Vector3f coords);

}
