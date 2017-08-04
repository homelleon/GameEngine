package object.entity.entity;

import object.model.TexturedModel;
import tool.EngineUtils;

public class SimpleEntityBuilder extends EntityBuilder implements EntityBuilderInterface {

	@Override
	public Entity createEntity(String name) {
		TexturedModel staticModel = EngineUtils.loadStaticModel(this.modelName, this.textureName);
		return new TexturedEntity(name, staticModel, this.position, this.rotation, this.scale);
	}

}
