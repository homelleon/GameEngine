package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import renderer.viewCulling.frustum.Frustum;
import tool.manager.AbstractManager;

/**
 * Manages entities in the game engine.
 * <p>
 * Can differ entities that are situated in the frustum. Also can store entities
 * chosen by player and entities for editor menu.
 * <p>
 * rus:<br>
 * �������� ������� ���������.
 * <p>
 * ����� ��������� ��������, ������� �������� � �������� ��������. ����� �����
 * ������� ��������, ��������� �������, � �������� ��� ���������� ���������.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public class EntityManager extends AbstractManager<IEntity> implements IEntityManager {

	public Map<Float, List<IEntity>> frustumEntities = new HashMap<Float, List<IEntity>>();
	public List<IEntity> pointedEntities = new ArrayList<IEntity>();
	public List<IEntity> editorEntities = new ArrayList<IEntity>();


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
		for (IEntity entity : this.getAll()) {
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
		super.clean();
		this.pointedEntities.clear();
		this.frustumEntities.clear();		
	}


}