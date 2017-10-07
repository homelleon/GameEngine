package manager.entity;

import java.util.Collection;
import java.util.List;

import object.camera.ICamera;
import object.entity.entity.IEntity;
import tool.manager.IManager;
import tool.math.Frustum;

/**
 * Entity control manager interface.
 * 
 * @author homelleon
 * @version 1.0
 * @see EntityManager
 */
public interface IEntityManager extends IManager<IEntity> {

	void addEntityInNodes(IEntity entity);
	
	void setHasNodes(boolean value);
	
	void rebuildNodes(int size);
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
	 * Update entity array using frustum culling technic.
	 * 
	 * @param frustum
	 *            {@link Frustum} value of frustum pyramid
	 */
	List<IEntity> updateWithFrustum(Frustum frustum, ICamera camera, boolean isLow);

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
