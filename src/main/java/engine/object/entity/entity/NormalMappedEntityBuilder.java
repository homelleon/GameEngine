package object.entity.entity;

import object.model.textured.TexturedModel;
import tool.EngineUtils;

public class NormalMappedEntityBuilder extends EntityBuilder implements IEntityBuilder {

	@Override
	public IEntity createEntity(String name) {
		if(textureName!=null && normalTextureName!=null && specularTextureName!=null) {
			TexturedModel staticModel = EngineUtils.loadStaticModel(name, textureName, normalTextureName, specularTextureName);
			staticModel.getTexture().setShineDamper(shiness);
			staticModel.getTexture().setReflectivity(reflectivity);
			return new NormalMappedEntity(name, staticModel, position, rotation, scale);
		} else {
			throw new NullPointerException("Some textures are not initialized for entity " + name + " in entity builder");
		}
	}

}
