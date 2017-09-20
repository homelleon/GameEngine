package object.animation;

import org.lwjgl.util.vector.Matrix4f;

import tool.math.Quaternion;
import tool.math.vector.Vec3f;

public class JointTransform {

	private final Vec3f position;
	private final Quaternion rotation;

	public JointTransform(Vec3f position, Quaternion rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	protected Matrix4f getLocalTransform() {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(this.position.getVector3f());
		Matrix4f.mul(matrix, rotation.toRotationMatrix(), matrix);
		return matrix;
	}

	protected static JointTransform interpolate(JointTransform frameA, JointTransform frameB, float progression) {
		Vec3f pos = interpolate(frameA.position, frameB.position, progression);
		Quaternion rot = Quaternion.interpolate(frameA.rotation, frameB.rotation, progression);
		return new JointTransform(pos, rot);
	}

	private static Vec3f interpolate(Vec3f start, Vec3f end, float progression) {
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vec3f(x, y, z);
	}

}
