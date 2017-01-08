package models;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private float radius;
	private String name;
	
	public RawModel(String name, int vaoID, int vertexCount, float radius) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.radius = radius;
		this.name = name;
	}
	
	public RawModel(int vaoID, int vertexCount, float radius) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.radius = radius;
		this.name = "NoName";
	}

	public String getName() {
		return this.name;
	}

	public int getVaoID() {
		return this.vaoID;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}
	
	public float getRadius() {
		return this.radius;
	}

}
