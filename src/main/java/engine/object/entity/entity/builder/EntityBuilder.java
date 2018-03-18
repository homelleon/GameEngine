package object.entity.entity.builder;

import java.util.ArrayList;
import java.util.List;

import object.entity.Entity;
import primitive.model.Model;
import shader.Shader;
import shader.ShaderPool;
import tool.math.vector.Vector3f;

public class EntityBuilder implements IEntityBuilder {
	
	private List<Model> models = new ArrayList<Model>();
	private float scale = 1.0f;
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f rotation = new Vector3f(0,0,0);
	private int textureIndex = 0;
	
	@Override
	public IEntityBuilder setModel(Model model) {
		this.models.add(model);
		return this;
	}

	@Override
	public IEntityBuilder setScale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public IEntityBuilder setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	@Override
	public IEntityBuilder setRotation(Vector3f rotation) {
		this.rotation = rotation;
		return this;
	}
	
	@Override
	public IEntityBuilder setTextureIndex(int index) {
		this.textureIndex = index;
		return this;
	}

	@Override
	public Entity build(String name) {
		if (!models.isEmpty()) {
			int shaderType = models.get(0).getMaterial().getNormalMap()!= null ?
				Shader.NORMAL_ENTITY :
				Shader.ENTITY;
			Shader shader = ShaderPool.getInstance().get(shaderType);
			return new Entity(name, shader, models, textureIndex, position, rotation, scale);
		} else {
			throw new NullPointerException("No model defined for entity builder!");
		}
	}
	
}