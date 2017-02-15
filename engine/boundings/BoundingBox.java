package boundings;

import org.lwjgl.util.vector.Vector3f;

public class BoundingBox {
	
	Vector3f min;
	Vector3f max;
	float[] positions;
	
	public BoundingBox(float[] vertecies) {
		Vector3f minPoint = new Vector3f(0,0,0);
		Vector3f maxPoint = new Vector3f(0,0,0);
		for(int i = 0; i < vertecies.length / 3; i++) {
			float x = vertecies[3 * i];
			float y = vertecies[3 * i + 1];
			float z = vertecies[3 * i + 2];
			if(x < minPoint.x & y < minPoint.y & z < minPoint.z) {
				minPoint = new Vector3f(x, y, z);
			}
			
			if(x > minPoint.x & y > minPoint.y & z > minPoint.z) {
				maxPoint = new Vector3f(x, y, z);
			}
		}
		this.max = maxPoint;
		this.min = minPoint;
	}
	
	public Vector3f getMin() {
		return this.min;
	}
	
	public Vector3f getMax() {
		return this.max;
	}

}
