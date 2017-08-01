package object.entity.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.settings.EngineSettings;
import object.entity.entity.Entity;
import renderer.viewCulling.frustum.Frustum;

/**
 * Manages entities in the game engine.
 * <p>
 * Can differ entities that are situated in the frustum. Also can store entities
 * chosen by player and entities for editor menu.
 * <p>
 * rus:<br>
 * Менеджер игровых сущностей.
 * <p>
 * Может разделять сущности, которые попадают в пирамиду проекции. Также может
 * хранить сущности, выбранные игроком, и сущности для интерфейса редактора.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public class EntityManager implements EntityManagerInterface {

	public Map<String, Entity> allEntities = new HashMap<String, Entity>();
	public Map<Float, List<Entity>> frustumEntities = new HashMap<Float, List<Entity>>();
	public List<Entity> pointedEntities = new ArrayList<Entity>();
	public List<Entity> editorEntities = new ArrayList<Entity>();

	@Override
	public void addAll(List<Entity> elementList) {
		if ((elementList != null) && (!elementList.isEmpty())) {
			for (Entity entity : elementList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}
	
	@Override
	public void addAll(Collection<Entity> elementList) {
		if ((elementList != null) && (!elementList.isEmpty())) {
			for (Entity entity : elementList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}

	@Override
	public void addPointedList(Collection<Entity> pointedList) {
		if ((pointedList != null) && (!pointedList.isEmpty())) {
			for (Entity entity : pointedList) {
				entity.setIsChosen(true);
				this.pointedEntities.add(entity);
			}
		}
	}

	@Override
	public void setEditorList(List<Entity> editorList) {
		if (editorList != null) {
			this.editorEntities = editorList;
		}

	}

	@Override
	public void addFrustumMap(Map<Float, List<Entity>> frustumMap) {
		if ((frustumMap != null) && (!frustumMap.isEmpty())) {
			for (Float key : frustumMap.keySet()) {
				List<Entity> batch = new ArrayList<Entity>();
				for (Entity entity : frustumMap.get(key)) {
					batch.add(entity);
				}
				this.frustumEntities.put(key, batch);
			}
		}
	}

	@Override
	public void add(Entity entity) {
		if (entity != null) {
			this.allEntities.put(entity.getName(), entity);
		}
	}

	@Override
	public void addPointed(Entity entity) {
		if (entity != null) {
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
		if (entity != null) {
			if (this.frustumEntities.containsKey(distance)) {
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
		for (Entity entity : this.allEntities.values()) {
			float distance = frustum.distanceSphereInFrustum(entity.getPosition(), entity.getSphereRadius());
			if (distance >= 0 && distance < EngineSettings.RENDERING_VIEW_DISTANCE) {
				List<Entity> batch;
				if (this.frustumEntities.containsKey(distance)) {
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
	public Entity get(String name) {
		Entity entity = null;
		if (this.allEntities.containsKey(name)) {
			entity = this.allEntities.get(name);
		}
		return entity;
	}

	@Override
	public Collection<Entity> getAll() {
		return this.allEntities.values();
	}
	
	@Override
	public boolean delete(String name) {
		if(this.allEntities.containsKey(name)) {
			this.allEntities.remove(name);
			return true;
		} else {
			return false;
		}
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
		for (Entity entity : this.pointedEntities) {
			entity.setIsChosen(false);
		}
		this.pointedEntities.clear();
	}

	@Override
	public void clean() {
		this.allEntities.clear();
		this.pointedEntities.clear();
		this.frustumEntities.clear();		
	}

}
