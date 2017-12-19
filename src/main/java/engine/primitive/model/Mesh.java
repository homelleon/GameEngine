package primitive.model;

import object.bounding.BoundingBox;
import object.bounding.BoundingSphere;
import primitive.buffer.VAO;

public class Mesh {

	private int vertexCount;
	private BoundingSphere sphere;
	private BoundingBox box;
	private String name;
	private VAO vao;
	
	public Mesh(String name, VAO vao, int vertexCount, BoundingSphere sphere, BoundingBox box) {
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.sphere = sphere;
		this.box = box;
		this.name = name;
	}
	
	public Mesh(String name, Mesh mesh) {
		this.vao = mesh.getVAO();
		this.vertexCount = mesh.getVertexCount();
		this.sphere = mesh.getBSphere();
		this.box = mesh.getBBox();
		this.name = name;
	}

	public Mesh(VAO vao, int vertexCount, BoundingSphere sphere, BoundingBox box) {
		this.vao = vao;
		this.vertexCount = vertexCount;
		this.sphere = sphere;
		this.box = box;
		this.name = "NoName";
	}

	public Mesh(VAO vao, int vertexCount) {
		this.vao = vao;
		this.vertexCount = vertexCount;
	}

	public String getName() {
		return this.name;
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
		Mesh rawModel = (Mesh) obj;
		if(rawModel.getVAO() == this.getVAO() &&
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
		result = prime * result + vao.hashCode();
		result = prime * result + vertexCount;
		result = prime * result + sphere.hashCode();
		result = prime * result + box.hashCode();
		return result;
	}
	
	public Mesh clone(String name) {
		return new Mesh(name, this.vao, this.vertexCount, this.sphere, this.box);
	}

	public VAO getVAO() {
		return vao;
	}

	public void setVAO(VAO vao) {
		this.vao = vao;
	}

}
