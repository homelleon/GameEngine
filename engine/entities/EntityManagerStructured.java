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
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Frustum;

/**
 * Manages entities in the game engine.
 * <p>Can differ entities that are situated in the frustum. Also can store
 * entities chosen by player and entities for editor menu.
 * <p>rus:<br> 
 * Менеджер игровых сущностей.
 * <p>Может разделять сущности, которые попадают в пирамиду проекции. Также
 * может хранить сущности, выбранные игроком, и сущности для интерфейса
 * редактора.
 * 
 * @author homelleon
 * @version 1.0
 *  
 */
public class EntityManagerStructured implements EntityManager {
	
	
	
	public Map<String, Entity> allEntities = new HashMap<String, Entity>();
	public Map<Float, List<Entity>> frustumEntities = new HashMap<Float, List<Entity>>();
	public List<Entity> pointedEntities = new ArrayList<Entity>();
	public List<Entity> editorEntities = new ArrayList<Entity>();
	
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
		if((entityList != null) && (!entityList.isEmpty())) {
			for(Entity entity : entityList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}
	
	@Override
	public void addPointedList(Collection<Entity> pointedList) {
		if((pointedList != null) && (!pointedList.isEmpty())) {
			for(Entity entity : pointedList) {
				entity.setIsChosen(true);
				this.pointedEntities.add(entity);
			}
		}
	}	
	

	@Override
	public void setEditorList(List<Entity> editorList) {
		if(editorList != null) {
			this.editorEntities = editorList;
		}

		
	}

	@Override
	public void addFrustumMap(Map<Float, List<Entity>> frustumMap) {
		if((frustumMap != null) && (!frustumMap.isEmpty())) {
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
		if(entity != null) {
			this.allEntities.put(entity.getName(), entity); 		
		}
	}
	
	@Override
	public void addPointed(Entity entity) {
		if(entity != null) {
			entity.setIsChosen(true);
			this.pointedEntities.add(entity); 		
		}
	}
	
	@Override
	public void addForEditor(Entity entity) {
		this.editorEntities.add(entity);		
	}
	
	@Override
	public void addInFrustum(float distance, Entity entity) {
		if(entity != null) {
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
	public void updateWithFrustum(Frustum frustum) {
		this.frustumEntities.clear();
		for(Entity entity : this.allEntities.values()) {
			float distance = 
					frustum.distanceSphereInFrustum(
							entity.getPosition(), 
							entity.getSphereRadius());
			if (distance >=0 && distance < ES.RENDERING_VIEW_DISTANCE) {
				List<Entity> batch;
				if(this.frustumEntities.containsKey(distance)) {
					batch = frustumEntities.get(distance);
				} else {
					batch = new ArrayList<Entity>();
				}
				batch.add(entity);
				this.frustumEntities.put(distance, batch);
			}
		}
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
	public List<Entity> getForEditor() {
		return this.editorEntities;
	}
	
	@Override
	public Entity getForEditorByIndex(int index) {
		return this.editorEntities.get(index);
	}
		

	@Override
	public void clearPointed() {
		for(Entity entity : this.pointedEntities) {
			entity.setIsChosen(false);
		}
		this.pointedEntities.clear();		
	}
	
	@Override
	public void clearAll() {
		this.allEntities.clear();
		this.pointedEntities.clear();
		this.frustumEntities.clear();
	}
	
}
