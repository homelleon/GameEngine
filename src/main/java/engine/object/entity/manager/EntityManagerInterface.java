package object.entity.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.Entity;
import renderer.viewCulling.frustum.Frustum;
import tool.manager.ManagerInterface;

/**
 * Entity control manager interface.
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public interface EntityManagerInterface extends ManagerInterface<Entity> {


	/**
	 * Add list of entities chosen by player.
	 * 
	 * @param pointedList
	 *            {@link Collection}<{@link Entity}> value of {@link Entity}
	 *            list
	 * 
	 * @see #addAll(Collection)
	 * @see #add(Entity)
	 * @see #getAll()
	 * @see #getByName(String)
	 */
	void addPointedList(Collection<Entity> pointedList);

	/**
	 * Set default entities for editor list.
	 * 
	 * @param editorList
	 *            {@link Collection}<{@link Entity}> value of {@link Entity}
	 *            list
	 * @see #addForEditor(Entity)
	 * 
	 */
	void setEditorList(List<Entity> editorList);

	/**
	 * Add map of entitiese into the array of entities chosen by frustum
	 * culling.
	 * 
	 * @param frustumMap
	 *            {@link Map}<{@link Float}, {@link List}<{@link Entity}>> value
	 *            of frustum culled entities
	 * @see #addForEditor(Entity)
	 * @see #getForEditor()
	 */
	void addFrustumMap(Map<Float, List<Entity>> frustumMap);

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
	 * Add one entity into the entity array chosen by frustum culling.
	 * 
	 * @param distance
	 *            {@link Float} value of distance from camera to entity
	 * @param entity
	 *            {@link Entity} value
	 */
	void addInFrustum(float distance, Entity entity);

	/**
	 * Update entity array using frustum culling technic.
	 * 
	 * @param frustum
	 *            {@link Frustum} value of frustum pyramid
	 */
	void updateWithFrustum(Frustum frustum);


	/**
	 * Returns map of entities chosen by frustum culling.
	 * 
	 * @return {@link Map}<{@link Float},{@link List}<{@link Entity}>> value of
	 *         entities chosen by frustum culling.
	 */
	Map<Float, List<Entity>> getFromFrustum();

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
	 * @return {@link Entity} value
	 */
	Entity getForEditorByIndex(int index);

	/**
	 * Clear array of entities chosen by player.
	 */
	void clearPointed();
}
