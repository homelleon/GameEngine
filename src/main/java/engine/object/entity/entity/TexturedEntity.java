package object.entity.entity;

import java.util.Collection;

import core.settings.EngineSettings;
import object.entity.BaseEntity;
import primitive.model.Model;
import tool.math.vector.Vector3f;

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
	public TexturedEntity(String name, Collection<Model> modelList, Vector3f position, Vector3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_SIMPLE, modelList, position, rotation, scale);
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
	public TexturedEntity(String name, Collection<Model> modelList, int textureIndex, Vector3f position, Vector3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_SIMPLE, modelList, textureIndex, position, rotation, scale);
	} 
	
	@Override
	public IEntity clone(String name) {
		IEntity entity = new TexturedEntity(name, this.models, this.textureIndex, new Vector3f(this.position), new Vector3f(this.rotation), this.scale);
		entity.setBaseName(this.getName());
		return entity;
	}


}
