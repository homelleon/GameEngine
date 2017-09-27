package tool.meshLoader.normalMapObject;

import java.util.ArrayList;
import java.util.List;

import tool.math.vector.Vector3fF;

public class VertexNM {

	private static final int NO_INDEX = -1;

	private Vector3fF position;
	private int textureIndex = NO_INDEX;
	private int normalIndex = NO_INDEX;
	private VertexNM duplicateVertex = null;
	private int index;
	private float length;
	private List<Vector3fF> tangents = new ArrayList<Vector3fF>();
	private Vector3fF averagedTangent = new Vector3fF(0, 0, 0);

	protected VertexNM(int index, Vector3fF position) {
		this.index = index;
		this.position = position;
		this.length = position.length();
	}

	protected void addTangent(Vector3fF tangent) {
		tangents.add(tangent);
	}

	// NEW
	protected VertexNM duplicate(int newIndex) {
		VertexNM vertex = new VertexNM(newIndex, position);
		vertex.tangents = this.tangents;
		return vertex;
	}

	protected void averageTangents() {
		if (tangents.isEmpty()) {
			return;
		}
		for (Vector3fF tangent : tangents) {
			averagedTangent.add(tangent);
		}
		averagedTangent.normalize();
	}

	protected Vector3fF getAverageTangent() {
		return averagedTangent;
	}

	protected int getIndex() {
		return index;
	}

	protected float getLength() {
		return length;
	}

	protected boolean isSet() {
		return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
	}

	protected boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther) {
		return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
	}

	protected void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	protected void setNormalIndex(int normalIndex) {
		this.normalIndex = normalIndex;
	}

	protected Vector3fF getPosition() {
		return position;
	}

	protected int getTextureIndex() {
		return textureIndex;
	}

	protected int getNormalIndex() {
		return normalIndex;
	}

	protected VertexNM getDuplicateVertex() {
		return duplicateVertex;
	}

	protected void setDuplicateVertex(VertexNM duplicateVertex) {
		this.duplicateVertex = duplicateVertex;
	}

}
