package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import object.entity.entity.IEntity;
import tool.manager.AbstractManager;

/**
 * Manages entities in the game engine.
 * <p>
 * Can differ entities that are situated in the frustum. Also can store entities
 * chosen by player and entities for editor menu.
 * 
 * @author homelleon
 * @version 1.0.1
 * @see IEntityManager
 * @see AbstactManager
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
		this.editorEntities.clear();
	}


}
