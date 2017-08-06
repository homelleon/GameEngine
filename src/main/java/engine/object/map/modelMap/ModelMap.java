package object.map.modelMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.audio.source.AudioSourceInterface;
import object.camera.CameraInterface;
import object.entity.entity.Entity;
import object.entity.entity.EntityBuilder;
import object.entity.entity.NormalMappedEntity;
import object.entity.entity.SimpleEntityBuilder;
import object.entity.entity.TexturedEntity;
import object.entity.player.PlayerInterface;
import object.gui.texture.GUITexture;
import object.light.Light;
import object.model.TexturedModel;
import object.particle.ParticleSystem;
import object.terrain.terrain.Terrain;
import object.texture.particle.ParticleTexture;
import object.trigger.Trigger;
import object.water.WaterTile;
import renderer.loader.Loader;
import tool.EngineUtils;

public class ModelMap implements ModelMapInterface {

	private String name;
	private Map<String, Entity> entities = new WeakHashMap<String, Entity>();
	private Map<String, Terrain> terrains = new WeakHashMap<String, Terrain>();
	private Map<String, AudioSourceInterface> audioSorces = new WeakHashMap<String, AudioSourceInterface>();
	private Map<String, Trigger> triggers = new WeakHashMap<String, Trigger>();
	private Map<String, ParticleSystem> particleSystems = new WeakHashMap<String, ParticleSystem>();
	private Map<String, GUITexture> guis = new WeakHashMap<String, GUITexture>();
	private Map<String, CameraInterface> cameras = new WeakHashMap<String, CameraInterface>();
	private Map<String, PlayerInterface> players = new WeakHashMap<String, PlayerInterface>();
	private Map<String, WaterTile> waters = new WeakHashMap<String, WaterTile>();
	private Map<String, Light> lights = new WeakHashMap<String, Light>();
	
	public ModelMap(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/*
	 * Entities
	 * 
	 */

	public Map<String, Entity> getEntities() {
		return entities;
	}

	public void setEntities(Collection<Entity> entities) {
		for (Entity entity : entities) {
			this.entities.put(entity.getName(), entity);
		}
	}

	public void addEntity(Entity entity) {
		this.entities.put(entity.getName(), entity);
	}

	/*
	 * Terrains
	 * 
	 */

	public Map<String, Terrain> getTerrains() {
		return terrains;
	}

	public void setTerrains(Collection<Terrain> terrainList) {
		for (Terrain terrain : terrainList) {
			this.terrains.put(terrain.getName(), terrain);
		}
	}

	public void addTerrian(Terrain terrain) {
		this.terrains.put(terrain.getName(), terrain);
	}

	public void createTerrain(String terrainName, Vector2f position, String baseTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, float amplitude, int octave,
			float roughness) {
		int x = (int) position.x;
		int y = (int) position.y;
		Terrain terrain = EngineUtils.createMultiTexTerrain(terrainName, x, y, baseTexture, redTexture,
				greenTexture, blueTexture, blendTexture, amplitude, octave, roughness);
		this.terrains.put(terrain.getName(), terrain);
	}

	public void createTerrain(String terrainName, Vector2f position, String baseTexture, String redTexture,
			String greenTexture, String blueTexture, String blendTexture, String heightMap) {
		int x = (int) position.x;
		int y = (int) position.y;
		Terrain terrain = EngineUtils.createMultiTexTerrain(terrainName, x, y, baseTexture, redTexture,
				greenTexture, blueTexture, blendTexture, heightMap, Loader.getInstance());
		this.terrains.put(terrain.getName(), terrain);
	}

	/*
	 * AduioSources
	 * 
	 */

	public Map<String, AudioSourceInterface> getAudioSources() {
		return audioSorces;
	}

	public void setAudioSources(List<AudioSourceInterface> audioList) {
		for (AudioSourceInterface audio : audioList) {
			this.audioSorces.put(audio.getName(), audio);
		}
	}

	public void addAudio(AudioSourceInterface auido) {
		this.audioSorces.put(auido.getName(), auido);
	}

	/*
	 * Triggers
	 * 
	 */

	public Map<String, Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggerList) {
		for (Trigger trigger : triggerList) {
			this.triggers.put(trigger.getName(), trigger);
		}
	}

	public void addTrigger(Trigger trigger) {
		this.triggers.put(trigger.getName(), trigger);
	}

	/*
	 * Particles
	 * 
	 */

	public Map<String, ParticleSystem> getParticles() {
		return particleSystems;
	}

	public void setParticles(List<ParticleSystem> particleList) {
		for (ParticleSystem particles : particleList) {
			this.particleSystems.put(particles.getName(), particles);
		}
	}

	public void addParticle(ParticleSystem particle) {
		this.particleSystems.put(particle.getName(), particle);
	}

	/*
	 * Guis
	 * 
	 */

	public Map<String, GUITexture> getGuis() {
		return guis;
	}

	public void setGuis(List<GUITexture> guiList) {
		for (GUITexture gui : guiList) {
			this.guis.put(gui.getName(), gui);
		}
	}

	public void addGui(GUITexture gui) {
		this.guis.put(gui.getName(), gui);
	}

	/*
	 * Cameras
	 * 
	 */

	public Map<String, CameraInterface> getCameras() {
		return cameras;
	}

	public void setCameras(List<CameraInterface> cameraList) {
		for (CameraInterface camera : cameraList) {
			this.cameras.put(camera.getName(), camera);
		}
	}

	public void addCamera(CameraInterface camera) {
		this.cameras.put(camera.getName(), camera);
	}

	/*
	 * Players
	 * 
	 */

	public Map<String, PlayerInterface> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerInterface> playerList) {
		for (PlayerInterface player : playerList) {
			this.players.put(player.getName(), player);
		}
	}

	public void addPlayer(PlayerInterface player) {
		this.players.put(player.getName(), player);
	}

	/*
	 * Waters
	 * 
	 */

	public Map<String, WaterTile> getWaters() {
		return waters;
	}

	public void setWaters(List<WaterTile> waterList) {
		for (WaterTile water : waterList) {
			this.waters.put(water.getName(), water);
		}
	}

	public void addWater(WaterTile water) {
		this.waters.put(water.getName(), water);
	}

	/*
	 * Lights
	 * 
	 */

	public Map<String, Light> getLights() {
		return lights;
	}

	public void setLights(List<Light> lightList) {
		for (Light light : lightList) {
			this.lights.put(light.getName(), light);
		}
	}

	public void addLight(Light light) {
		this.lights.put(light.getName(), light);
	}

	public void createParticles(String name, String texName, int texDimentions, boolean additive, float pps,
			float speed, float gravityComplient, float lifeLength, float scale) {
		ParticleTexture texture = new ParticleTexture(Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_PARTICLE_PATH, texName),
				texDimentions, additive);
		ParticleSystem particles = new ParticleSystem(name, texture, pps, speed, gravityComplient, lifeLength, scale);
		this.particleSystems.put(name, particles);
	}

	public void createAudioSource(String name, String path, int maxDistance, Vector3f coords) {
		// AudioSource source = new AudioSource(name, path, maxDistance,
		// coords);
		// this.audioSorces.put(name, source);
	}

}
