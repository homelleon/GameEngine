package object.entity.entity;

import core.settings.EngineSettings;
import object.entity.BaseEntity;
import primitive.model.Model;
import tool.math.vector.Vector3fF;

/*
 * EntityTextured - объект с текстурой
 * 03.02.17
 * ------------
 */
/**
 * Textured type of in-game entity.
 * 
 * @author homelleon
 *
 */

public class NormalMappedEntity extends BaseEntity implements IEntity {
	
	/**
	 * 
	 * @param name
	 * @param model
	 * @param position
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scale
	 */
	public NormalMappedEntity(String name, Model model, Vector3fF position, Vector3fF rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_NORMAL, model, position, rotation, scale);
	}
	
	/**
	 * 
	 * @param name
	 * @param model
	 * @param textureIndex
	 * @param position
	 * @param rotation
	 * @param scale
	 */
	public NormalMappedEntity(String name, Model model, int textureIndex, Vector3fF position, Vector3fF rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_NORMAL, model, textureIndex, position, rotation, scale);
	} 
	
	@Override
	public IEntity clone(String name) {
		IEntity entity = new NormalMappedEntity(name, this.model, this.textureIndex, this.position, this.rotation, this.scale);
		entity.setBaseName(this.getName());
		return entity;
	}


}
