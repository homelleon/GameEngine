package tool.math;


import tool.math.vector.Vec3f;

public class Quaternion {

	public float x, y, z, w;

	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		normalize();
	}

	public void normalize() {
		float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
	}
	
	public Quaternion conjugate()
	{
		return new Quaternion (-x, -y, -z, w);
	}
	
	public Quaternion mul(Quaternion r)
	{
		float w_ = w * r.w - x * r.x - y * r.y - z * r.z;
		float x_ = x * r.w + w * r.x + y * r.z - z * r.y;
		float y_ = y * r.w + w * r.y + z * r.x - x * r.z;
		float z_ = z * r.w + w * r.z + x * r.y - y * r.x;

		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion mul(Vec3f r)
	{
		float w_ = -x * r.x - y * r.y - z * r.z;
		float x_ =  w * r.x + y * r.z - z * r.y;
		float y_ =  w * r.y + z * r.x - x * r.z;
		float z_ =  w * r.z + x * r.y - y * r.x;

		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion div(float r)
	{
		float w_ = w/r;
		float x_ = x/r;
		float y_ = y/r;
		float z_ = z/r;
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion mul(float r)
	{
		float w_ = w*r;
		float x_ = x*r;
		float y_ = y*r;
		float z_ = z*r;
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion sub(Quaternion r)
	{
		float w_ = w - r.w;
		float x_ = x - r.x;
		float y_ = y - r.y;
		float z_ = z - r.z;
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion add(Quaternion r)
	{
		float w_ = w + r.w;
		float x_ = x + r.x;
		float y_ = y + r.y;
		float z_ = z + r.z;
		return new Quaternion(x_, y_, z_, w_);
	}

	public Matrix4f toRotationMatrix() {
		Matrix4f matrix = new Matrix4f();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float xSquared = x * x;
		final float ySquared = y * y;
		final float zSquared = z * z;
		matrix.m[0][0] = 1 - 2 * (ySquared + zSquared);
		matrix.m[0][1] = 2 * (xy - zw);
		matrix.m[0][2] = 2 * (yz + xw);
		matrix.m[0][3] = 0;
		matrix.m[1][0] = 2 * (xy + zw);
		matrix.m[1][1] = 1 - 2 * (xSquared + zSquared);
		matrix.m[1][2] = 2 * (yz - xw);
		matrix.m[1][3] = 0;
		matrix.m[2][0] = 2 * (xz - yw);
		matrix.m[2][1] = 2 * (yz + xw);
		matrix.m[2][2] = 1 - 2 * (xSquared + ySquared);
		matrix.m[2][3] = 0;
		matrix.m[3][0] = 0;
		matrix.m[3][1] = 0;
		matrix.m[3][2] = 0;
		matrix.m[3][3] = 1;
		return matrix;
	}

	public static Quaternion fromMatrix(Matrix4f matrix) {
		float w, x, y, z;
		float diagonal = matrix.m[0][0] + matrix.m[1][1] + matrix.m[2][2];
		if (diagonal > 0) {
			float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
			w = w4 / 4f;
			x = (matrix.m[2][1] - matrix.m[1][2]) / w4;
			y = (matrix.m[0][2] - matrix.m[2][0]) / w4;
			z = (matrix.m[1][0] - matrix.m[0][1]) / w4;
		} else if ((matrix.m[0][0] > matrix.m[1][1]) && (matrix.m[0][0] > matrix.m[2][2])) {
			float x4 = (float) (Math.sqrt(1f + matrix.m[0][0] - matrix.m[1][1] - matrix.m[2][2]) * 2f);
			w = (matrix.m[2][1] - matrix.m[1][2]) / x4;
			x = x4 / 4f;
			y = (matrix.m[0][1] + matrix.m[1][0]) / x4;
			z = (matrix.m[0][2] + matrix.m[2][0]) / x4;
		} else if (matrix.m[1][1] > matrix.m[2][2]) {
			float y4 = (float) (Math.sqrt(1f + matrix.m[1][1] - matrix.m[0][0] - matrix.m[2][2]) * 2f);
			w = (matrix.m[0][2] - matrix.m[2][0]) / y4;
			x = (matrix.m[0][1] + matrix.m[1][0]) / y4;
			y = y4 / 4f;
			z = (matrix.m[1][2] + matrix.m[2][1]) / y4;
		} else {
			float z4 = (float) (Math.sqrt(1f + matrix.m[2][2] - matrix.m[0][0] - matrix.m[1][1]) * 2f);
			w = (matrix.m[1][0] - matrix.m[0][1]) / z4;
			x = (matrix.m[0][2] - matrix.m[2][0]) / z4;
			y = (matrix.m[1][2] - matrix.m[2][1]) / z4;
			z = z4 / 4f;
		}
		return new Quaternion(x, y, z, w);
	}

	public static Quaternion interpolate(Quaternion a, Quaternion b, float blend) {
		Quaternion result = new Quaternion(0, 0, 0, 1);
		float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
		float blendI = 1f - blend;
		if (dot < 0) {
			result.w = blendI * a.w + blend * -b.w;
			result.x = blendI * a.x + blend * -b.x;
			result.y = blendI * a.y + blend * -b.y;
			result.z = blendI * a.z + blend * -b.z;
		} else {
			result.w = blendI * a.w + blend * b.w;
			result.x = blendI * a.x + blend * b.x;
			result.y = blendI * a.y + blend * b.y;
			result.z = blendI * a.z + blend * b.z;
		}
		result.normalize();
		return result;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

}
