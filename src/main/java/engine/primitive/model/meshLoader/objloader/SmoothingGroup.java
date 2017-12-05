package primitive.model.meshLoader.objloader;

import java.util.ArrayList;

public class SmoothingGroup {
	
	private ArrayList<Face> faces;
	private ArrayList<Vertex> vertices;
	
	protected SmoothingGroup() {
		faces = new ArrayList<Face>();
		vertices = new ArrayList<Vertex>();
	}
	
	protected ArrayList<Vertex> getVertices() {
		return vertices;
	}
	protected void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	protected ArrayList<Face> getFaces() {
		return faces;
	}

	protected void setFaces(ArrayList<Face> faces) {
		this.faces = faces;
	}
}
