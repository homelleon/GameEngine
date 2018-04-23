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
		this.sphere = mesh.getBoundSphere();
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
		return name;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public BoundingSphere getBoundSphere() {
		return sphere;
	}

	public BoundingBox getBBox() {
		return box;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.hashCode() != this.hashCode()) return false;
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		
		Mesh rawModel = (Mesh) obj;
		return (rawModel.getVAO() == this.getVAO() &&
				rawModel.getVertexCount() == this.getVertexCount() &&
				rawModel.getBBox() == this.getBBox() &&
				rawModel.getBoundSphere() == this.getBoundSphere());
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
		return new Mesh(name, vao, vertexCount, sphere, box);
	}

	public VAO getVAO() {
		return vao;
	}

	public void setVAO(VAO vao) {
		this.vao = vao;
	}

}
