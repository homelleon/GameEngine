package ru.homelleon.renderEngine;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	
	public RawModel(int vaoID, int vetexCount){
		
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

}
