package maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.util.vector.Vector3f;

import audio.AudioSource;
import cameras.Camera;
import entities.Entity;
import entities.EntityTextured;
import entities.Player;
import guis.GuiTexture;
import lights.Light;
import models.TexturedModel;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Loader;
import scene.ES;
import terrains.Terrain;
import toolbox.ObjectUtils;
import triggers.Trigger;
import water.WaterTile;

public class GameMap {
	
	private String name;
	private Map<String, Entity> entities = new WeakHashMap<String, Entity>();
	private Map<String, Terrain> terrains = new WeakHashMap<String, Terrain>();
	private Map<String, AudioSource> audioSorces = new WeakHashMap<String, AudioSource>();
	private Map<String, Trigger> triggers = new WeakHashMap<String, Trigger>();
	private Map<String, ParticleSystem> particleSystems = new WeakHashMap<String, ParticleSystem>();
	private Map<String, GuiTexture> guis = new WeakHashMap<String, GuiTexture>();
	private Map<String, Camera> cameras = new WeakHashMap<String, Camera>();
	private Map<String, Player> players = new WeakHashMap<String, Player>();
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

	public Map<String, Entity> getEntities() {
		return entities;
	}

	public void setEntities(Collection<Entity> entities) {
		for(Entity entity : entities) {
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
		for(Terrain terrain : terrainList){
			this.terrains.put(terrain.getName(), terrain);
		}
	}
	
	public void addTerrian(Terrain terrain) {
		this.terrains.put(terrain.getName(), terrain);
	}
	
	/*
	 * AduioSources
	 * 
	 */

	public Map<String, AudioSource> getAudioSources() {
		return audioSorces;
	}

	public void setAudioSources(List<AudioSource> audioList) {
		for(AudioSource audio : audioList) {
			this.audioSorces.put(audio.getName(), audio);
		}
	}
	
	public void addAudio(AudioSource auido) {
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
	
	public Map<String, GuiTexture> getGuis() {
		return guis;
	}

	public void setGuis(List<GuiTexture> guiList) {
		for(GuiTexture gui : guiList) {
			this.guis.put(gui.getName(), gui);
		}
	}
	
	public void addGui(GuiTexture gui) {
		this.guis.put(gui.getName(), gui);
	}
	
	/*
	 * Cameras
	 * 
	 */

	public Map<String, Camera> getCameras() {
		return cameras;
	}

	public void setCameras(List<Camera> cameraList) {
		for(Camera camera : cameraList) {
			this.cameras.put(camera.getName(), camera);
		}
	}
	
	public void addCamera(Camera camera) {
		this.cameras.put(camera.getName(), camera);
	}
	
	/*
	 * Players
	 * 
	 */

	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> playerList) {
		for(Player player : playerList) {
			this.players.put(player.getName(), player);
		}
	}
	
	public void addPlayer(Player player) {
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
		EntityTextured entity = new EntityTextured(name, staticModel, position, rotX, rotY, rotZ, scale);
		this.entities.put(name, entity);
	}
	
	public void createEntity(String name, String model, String texName, String normal, String specular, Vector3f position, float rotX, float rotY, float rotZ, float scale, float shine, float reflectivity) {
		TexturedModel staticModel = ObjectUtils.loadNormalModel(name, texName, normal, specular, loader);
		staticModel.getTexture().setShineDamper(shine);
		staticModel.getTexture().setReflectivity(reflectivity);
		EntityTextured entity = new EntityTextured(name, ES.ENTITY_TYPE_NORMAL, staticModel, position, rotX, rotY, rotZ, scale);
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
