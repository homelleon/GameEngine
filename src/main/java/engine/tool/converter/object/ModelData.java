package tool.converter.object;

import java.util.ArrayList;
import java.util.List;

public class ModelData {

	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private int[] indices;
	private float furthestPoint;
	private float[] tangents;

	public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices, float furthestPoint) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.furthestPoint = furthestPoint;
	}
	
	public ModelData(float[] vertices, float[] textureCoords, float[] normals, float[] tangents, int[] indices) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.tangents = tangents;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public int[] getIndices() {
		return indices;
	}	

	public float[] getTangents() {
		return tangents;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}
	
	@Override
	public String toString() {
		List<Float> vertList = new ArrayList<Float>();
		for(int i = 0; i < vertices.length; i++) {
			vertList.add(vertices[i]);
		}
		return (this.getClass().getName() + "@vertices:" + vertList +
				"; textureCoords:" + this.textureCoords.toString() +
				"; normals:" + this.normals.toString() + 
				"; indices:" + this.indices.toString() + 
				"; furthestPoint:" + this.furthestPoint +
				"; tangents:" + this.tangents.toString());
	}

}