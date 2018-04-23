package object.entity;

import java.util.ArrayList;
import java.util.List;

import primitive.model.Model;
import shader.Shader;
import shader.ShaderPool;
import tool.math.vector.Vector3f;

public class EntityBuilder {
	
	private List<Model> models = new ArrayList<Model>();
	private float scale = 1.0f;
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f rotation = new Vector3f(0,0,0);
	private int textureIndex = 0;
	
	public EntityBuilder setModel(Model model) {
		models.add(model);
		return this;
	}

	public EntityBuilder setScale(float scale) {
		this.scale = scale;
		return this;
	}

	public EntityBuilder setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	public EntityBuilder setRotation(Vector3f rotation) {
		this.rotation = rotation;
		return this;
	}
	
	public EntityBuilder setTextureIndex(int index) {
		this.textureIndex = index;
		return this;
	}

	public Entity build(String name) {
		if (models.isEmpty())
			throw new NullPointerException("No model defined for entity builder!");
		
		int shaderType = models.get(0).getMaterial().getNormalMap() != null ?
			Shader.NORMAL_ENTITY :
			Shader.ENTITY;
		Shader shader = ShaderPool.getInstance().get(shaderType);
		return new Entity(name, shader, models, textureIndex, position, rotation, new Vector3f(scale, scale, scale));

	}
	
}