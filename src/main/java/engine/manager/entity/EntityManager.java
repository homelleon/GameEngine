package manager.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import object.entity.Entity;
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
public class EntityManager extends AbstractManager<Entity> {

	private List<Entity> pointedEntities = new ArrayList<Entity>();
	private List<Entity> editorEntities = new ArrayList<Entity>();
	
	public void addPointedList(Collection<Entity> pointedList) {
		pointedList.forEach(entity -> {
			entity.setChosen(true);
			this.pointedEntities.add(entity);
		});
	}

	public void setEditorList(List<Entity> editorList) {
		if (editorList != null) {
			this.editorEntities = editorList;
		}
	}
	
	public void addPointed(Entity entity) {
		if (entity != null) {
			entity.setChosen(true);
			this.pointedEntities.add(entity);
		}
	}

	public void addForEditor(Entity entity) {
		this.editorEntities.add(entity);
	}

	public List<Entity> getPointed() {
		return this.pointedEntities;
	}

	public List<Entity> getForEditor() {
		return this.editorEntities;
	}

	public Entity getForEditorByIndex(int index) {
		return this.editorEntities.get(index);
	}

	public void clearPointed() {
		this.pointedEntities
			.forEach(entity -> entity.setChosen(false));
		this.pointedEntities.clear();
	}

	public void clean() {
		super.clean();
		this.pointedEntities.clear();
		this.editorEntities.clear();
	}


}
