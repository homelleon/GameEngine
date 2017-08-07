package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.model.TexturedModel;

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
	public TexturedEntity(String name, TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
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
	public TexturedEntity(String name, TexturedModel model, int textureIndex, Vector3f position, Vector3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_SIMPLE, model, textureIndex, position, rotation, scale);
	} 
	
	@Override
	public IEntity clone(String name) {
		IEntity entity = new TexturedEntity(name, this.model, this.position, this.rotation, this.scale);
		return entity;
	}


}
