package object.entity.entity;

import object.model.TexturedModel;
import tool.EngineUtils;

public class SimpleEntityBuilder extends EntityBuilder implements IEntityBuilder {

	@Override
	public IEntity createEntity(String name) {
		if(textureName!=null) {
			TexturedModel staticModel = EngineUtils.loadStaticModel(this.modelName, this.textureName);
			return new TexturedEntity(name, staticModel, this.position, this.rotation, this.scale);
		} else {
			throw new NullPointerException("Texture is not initialized for entity " + name + " in entity builder");
		}
	}

}
