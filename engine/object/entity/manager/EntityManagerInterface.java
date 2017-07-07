package object.entity.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import object.entity.entity.EntityInterface;
import renderer.viewCulling.Frustum;

/**
 * Entity control manager interface.
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public interface EntityManagerInterface {
	
	/**
	 * Add entity list into entity array.
	 * 
	 * @param entityList
	 * 					 {@link Collection}<{@link EntityInterface}> value of 
	 * 					 {@link EntityInterface} list 
	 * @see #add(EntityInterface)
	 * @see #getAll()
	 * @see #getByName(String)
	 */
	void addAll(Collection<EntityInterface> entityList);
	
	/**
	 * Add list of entities chosen by player.
	 * 
	 * @param pointedList
	 * 					  {@link Collection}<{@link EntityInterface}> value of
	 * 					  {@link EntityInterface} list
	 * 
	 * @see #addAll(Collection)
	 * @see #add(EntityInterface)
	 * @see #getAll()
	 * @see #getByName(String)
	 */
	void addPointedList(Collection<EntityInterface> pointedList);
	
	/**
	 * Set default entities for editor list. 
	 * 
	 * @param editorList
	 * 					 {@link Collection}<{@link EntityInterface}> value of 
	 * 					 {@link EntityInterface} list
	 * @see #addForEditor(EntityInterface)
	 * 
	 */
	void setEditorList(List<EntityInterface> editorList);
	
	/**
	 * Add map of entitiese into the array of entities chosen by frustum 
	 * culling. 
	 * 
	 * @param frustumMap {@link Map}<{@link Float}, {@link List}<{@link EntityInterface}>>
	 * 					 value of frustum culled entities 
	 * @see #addForEditor(EntityInterface)
	 * @see #getForEditor()
	 */
	void addFrustumMap(Map<Float, List<EntityInterface>> frustumMap);
	
	/**
	 * Add one entity in entity array.
	 * 
	 * @param entity
	 * 				 {@link EntityInterface} value
	 */
	void add(EntityInterface entity); 
	
	/**
	 * Add one entity chosen by player into the pointed entity array.
	 * 
	 * @param entity
	 * 				 {@link EntityInterface} value
	 */
	void addPointed(EntityInterface entity);
	
	/**
	 * Add one entity in array of default entities for editor menu.
	 * 
	 * @param entity
	 * 				 {@link EntityInterface} value
	 */
	void addForEditor(EntityInterface entity);
	
	/**
	 * Add one entity into the entity array chosen by frustum culling.
	 * 
	 * @param distance
	 * 				   {@link Float} value of distance from camera to entity
	 * @param entity
	 * 				   {@link EntityInterface} value
	 */
	void addInFrustum(float distance, EntityInterface entity);
	
	/**
	 * Update entity array using frustum culling technic. 
	 *  
	 * @param frustum
	 * 				  {@link Frustum} value of frustum pyramid
	 */
	void updateWithFrustum(Frustum frustum);
	
	/**
	 * Returns entity from entity array by name.
	 * 
	 * @param name
	 * 			   String value of entity's name
	 * @return     {@link EntityInterface} value of chosen entity
	 */
	EntityInterface getByName(String name);
	
	/**
	 * Returns all entities in array list.
	 *  
	 * @return {@link Collection}<{@link EntityInterface}> value of entity list 
	 */
	Collection<EntityInterface> getAll();
	
	/**
	 * Returns map of entities chosen by frustum culling.  
	 * 
	 * @return {@link Map}<{@link Float},{@link List}<{@link EntityInterface}>> value of
	 * 		   entities chosen by frustum culling.
	 */
	Map<Float, List<EntityInterface>> getFromFrustum();
	
	/**
	 * Returns list of entities chosen by player.
	 * 
	 * @return {@link List}<{@link EntityInterface}> value of entities chosen by player
	 */
	List<EntityInterface> getPointed();
	
	/**
	 * Returns list of default entities for editor menu.
	 *  
	 * @return {@link List}<{@link EntityInterface}> value of entities for editor menu
	 */
	List<EntityInterface> getForEditor();
	
	/**
	 * Returns entity of defalut entity from entity array for editor menu
	 * chosen by its index.
	 * 
	 * @return {@link EntityInterface} value
	 */
	EntityInterface getForEditorByIndex(int index);
	
	/**
	 * Clear array of entities chosen by player.
	 */
	void clearPointed();
	
	/**
	 * Clear all arrays of entities.
	 */
	void clearAll();
}
