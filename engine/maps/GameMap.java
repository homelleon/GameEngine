package maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import audio.Source;
import entities.Entity;
import entities.EntityTextured;
import models.TexturedModel;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Loader;
import scene.ES;
import scene.SceneObjectTools;
import terrains.Terrain;
import triggers.Trigger;

public class GameMap {
	
	private String name;
	private Map<String, Entity> entities = new HashMap<String, Entity>();
	private Map<String, Entity> normalEntities = new HashMap<String, Entity>();
	private Map<String, Terrain> terrains = new HashMap<String, Terrain>();
	private Map<String, Source> audios = new HashMap<String, Source>();
	private Map<String, Trigger> triggers = new HashMap<String, Trigger>();
	private Map<String, ParticleSystem> particleSystem = new HashMap<String, ParticleSystem>();
	
	private Loader loader;
	
	public GameMap(String name, Loader loader){
		this.loader = loader;
	}	
	
	public String getName() {
		return name;
	}

	public Map<String, Entity> getEntities() {
		return entities;
	}

	public void setEntities(Collection<Entity> entities) {
		for(Entity entity : entities){
			this.entities.put(entity.getName(), entity);			
		}
	}

	public Map<String, Entity> getNormalEntities() {
		return normalEntities;
	}

	public void setNormalEntities(List<Entity> normalEntities) {
		for(Entity entity : normalEntities){
			this.normalEntities.put(entity.getName(), entity);
		}
	}

	public Map<String, Terrain> getTerrains() {
		return terrains;
	}

	public void setTerrains(Collection<Terrain> terrains) {
		for(Terrain terrain : terrains){
			this.terrains.put(terrain.getName(), terrain);
		}
	}

	public Map<String, Source> getAudios() {
		return audios;
	}

	public void setAudios(List<Source> audios) {
		for(Source source : audios){
			this.audios.put(source.getName(), source);
		}
	}
	
	public Map<String, Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		for(Trigger trigger : triggers){
			this.triggers.put(trigger.getName(), trigger);
		}
	}
	
	public void setParticles(List<ParticleSystem> particleSystem) {
		for(ParticleSystem particles : particleSystem){
			this.particleSystem.put(particles.getName(), particles);
		}
	}
	
	public Map<String, ParticleSystem> getParticles() {
		return particleSystem;
	}
	
	public void createEntity(String name, String model, String texName, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		TexturedModel staticModel = SceneObjectTools.loadStaticModel(model, texName, loader);
		EntityTextured entity = new EntityTextured(name, staticModel, position, rotX, rotY, rotZ, scale);
		this.entities.put(name, entity);
	}
	
	public void createParticles(String name, String texName, int texDimentions, boolean additive, float pps, float speed, float gravityComplient, float lifeLength, float scale) {
		ParticleTexture texture = new ParticleTexture(loader.loadTexture(ES.PARTICLE_TEXTURE_PATH, texName), texDimentions, additive);
		ParticleSystem particles = new ParticleSystem(name, texture, pps, speed, gravityComplient, lifeLength, scale);	    	
		this.particleSystem.put(name, particles);
	}

}
