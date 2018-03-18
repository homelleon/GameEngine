package manager.entity;

import java.util.Collection;
import java.util.List;

import object.entity.Entity;
import tool.manager.IManager;

/**
 * Entity control manager interface.
 * 
 * @author homelleon
 * @version 1.0
 * @see EntityManager
 */
public interface IEntityManager extends IManager<Entity> {
	
	/**
	 * Add list of entities chosen by player.
	 * 
	 * @param pointedList
	 *            {@link Collection}<{@link Entity}> value of {@link Entity}
	 *            list
	 * 
	 * @see #addAll(Collection)
	 * @see #add(IEntity)
	 * @see #getAll()
	 * @see #get(String)
	 */
	void addPointedList(Collection<Entity> pointedList);

	/**
	 * Set default entities for editor list.
	 * 
	 * @param editorList
	 *            {@link Collection}<{@link Entity}> value of {@link Entity}
	 *            list
	 * @see #addForEditor(IEntity)
	 * 
	 */
	void setEditorList(List<Entity> editorList);

	/**
	 * Add one entity chosen by player into the pointed entity array.
	 * 
	 * @param entity
	 *            {@link Entity} value
	 */
	void addPointed(Entity entity);

	/**
	 * Add one entity in array of default entities for editor menu.
	 * 
	 * @param entity
	 *            {@link Entity} value
	 */
	void addForEditor(Entity entity);

	/**
	 * Returns list of entities chosen by player.
	 * 
	 * @return {@link List}<{@link Entity}> value of entities chosen by player
	 */
	List<Entity> getPointed();

	/**
	 * Returns list of default entities for editor menu.
	 * 
	 * @return {@link List}<{@link Entity}> value of entities for editor menu
	 */
	List<Entity> getForEditor();

	/**
	 * Returns entity of defalut entity from entity array for editor menu chosen
	 * by its index.
	 * 
	 * @return {@link IEntity} value
	 */
	Entity getForEditorByIndex(int index);

	/**
	 * Clear array of entities chosen by player.
	 */
	void clearPointed();
}
