package maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import audio.Source;
import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;
import scene.SceneObjectTools;
import terrains.Terrain;
import triggers.Trigger;

public class GameMap {
	
	private String name;
	private Map<String, Entity> entities;
	private Map<String, Entity> normalEntities;
	private Map<String, Terrain> terrains;
	private Map<String, Source> audios;
	private Map<String, Trigger> triggers;
	
	private Loader loader;
	
	public GameMap(String name, Loader loader){
		this.name = name;
		this.entities = new HashMap<String, Entity>();
		this.normalEntities = new HashMap<String, Entity>();
		this.terrains = new HashMap<String, Terrain>();
		this.audios = new HashMap<String, Source>();
		this.triggers = new HashMap<String, Trigger>();
		this.loader = loader;
	}	
	
	public String getName() {
		return name;
	}

	public Map<String, Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
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

	public void setTerrains(List<Terrain> terrains) {
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
	
	public void createEntity(String name, String model, String texName, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		TexturedModel staticModel = SceneObjectTools.loadStaticModel(model, texName, loader);
		Entity entity = new Entity(name, staticModel, position, rotX, rotY, rotZ, scale);
		this.entities.put(name, entity);
	}

}
