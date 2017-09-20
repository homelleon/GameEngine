package object.entity.entity;

import core.settings.EngineSettings;
import object.entity.BaseEntity;
import object.model.textured.TexturedModel;
import tool.math.vector.Vec3f;

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

public class TexturedEntity extends BaseEntity implements IEntity {
	
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
	public TexturedEntity(String name, TexturedModel model, Vec3f position, Vec3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_SIMPLE, model, position, rotation, scale);
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
	public TexturedEntity(String name, TexturedModel model, int textureIndex, Vec3f position, Vec3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_SIMPLE, model, textureIndex, position, rotation, scale);
	} 
	
	@Override
	public IEntity clone(String name) {
		IEntity entity = new TexturedEntity(name, this.model, this.textureIndex, this.position, this.rotation, this.scale);
		entity.setBaseName(this.getName());
		return entity;
	}


}
