package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import core.settings.EngineSettings;
import object.camera.ICamera;
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
 * @version 1.0.1
 * 
 */
public class EntityManager extends AbstractManager<IEntity> implements IEntityManager {

	private List<IEntity> pointedEntities = new ArrayList<IEntity>();
	private List<IEntity> editorEntities = new ArrayList<IEntity>();

	@Override
	public void addPointedList(Collection<IEntity> pointedList) {
		pointedList.forEach(entity -> {
			entity.setChosen(true);
			this.pointedEntities.add(entity);
		});
	}

	@Override
	public void setEditorList(List<IEntity> editorList) {
		if (editorList != null) {
			this.editorEntities = editorList;
		}

	}


	@Override
	public void addPointed(IEntity entity) {
		if (entity != null) {
			entity.setChosen(true);
			this.pointedEntities.add(entity);
		}
	}

	@Override
	public void addForEditor(IEntity entity) {
		this.editorEntities.add(entity);
	}

	@Override
	public Map<Integer, List<IEntity>> updateWithFrustum(Frustum frustum, ICamera camera) {
		return this.getAll().parallelStream()
				.filter(entity -> checkVisibility(entity, frustum))
				.collect(Collectors.groupingBy(IEntity::getType));
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
			.forEach(entity -> entity.setChosen(false));
		this.pointedEntities.clear();
	}

	@Override
	public void clean() {
		super.clean();
		this.pointedEntities.clear();		
	}
	
	private boolean checkVisibility(IEntity entity, Frustum frustum) {
		float distance1 = 0;
		float distance2 = 0;
		switch(entity.getType()) {
		case EngineSettings.ENTITY_TYPE_SIMPLE:
			distance2 = EngineSettings.RENDERING_VIEW_DISTANCE;
			break;
		case EngineSettings.ENTITY_TYPE_NORMAL:
			distance2 = EngineSettings.RENDERING_VIEW_DISTANCE;
			break;
		case EngineSettings.ENTITY_TYPE_DECORATE:
			distance2 = EngineSettings.RENDERING_VIEW_DISTANCE/6;
			break;
		default:
			return false;
		}
		return frustum.sphereInFrustumAndDsitance(
				entity.getPosition(), entity.getSphereRadius(), distance1, distance2);	
	}


}
