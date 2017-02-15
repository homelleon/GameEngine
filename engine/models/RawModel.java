package models;

import boundings.BoundingBox;
import boundings.BoundingSphere;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private BoundingSphere sphere;
	private BoundingBox box;
	private String name;
	
	public RawModel(String name, int vaoID, int vertexCount, 
			BoundingSphere sphere, BoundingBox box) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.sphere = sphere;
		this.box = box;
		this.name = name;
	}
	
	public RawModel(int vaoID, int vertexCount, 
			BoundingSphere sphere, BoundingBox box) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.sphere = sphere;
		this.box = box;
		this.name = "NoName";
	}
	
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
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
	
	public BoundingSphere getBSphere() {
		return this.sphere;
	}
	
	public BoundingBox getBBox() {
		return this.box;
	}

}
