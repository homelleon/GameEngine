package renderer.entity;

import object.texture.material.Material;
import primitive.model.Mesh;

public class ModelPair {
	
	private Mesh mesh;
	private Material material;
	
	public ModelPair(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;		
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
	
	public Material getMaterial() {
		return this.material;
	}	
	

}
