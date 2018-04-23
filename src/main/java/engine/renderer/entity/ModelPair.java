package renderer.entity;

import primitive.model.Mesh;
import primitive.texture.material.Material;

public class ModelPair {
	
	private Mesh mesh;
	private Material material;
	
	public ModelPair(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;		
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Material getMaterial() {
		return material;
	}	
	

}
