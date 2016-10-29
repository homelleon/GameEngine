package models;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private String name;
	
	public RawModel(String name, int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.name = name;
	}
	
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.name = "NoName";
	}

	public String getName() {
		return name;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

}
