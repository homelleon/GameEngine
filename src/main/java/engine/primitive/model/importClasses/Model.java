package primitive.model.importClasses;

import object.texture.material.Material;
import tool.meshLoader.objloader.MeshData;

public class Model {

	private MeshData mesh;
	private Material material;
	
	public Model(MeshData mesh)
	{
		this.mesh = mesh;
	}
	
	public Model() {
	}

	public MeshData getMesh() {
		return mesh;
	}
	public void setMesh(MeshData mesh) {
		this.mesh = mesh;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
