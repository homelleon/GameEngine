package primitive.model;

import object.texture.material.Material;

public class Model {

	private String name;
	private Mesh mesh;
	private Material material;

	public Model(String name, Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Mesh getMesh() {
		return this.mesh;
	}

	public Material getMaterial() {
		return this.material;
	}

}
