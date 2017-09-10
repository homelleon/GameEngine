package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.settings.EngineSettings;
import object.camera.ICamera;
import object.entity.entity.IEntity;
import renderer.viewCulling.frustum.Frustum;
import tool.manager.AbstractManager;
import tool.math.Maths;

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
 * @version 1.0.1
 * 
 */
public class EntityManager extends AbstractManager<IEntity> implements IEntityManager {

	private Map<Float, List<IEntity>> frustumEntities = new HashMap<Float, List<IEntity>>();
	private List<IEntity> pointedEntities = new ArrayList<IEntity>();
	private List<IEntity> editorEntities = new ArrayList<IEntity>();

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
	public void updateWithFrustum(Frustum frustum, ICamera camera) {
		this.frustumEntities.clear();
		this.frustumEntities.putAll(this.getAll().stream()
				.filter(entity -> checkOnDistance(entity, camera))
				.filter(entity -> checkOnFrustum(entity, frustum))
				.map(entity -> new EDPair(entity, Maths.distanceFromCameraWithRadius(entity, camera)))
				.collect(Collectors.groupingBy(
						EDPair::getDistance,
						Collectors.mapping(
								EDPair::getEntity, Collectors.toList()))));
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
	
	private boolean checkOnFrustum(IEntity entity, Frustum frustum) {
		return frustum.sphereInFrustum(entity.getPosition(), entity.getSphereRadius());
	}
	
	private boolean checkOnDistance(IEntity entity, ICamera camera) {
		float distance = Maths.distance2Points(entity.getPosition(), camera.getPosition());
		switch(entity.getType()) {
			case EngineSettings.ENTITY_TYPE_SIMPLE:
				return distance <= EngineSettings.RENDERING_VIEW_DISTANCE;
			case EngineSettings.ENTITY_TYPE_NORMAL:
				return distance <= EngineSettings.RENDERING_VIEW_DISTANCE;
			case EngineSettings.ENTITY_TYPE_DECORATE:
				return distance <= EngineSettings.RENDERING_VIEW_DISTANCE/4;
			default:
				return false;
		}
	}


}
