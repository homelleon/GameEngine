package object.model.raw;

import object.bounding.BoundingBox;
import object.bounding.BoundingSphere;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	private BoundingSphere sphere;
	private BoundingBox box;
	private String name;

	public RawModel(String name, int vaoID, int vertexCount, BoundingSphere sphere, BoundingBox box) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.sphere = sphere;
		this.box = box;
		this.name = name;
	}
	
	public RawModel(String name, RawModel model) {
		this.vaoID = model.getVaoID();
		this.vertexCount = model.getVertexCount();
		this.sphere = model.getBSphere();
		this.box = model.getBBox();
		this.name = name;
	}

	public RawModel(int vaoID, int vertexCount, BoundingSphere sphere, BoundingBox box) {
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj.hashCode() != this.hashCode()) {
			return false;
		}
		if(obj == this) {
			return true;
		}
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		RawModel rawModel = (RawModel) obj;
		if(rawModel.getVaoID() == this.getVaoID() &&
				rawModel.getVertexCount() == this.getVertexCount() &&
				rawModel.getBBox() == this.getBBox() &&
				rawModel.getBSphere() == this.getBSphere()) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 33;
		int result = 1;
		result = prime * result + vaoID;
		result = prime * result + vertexCount;
		result = prime * result + sphere.hashCode();
		result = prime * result + box.hashCode();
		return result;
	}
	
	public RawModel clone(String name) {
		return new RawModel(name, this.vaoID, this.vertexCount, this.sphere, this.box);
	}

}
