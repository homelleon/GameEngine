package primitive.model.meshLoader.objloader;

public class Face {

	private int[] indices = new int[3];
	private String material;
	
	protected Face(){}

	protected int[] getIndices() {
		return indices;
	}

	protected void setIndices(int[] indices) {
		this.indices = indices;
	}

	protected String getMaterial() {
		return material;
	}

	protected void setMaterial(String material) {
		this.material = material;
	}
}
