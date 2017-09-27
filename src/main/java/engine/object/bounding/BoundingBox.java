package object.bounding;

import primitive.buffer.Loader;
import primitive.model.Mesh;
import tool.math.vector.Vector3fF;

public class BoundingBox {

	Vector3fF min;
	Vector3fF max;
	Mesh model;

	public BoundingBox(float[] positions) {
		Vector3fF minPoint = new Vector3fF(0, 0, 0);
		Vector3fF maxPoint = new Vector3fF(0, 0, 0);
		for (int i = 0; i < positions.length / 3; i++) {
			float x = positions[3 * i];
			float y = positions[3 * i + 1];
			float z = positions[3 * i + 2];

			/* find minimum point */
			minPoint.x = Math.min(minPoint.x, x);
			minPoint.y = Math.min(minPoint.y, y);
			minPoint.z = Math.min(minPoint.z, z);

			/* find maximum point */
			maxPoint.x = Math.max(maxPoint.x, x);
			maxPoint.y = Math.max(maxPoint.y, y);
			maxPoint.z = Math.max(maxPoint.z, z);

		}

		this.max = maxPoint;
		this.min = minPoint;

		float[] boundPositions = {
				// front
				minPoint.x, maxPoint.y, maxPoint.z, maxPoint.x, maxPoint.y, maxPoint.z, maxPoint.x, minPoint.y,
				maxPoint.z, minPoint.x, minPoint.y, maxPoint.z,
				// back
				maxPoint.x, maxPoint.y, minPoint.z, minPoint.x, maxPoint.y, minPoint.z, minPoint.x, minPoint.y,
				minPoint.z, maxPoint.x, minPoint.y, minPoint.z,
				// top
				minPoint.x, maxPoint.y, minPoint.z, maxPoint.x, maxPoint.y, minPoint.z, maxPoint.x, maxPoint.y,
				maxPoint.z, minPoint.x, maxPoint.y, maxPoint.z,
				// bottom
				maxPoint.x, minPoint.y, minPoint.z, minPoint.x, minPoint.y, minPoint.z, minPoint.x, minPoint.y,
				maxPoint.z, maxPoint.x, minPoint.y, maxPoint.z,
				// left
				minPoint.x, maxPoint.y, minPoint.z, minPoint.x, maxPoint.y, maxPoint.z, minPoint.x, minPoint.y,
				maxPoint.z, minPoint.x, minPoint.y, minPoint.z,
				// right
				maxPoint.x, maxPoint.y, maxPoint.z, maxPoint.x, maxPoint.y, minPoint.z, maxPoint.x, minPoint.y,
				minPoint.z, maxPoint.x, minPoint.y, maxPoint.z };

		float[] boundNormals = { 0, 0, 1, 0, 0, 1, // front
				0, 0, 1, 0, 0, 1, // front
				0, 0, -1, 0, 0, -1, // back
				0, 0, -1, 0, 0, -1, // back
				0, 1, 0, 0, 1, 0, // top
				0, 1, 0, 0, 1, 0, // top
				0, -1, 0, 0, -1, 0, // bottom
				0, -1, 0, 0, -1, 0, // bottom
				-1, 0, 0, -1, 0, 0, // left
				-1, 0, 0, -1, 0, 0, // left
				1, 0, 0, 1, 0, 0, // right
				1, 0, 0, 1, 0, 0 // right
		};

		int[] boundIndices = { 0, 3, 1, 1, 3, 2, // front
				4, 7, 5, 5, 7, 6, // back
				8, 11, 9, 9, 11, 10, // top
				12, 15, 13, 13, 15, 14, // bottom
				16, 19, 17, 17, 19, 18, // left
				20, 23, 21, 21, 23, 22 // right
		};

		this.model = Loader.getInstance().getVertexLoader().loadToVao(boundPositions, boundNormals, boundIndices);
	}

	public Vector3fF getMin() {
		return this.min;
	}

	public Vector3fF getMax() {
		return this.max;
	}

	public Mesh getModel() {
		return this.model;
	}

}
