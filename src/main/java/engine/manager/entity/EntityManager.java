package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * Менеджер игровых сущностей.
 * <p>
 * Может разделять сущности, которые попадают в пирамиду проекции. Также может
 * хранить сущности, выбранные игроком, и сущности для интерфейса редактора.
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
			pointedList.forEach(entity -> {
				entity.setIsChosen(true);
				this.pointedEntities.add(entity);
			});
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
			this.frustumEntities.putAll(frustumMap);
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
				this.frustumEntities.put(
						distance, 
						Stream.of(entity)
							  .collect(Collectors.toList())
				); 
			}
		}
	}

	@Override
	public void updateWithFrustum(Frustum frustum) {
		this.frustumEntities.clear();
		
		class Pair {
			private float distance;
			private IEntity entity;
			
			public Pair(IEntity entity) {
				this.entity = entity;
				this.distance = frustum.distanceSphereInFrustum(entity.getPosition(), entity.getSphereRadius());
			}
			public float getDistance() {
				return this.distance;
			}
			
			public IEntity getEntity() {
				return this.entity;
			}
			
			public boolean valid() {
				return distance > 0;
			}
		}
		
		Map<Float, List<IEntity>> validEntities = this.getAll().stream()
			.map(Pair::new)
			.filter(pair -> pair.valid())
			.collect(Collectors.groupingBy(Pair::getDistance, Collectors.mapping(Pair::getEntity, Collectors.toList())));
		this.frustumEntities.putAll(validEntities);		
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
		this.pointedEntities
			.forEach(entity -> entity.setIsChosen(false));
		this.pointedEntities.clear();
	}

	@Override
	public void clean() {
		super.clean();
		this.pointedEntities.clear();
		this.frustumEntities.clear();		
	}


}
