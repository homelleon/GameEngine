package object.bounding;

import tool.math.Maths;
import tool.math.vector.Vector3fF;

public class BoundingSphere {

	private float radius;
	float[] positions;

	public BoundingSphere(float[] vertecies) {
		float maxRadius = 0;
		Vector3fF center = new Vector3fF(0, 0, 0);
		for (int i = 0; i < vertecies.length / 3; i++) {
			float x = vertecies[3 * i];
			float y = vertecies[3 * i + 1];
			float z = vertecies[3 * i + 2];
			Vector3fF point = new Vector3fF(x, y, z);
			float pointRadius = Maths.distance2Points(center, point);
			if (pointRadius > maxRadius) {
				maxRadius = pointRadius;
			}
		}
		this.radius = maxRadius;
	}

	public float getRadius() {
		return this.radius;
	}
}
