package object.animation;


import tool.math.VMatrix4f;
import tool.math.Quaternion;
import tool.math.vector.Vector3fF;

public class JointTransform {

	private final Vector3fF position;
	private final Quaternion rotation;

	public JointTransform(Vector3fF position, Quaternion rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	protected VMatrix4f getLocalTransform() {
		VMatrix4f matrix = new VMatrix4f();
		matrix.translate(this.position);
		matrix.mul(matrix, rotation.toRotationMatrix());
		return matrix;
	}

	protected static JointTransform interpolate(JointTransform frameA, JointTransform frameB, float progression) {
		Vector3fF pos = interpolate(frameA.position, frameB.position, progression);
		Quaternion rot = Quaternion.interpolate(frameA.rotation, frameB.rotation, progression);
		return new JointTransform(pos, rot);
	}

	private static Vector3fF interpolate(Vector3fF start, Vector3fF end, float progression) {
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vector3fF(x, y, z);
	}

}
