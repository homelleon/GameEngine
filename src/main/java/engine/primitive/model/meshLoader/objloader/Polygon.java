package primitive.model.meshLoader.objloader;

import java.util.ArrayList;

public class Polygon {

	private ArrayList<Vertex> vertices;
	private ArrayList<Integer> indices;
	private String material = null;
	
	protected Polygon(){
		vertices = new ArrayList<Vertex>();
		indices = new ArrayList<Integer>();
	}
	
	protected String getMaterial() {
		return material;
	}
	
	protected void setMaterial(String material) {
		this.material = material;
	}

	protected ArrayList<Vertex> getVertices() {
		return vertices;
	}

	protected void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	protected ArrayList<Integer> getIndices() {
		return indices;
	}

	protected void setIndices(ArrayList<Integer> indices) {
		this.indices = indices;
	}
}
