package object.entity.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.settings.EngineSettings;
import object.entity.entity.EntityInterface;
import renderer.viewCulling.frustum.Frustum;

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
public class EntityManager implements EntityManagerInterface {
	
	
	
	public Map<String, EntityInterface> allEntities = new HashMap<String, EntityInterface>();
	public Map<Float, List<EntityInterface>> frustumEntities = new HashMap<Float, List<EntityInterface>>();
	public List<EntityInterface> pointedEntities = new ArrayList<EntityInterface>();
	public List<EntityInterface> editorEntities = new ArrayList<EntityInterface>();

	@Override
	public void addAll(Collection<EntityInterface> entityList) {
		if((entityList != null) && (!entityList.isEmpty())) {
			for(EntityInterface entity : entityList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}
	
	@Override
	public void addPointedList(Collection<EntityInterface> pointedList) {
		if((pointedList != null) && (!pointedList.isEmpty())) {
			for(EntityInterface entity : pointedList) {
				entity.setIsChosen(true);
				this.pointedEntities.add(entity);
			}
		}
	}	
	

	@Override
	public void setEditorList(List<EntityInterface> editorList) {
		if(editorList != null) {
			this.editorEntities = editorList;
		}

		
	}

	@Override
	public void addFrustumMap(Map<Float, List<EntityInterface>> frustumMap) {
		if((frustumMap != null) && (!frustumMap.isEmpty())) {
			for(Float key : frustumMap.keySet()) {
				List<EntityInterface> batch = new ArrayList<EntityInterface>();
				for(EntityInterface entity : frustumMap.get(key)) {
					batch.add(entity);
				}
				this.frustumEntities.put(key, batch);
			}
		}
	}


	@Override
	public void add(EntityInterface entity) {
		if(entity != null) {
			this.allEntities.put(entity.getName(), entity); 		
		}
	}
	
	@Override
	public void addPointed(EntityInterface entity) {
		if(entity != null) {
			entity.setIsChosen(true);
			this.pointedEntities.add(entity); 		
		}
	}
	
	@Override
	public void addForEditor(EntityInterface entity) {
		this.editorEntities.add(entity);		
	}
	
	@Override
	public void addInFrustum(float distance, EntityInterface entity) {
		if(entity != null) {
			if(this.frustumEntities.containsKey(distance)) {
				this.frustumEntities.get(distance).add(entity);
			} else {
				List<EntityInterface> batch = new ArrayList<EntityInterface>();
				batch.add(entity);
				this.frustumEntities.put(distance, batch);
			}			 		
		}
	}
	
	@Override
	public void updateWithFrustum(Frustum frustum) {
		this.frustumEntities.clear();
		for(EntityInterface entity : this.allEntities.values()) {
			float distance = 
					frustum.distanceSphereInFrustum(
							entity.getPosition(), 
							entity.getSphereRadius());
			if (distance >=0 && distance < EngineSettings.RENDERING_VIEW_DISTANCE) {
				List<EntityInterface> batch;
				if(this.frustumEntities.containsKey(distance)) {
					batch = frustumEntities.get(distance);
				} else {
					batch = new ArrayList<EntityInterface>();
				}
				batch.add(entity);
				this.frustumEntities.put(distance, batch);
			}
		}
	}

	@Override
	public EntityInterface getByName(String name) {
		EntityInterface entity = null;
		if(this.allEntities.containsKey(name)) {
			entity = this.allEntities.get(name);
		}
		return entity;
	}

	@Override
	public Collection<EntityInterface> getAll() {
		return this.allEntities.values();
	}

	@Override
	public Map<Float, List<EntityInterface>> getFromFrustum() {
		return this.frustumEntities;
	}

	@Override
	public List<EntityInterface> getPointed() {
		return this.pointedEntities;
	}
	
	@Override
	public List<EntityInterface> getForEditor() {
		return this.editorEntities;
	}
	
	@Override
	public EntityInterface getForEditorByIndex(int index) {
		return this.editorEntities.get(index);
	}
		

	@Override
	public void clearPointed() {
		for(EntityInterface entity : this.pointedEntities) {
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
