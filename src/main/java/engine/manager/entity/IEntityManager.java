package manager.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import renderer.viewCulling.frustum.Frustum;
import tool.manager.IManager;

/**
 * Entity control manager interface.
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public interface IEntityManager extends IManager<IEntity> {


	/**
	 * Add list of entities chosen by player.
	 * 
	 * @param pointedList
	 *            {@link Collection}<{@link IEntity}> value of {@link IEntity}
	 *            list
	 * 
	 * @see #addAll(Collection)
	 * @see #add(IEntity)
	 * @see #getAll()
	 * @see #get(String)
	 */
	void addPointedList(Collection<IEntity> pointedList);

	/**
	 * Set default entities for editor list.
	 * 
	 * @param editorList
	 *            {@link Collection}<{@link IEntity}> value of {@link IEntity}
	 *            list
	 * @see #addForEditor(IEntity)
	 * 
	 */
	void setEditorList(List<IEntity> editorList);

	/**
	 * Add map of entitiese into the array of entities chosen by frustum
	 * culling.
	 * 
	 * @param frustumMap
	 *            {@link Map}<{@link Float}, {@link List}<{@link IEntity}>> value
	 *            of frustum culled entities
	 * @see #addForEditor(IEntity)
	 * @see #getForEditor()
	 */
	void addFrustumMap(Map<Float, List<IEntity>> frustumMap);

	/**
	 * Add one entity chosen by player into the pointed entity array.
	 * 
	 * @param entity
	 *            {@link IEntity} value
	 */
	void addPointed(IEntity entity);

	/**
	 * Add one entity in array of default entities for editor menu.
	 * 
	 * @param entity
	 *            {@link IEntity} value
	 */
	void addForEditor(IEntity entity);

	/**
	 * Add one entity into the entity array chosen by frustum culling.
	 * 
	 * @param distance
	 *            {@link Float} value of distance from camera to entity
	 * @param entity
	 *            {@link IEntity} value
	 */
	void addInFrustum(float distance, IEntity entity);

	/**
	 * Update entity array using frustum culling technic.
	 * 
	 * @param frustum
	 *            {@link Frustum} value of frustum pyramid
	 */
	void updateWithFrustum(Frustum frustum, ICamera camera);


	/**
	 * Returns map of entities chosen by frustum culling.
	 * 
	 * @return {@link Map}<{@link Float},{@link List}<{@link IEntity}>> value of
	 *         entities chosen by frustum culling.
	 */
	Map<Float, List<IEntity>> getFromFrustum();

	/**
	 * Returns list of entities chosen by player.
	 * 
	 * @return {@link List}<{@link IEntity}> value of entities chosen by player
	 */
	List<IEntity> getPointed();

	/**
	 * Returns list of default entities for editor menu.
	 * 
	 * @return {@link List}<{@link IEntity}> value of entities for editor menu
	 */
	List<IEntity> getForEditor();

	/**
	 * Returns entity of defalut entity from entity array for editor menu chosen
	 * by its index.
	 * 
	 * @return {@link IEntity} value
	 */
	IEntity getForEditorByIndex(int index);

	/**
	 * Clear array of entities chosen by player.
	 */
	void clearPointed();
}
