package object.entity.entity;

import core.settings.EngineSettings;
import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;
import renderer.loader.Loader;
import tool.EngineUtils;
import tool.converter.object.ModelData;
import tool.converter.object.OBJFileLoader;

public class SimpleEntityBuilder extends EntityBuilder implements IEntityBuilder {

	@Override
	public IEntity createEntity(String name) {
		if(textureName!=null) {
			if(!this.modelTextureIsLoader) {
				this.texture = new ModelTexture(textureName, Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_MODEL_PATH, textureName));
			}
			if(!this.rawModelIsLoaded) {
				ModelData data = OBJFileLoader.loadOBJ(modelName);
				Loader loader = Loader.getInstance();
				this.rawModel = new RawModel(modelName, loader.getVertexLoader().loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
						data.getIndices()));		
			}
			TexturedModel staticModel = EngineUtils.loadStaticModel(this.rawModel.getName(), this.rawModel, this.texture);
			return new TexturedEntity(name, staticModel, this.position, this.rotation, this.scale);
		} else {
			throw new NullPointerException("Texture is not initialized for entity " + name + " in entity builder");
		}
	}

}
