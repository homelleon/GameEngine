package primitive.model.meshLoader.objloader;

public class MeshData{

	private Vertex[] vertices;
	private int[] indices;
	private int instances;
	private boolean isTangentSpace;
	private boolean isInstanced;
	
	protected MeshData(Vertex[] vertices, int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
	}

	protected Vertex[] getVertices() {
		return vertices;
	}

	protected void setVertices(Vertex[] vertices) {
		this.vertices = vertices;
	}

	protected int[] getIndices() {
		return indices;
	}

	protected void setIndices(int[] indices) {
		this.indices = indices;
	}

	protected boolean isInstanced() {
		return isInstanced;
	}

	protected void setInstanced(boolean instanced) {
		this.isInstanced = instanced;
	}

	protected int getInstances() {
		return instances;
	}

	protected void setInstances(int instances) {
		this.instances = instances;
	}

	protected boolean isTangentSpace() {
		return isTangentSpace;
	}

	protected void setTangentSpace(boolean isTangentSpace) {
		this.isTangentSpace = isTangentSpace;
	}
}
