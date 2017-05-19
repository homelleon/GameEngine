package maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector3f;

import audio.AudioSourceInterface;
import cameras.CameraInterface;
import entities.EntityInterface;
import entities.Entity;
import entities.PlayerInterface;
import guiTextures.GUITexture;
import lights.Light;
import models.TexturedModel;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Loader;
import scene.ES;
import terrains.TerrainInterface;
import toolbox.ObjectUtils;
import triggers.Trigger;
import water.WaterTile;

public class GameMap {
	
	private String name;
	private Map<String, EntityInterface> entities = new WeakHashMap<String, EntityInterface>();
	private Map<String, TerrainInterface> terrains = new WeakHashMap<String, TerrainInterface>();
	private Map<String, AudioSourceInterface> audioSorces = new WeakHashMap<String, AudioSourceInterface>();
	private Map<String, Trigger> triggers = new WeakHashMap<String, Trigger>();
	private Map<String, ParticleSystem> particleSystems = new WeakHashMap<String, ParticleSystem>();
	private Map<String, GUITexture> guis = new WeakHashMap<String, GUITexture>();
	private Map<String, CameraInterface> cameras = new WeakHashMap<String, CameraInterface>();
	private Map<String, PlayerInterface> players = new WeakHashMap<String, PlayerInterface>();
	private Map<String, WaterTile> waters = new WeakHashMap<String, WaterTile>();
	private Map<String, Light> lights = new WeakHashMap<String, Light>();
	
	private Loader loader;
	
	public GameMap(String name, Loader loader) {
		this.name = name;
		this.loader = loader;
	}	
	
	public String getName() {
		return name;
	}
	
	/*
	 * Entities
	 * 
	 */

	public Map<String, EntityInterface> getEntities() {
		return entities;
	}

	public void setEntities(Collection<EntityInterface> entities) {
		for(EntityInterface entity : entities) {
			this.entities.put(entity.getName(), entity);
		}
	}
	
	public void addEntity(EntityInterface entity) {
		this.entities.put(entity.getName(), entity);
	}
	
	/*
	 * Terrains
	 * 
	 */

	public Map<String, TerrainInterface> getTerrains() {
		return terrains;
	}

	public void setTerrains(Collection<TerrainInterface> terrainList) {
		for(TerrainInterface terrain : terrainList){
			this.terrains.put(terrain.getName(), terrain);
		}
	}
	
	public void addTerrian(TerrainInterface terrain) {
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
		for(AudioSourceInterface audio : audioList) {
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
		for(Trigger trigger : triggerList) {
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
		for(ParticleSystem particles : particleList) {
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
		for(GUITexture gui : guiList) {
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
		for(CameraInterface camera : cameraList) {
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
		for(PlayerInterface player : playerList) {
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
		for(WaterTile water : waterList) {
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
		for(Light light : lightList) {
			this.lights.put(light.getName(), light);
		}
	}
	
	public void addLight(Light light) {
		this.lights.put(light.getName(), light);
	}


	
	public void createEntity(String name, String model, String texName, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		TexturedModel staticModel = ObjectUtils.loadStaticModel(model, texName, loader);
		Entity entity = new Entity(name, staticModel, position, rotX, rotY, rotZ, scale);
		this.entities.put(name, entity);
	}
	
	public void createEntity(String name, String model, String texName, String normal, String specular, Vector3f position, float rotX, float rotY, float rotZ, float scale, float shine, float reflectivity) {
		TexturedModel staticModel = ObjectUtils.loadNormalModel(name, texName, normal, specular, loader);
		staticModel.getTexture().setShineDamper(shine);
		staticModel.getTexture().setReflectivity(reflectivity);
		Entity entity = new Entity(name, ES.ENTITY_TYPE_NORMAL, staticModel, position, rotX, rotY, rotZ, scale);
		this.entities.put(name, entity);
	}
	
	public void createParticles(String name, String texName, int texDimentions, boolean additive, float pps, float speed, float gravityComplient, float lifeLength, float scale) {
		ParticleTexture texture = new ParticleTexture(loader.loadTexture(ES.PARTICLE_TEXTURE_PATH, texName), texDimentions, additive);
		ParticleSystem particles = new ParticleSystem(name, texture, pps, speed, gravityComplient, lifeLength, scale);	    	
		this.particleSystems.put(name, particles);
	}
	
	public void createAudioSource(String name, String path, int maxDistance, Vector3f coords) {
		//AudioSource source = new AudioSource(name, path, maxDistance, coords);
		//this.audioSorces.put(name, source);
	}

}
