package object.bounding;

import tool.math.Maths;
import tool.math.vector.Vector3f;

public class BoundingSphere {

	private float radius;

	public BoundingSphere(float[] vertecies) {
		float maxRadius = 0;
		Vector3f center = new Vector3f(0, 0, 0);
		for (int i = 0; i < vertecies.length / 3; i++) {
			float x = vertecies[3 * i];
			float y = vertecies[3 * i + 1];
			float z = vertecies[3 * i + 2];
			Vector3f point = new Vector3f(x, y, z);
			float pointRadius = Maths.distance2Points(center, point);
			if (pointRadius > maxRadius) {
				maxRadius = pointRadius;
			}
		}
		this.radius = maxRadius;
	}

	public float getRadius() {
		return radius;
	}
}
