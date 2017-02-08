package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import renderEngine.Loader;
import scene.ES;
import textures.ModelTexture;
import toolbox.ObjectUtils;

public class EntityManagerStructured implements EntityManager {
	
	/*
	 * EntityManager - менеджер моделей
	 * 03.02.17
	 * ------------
	 */
	
	public Map<String, Entity> allEntities = new HashMap<String, Entity>();
	public Map<Float, List<Entity>> frustumEntities = new HashMap<Float, List<Entity>>();
	public List<Entity> pointedEntities = new ArrayList<Entity>();
	
	//TODO: delete after upgrading of map
	public static List<Entity> createNormalMappedEntities(Loader loader) {
		List<Entity> entities = new ArrayList<Entity>();
		TexturedModel barrelModel = new TexturedModel("barrel", NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture(ES.MODEL_TEXTURE_PATH,"barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture(ES.NORMAL_MAP_PATH, "barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		barrelModel.getTexture().setSpecularMap(loader.loadTexture(ES.SPECULAR_MAP_PATH, "barrelS"));

		TexturedModel boulderModel = new TexturedModel("boulder", NormalMappedObjLoader.loadOBJ("boulder", loader),
				new ModelTexture(loader.loadTexture(ES.MODEL_TEXTURE_PATH,"boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture(ES.NORMAL_MAP_PATH, "boulderNormal"));
		boulderModel.getTexture().setShineDamper(10);
		boulderModel.getTexture().setReflectivity(0.5f);
		
		/* creating entities */
		Entity barrel = new EntityTextured("barrel", ES.ENTITY_TYPE_NORMAL, barrelModel, new Vector3f(200, 0, 200), 0,0,0,1);
		Entity boulder = new EntityTextured("boulder", ES.ENTITY_TYPE_NORMAL, boulderModel, new Vector3f(250, 0, 250), 0,0,0,1);
		
		entities.add(barrel);
		entities.add(boulder);
		
		return entities; 
	}

	@Override
	public void addAll(Collection<Entity> entityList) {
		if(entityList == null) {
			throw(new NullPointerException("Tried to add null entityList in " + this.toString()));
		} else if (entityList.isEmpty()) {
			throw(new NullPointerException("Tried to add empty entityList in " + this.toString()));
		} else {
			for(Entity entity : entityList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}
	
	@Override
	public void addPointedList(Collection<Entity> pointedList) {
		if(pointedList == null) {
			throw(new NullPointerException("Tried to add null pointedList in " + this.toString()));
		} else if (pointedList.isEmpty()) {
			throw(new NullPointerException("Tried to add empty pointedList in " + this.toString()));
		} else {
			for(Entity entity : pointedList) {
				entity.setIsChosen(true);
				this.pointedEntities.add(entity);
			}
		}
	}	

	@Override
	public void addFrustumMap(Map<Float, List<Entity>> frustumMap) {
		if(frustumMap == null) {
			throw(new NullPointerException("Tried to add null frustumMap in " + this.toString()));
		} else if (frustumMap.isEmpty()) {
			throw(new NullPointerException("Tried to add empty frustumMap in " + this.toString()));
		} else {
			for(Float key : frustumMap.keySet()) {
				List<Entity> batch = new ArrayList<Entity>();
				for(Entity entity : frustumMap.get(key)) {
					batch.add(entity);
				}
				this.frustumEntities.put(key, batch);
			}
		}
	}


	@Override
	public void add(Entity entity) {
		if(entity == null) {
			throw(new NullPointerException("Tried to add null entity in " + this.toString()));
		} else {
			this.allEntities.put(entity.getName(), entity); 		
		}
	}
	
	@Override
	public void addPointed(Entity entity) {
		if(entity == null) {
			throw(new NullPointerException("Tried to add null pointedEntity in " + this.toString()));
		} else {
			entity.setIsChosen(true);
			this.pointedEntities.add(entity); 		
		}
	}

	@Override
	public void addInFrustum(float distance, Entity entity) {
		if(entity == null) {
			throw(new NullPointerException("Tried to add null frustumEntity in " + this.toString()));
		} else {
			if(this.frustumEntities.containsKey(distance)) {
				this.frustumEntities.get(distance).add(entity);
			} else {
				List<Entity> batch = new ArrayList<Entity>();
				batch.add(entity);
				this.frustumEntities.put(distance, batch);
			}			 		
		}
	}

	@Override
	public void clearAll() {
		this.allEntities.clear();
		this.pointedEntities.clear();
		this.frustumEntities.clear();
	}

	@Override
	public Entity getByName(String name) {
		Entity entity = null;
		if(this.allEntities.containsKey(name)) {
			entity = this.allEntities.get(name);
		}
		return entity;
	}

	@Override
	public Collection<Entity> getAll() {
		return this.allEntities.values();
	}

	@Override
	public Map<Float, List<Entity>> getFromFrustum() {
		return this.frustumEntities;
	}

	@Override
	public List<Entity> getPointed() {
		return this.pointedEntities;
	}

	@Override
	public void clearPointed() {
		for(Entity entity : this.pointedEntities) {
			entity.setIsChosen(false);
		}
		this.pointedEntities.clear();		
	}




}
