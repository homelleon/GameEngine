package scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import primitive.model.Model;
import shader.Shader;
import tool.math.vector.Vector;

public abstract class Drawable<T extends Vector> extends Subject<T> {
	
	protected Shader shader;
	protected List<Model> models = new ArrayList<Model>();
	protected T scale;
	
	protected Drawable(String name, Shader shader, List<Model> models) {
		super(name);
		this.shader = shader;
		
		if (models != null)
			this.models.addAll(models);
	}
	
	protected Drawable(String name, Shader shader, Collection<Model> models) {
		super(name);
		this.shader = shader;
		this.models.addAll(models);
	}
	
	public Shader getShader() {
		return shader;
	}
	
	public Drawable<T> setShader(Shader shader) {
		this.shader = shader;
		return this;
	}
	
	public List<Model> getModels() {
		return models;
	}
	
	public Drawable<T> addModels(List<Model> models) {
		this.models.addAll(models);
		return this;
	}
	
	public Drawable<T> addModel(Model model) {
		this.models.add(model);
		return this;
	}
	
	public T getScale() {
		return scale;
	}
	
	public Drawable<T> setScale(T scale) {
		this.scale = scale;
		return this;
	}
	
	
	
}
