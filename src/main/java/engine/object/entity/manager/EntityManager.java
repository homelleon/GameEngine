package object.entity.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.settings.EngineSettings;
import object.entity.entity.IEntity;
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
public class EntityManager implements IEntityManager {

	public Map<String, IEntity> allEntities = new HashMap<String, IEntity>();
	public Map<Float, List<IEntity>> frustumEntities = new HashMap<Float, List<IEntity>>();
	public List<IEntity> pointedEntities = new ArrayList<IEntity>();
	public List<IEntity> editorEntities = new ArrayList<IEntity>();

	@Override
	public void addAll(List<IEntity> elementList) {
		if ((elementList != null) && (!elementList.isEmpty())) {
			for (IEntity entity : elementList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}
	
	@Override
	public void addAll(Collection<IEntity> elementList) {
		if ((elementList != null) && (!elementList.isEmpty())) {
			for (IEntity entity : elementList) {
				this.allEntities.put(entity.getName(), entity);
			}
		}
	}

	@Override
	public void addPointedList(Collection<IEntity> pointedList) {
		if ((pointedList != null) && (!pointedList.isEmpty())) {
			for (IEntity entity : pointedList) {
				entity.setIsChosen(true);
				this.pointedEntities.add(entity);
			}
		}
	}

	@Override
	public void setEditorList(List<IEntity> editorList) {
		if (editorList != null) {
			this.editorEntities = editorList;
		}

	}

	@Override
	public void addFrustumMap(Map<Float, List<IEntity>> frustumMap) {
		if ((frustumMap != null) && (!frustumMap.isEmpty())) {
			for (Float key : frustumMap.keySet()) {
				List<IEntity> batch = new ArrayList<IEntity>();
				for (IEntity entity : frustumMap.get(key)) {
					batch.add(entity);
				}
				this.frustumEntities.put(key, batch);
			}
		}
	}

	@Override
	public void add(IEntity entity) {
		if (entity != null) {
			this.allEntities.put(entity.getName(), entity);
		}
	}

	@Override
	public void addPointed(IEntity entity) {
		if (entity != null) {
			entity.setIsChosen(true);
			this.pointedEntities.add(entity);
		}
	}

	@Override
	public void addForEditor(IEntity entity) {
		this.editorEntities.add(entity);
	}

	@Override
	public void addInFrustum(float distance, IEntity entity) {
		if (entity != null) {
			if (this.frustumEntities.containsKey(distance)) {
				this.frustumEntities.get(distance).add(entity);
			} else {
				List<IEntity> batch = new ArrayList<IEntity>();
				batch.add(entity);
				this.frustumEntities.put(distance, batch);
			}
		}
	}

	@Override
	public void updateWithFrustum(Frustum frustum) {
		this.frustumEntities.clear();
		for (IEntity entity : this.allEntities.values()) {
			float distance = frustum.distanceSphereInFrustum(entity.getPosition(), entity.getSphereRadius());
			if (distance >= 0 && distance < EngineSettings.RENDERING_VIEW_DISTANCE) {
				List<IEntity> batch;
				if (this.frustumEntities.containsKey(distance)) {
					batch = frustumEntities.get(distance);
				} else {
					batch = new ArrayList<IEntity>();
				}
				batch.add(entity);
				this.frustumEntities.put(distance, batch);
			}
		}
	}

	@Override
	public IEntity get(String name) {
		IEntity entity = null;
		if (this.allEntities.containsKey(name)) {
			entity = this.allEntities.get(name);
		}
		return entity;
	}

	@Override
	public Collection<IEntity> getAll() {
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
	public Map<Float, List<IEntity>> getFromFrustum() {
		return this.frustumEntities;
	}

	@Override
	public List<IEntity> getPointed() {
		return this.pointedEntities;
	}

	@Override
	public List<IEntity> getForEditor() {
		return this.editorEntities;
	}

	@Override
	public IEntity getForEditorByIndex(int index) {
		return this.editorEntities.get(index);
	}

	@Override
	public void clearPointed() {
		for (IEntity entity : this.pointedEntities) {
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
