package tool.math.vector;

import org.lwjgl.util.vector.Vector4f;

import tool.math.Quaternion;

public class Vector3f extends Vector{
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f()
	{
		this.setX(0);
		this.setY(0);
		this.setZ(0);
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	public Vector3f(Vector3f vector)
	{
		this.setX(vector.x);
		this.setY(vector.y);
		this.setZ(vector.z);
	}
	
	public Vector3f(Vector4f plane) {
		this.setX(plane.x);
		this.setY(plane.y);
		this.setZ(plane.z);
	}	
	
	public float length()
	{
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}
	
	/**
	 * Negate a vector
	 * @return this
	 */
	public Vector3f negate() {
		setX(-x);
		setY(-y);
		setZ(-z);
		
		return this;
	}
	
	public float dot(Vector3f vector)
	{
		return x * vector.x + y * vector.y + z * vector.z;
	}
	
	public static float dot(Vector3f left, Vector3f right)
	{
		return left.x * right.getX() + left.y * right.getY() + left.z * right.getZ();
	}
	
	public Vector3f cross(Vector3f vector)
	{
		float x = this.y * vector.z - this.z * vector.y;
		float y = this.z * vector.x - this.x * vector.z;
		float z = this.x * vector.y - this.y * vector.x;
		setX(x);
		setY(y);
		setZ(z);
		
		return this;
	}
	
	public static Vector3f cross(Vector3f left, Vector3f right) {
		float x = left.y * right.z - left.z * right.y;
		float y = left.z * right.x - left.x * right.z;
		float z = left.x * right.y - left.y * right.x;
		
		return new Vector3f(x,y,z);
	}
	
	public Vector3f scale(float scale) {

		x *= scale;
		y *= scale;
		z *= scale;

		return this;

	}
	
	public Vector3f normalize()
	{
		float length = this.length();
		
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vector3f normalize(Vector3f vector)
	{
		float length = this.length();
		
		vector.x /= length;
		vector.y /= length;
		vector.z /= length;
		
		return vector;
	}
	
	public Vector3f rotate(float angle, Vector3f axis)
	{
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
		
		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion w = rotation.mul(this).mul(conjugate);
		
		this.x = w.x;
		this.y = w.y;
		this.z = w.z;
		
		return this;
	}
	
	public Vector3f add(Vector3f vector)
	{
		this.x = this.x + vector.x;
		this.y = this.y + vector.y;
		this.z = this.z + vector.z;
		
		return this;
	}
	
	public static Vector3f add(Vector3f left, Vector3f right)
	{
		return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
	}
	
	public Vector3f add(float value)
	{
		this.x = this.x + value;
		this.y = this.y + value;
		this.z = this.z + value;
		
		return this;
	}
	
	
	public Vector3f sub(Vector3f vector)
	{
		this.x = this.x - vector.x;
		this.y = this.y - vector.y;
		this.z = this.z - vector.z;
		
		return this;
	}
	
	public Vector3f sub(float value)
	{
		this.x = this.x - value;
		this.y = this.y - value;
		this.z = this.z - value;
		
		return this;
	}
	
	public static Vector3f sub(Vector3f left, Vector3f right)
	{
		return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
	}
	
	public Vector3f mul(Vector3f vector)
	{
		this.x = this.x * vector.x;
		this.y = this.y * vector.y;
		this.z = this.z * vector.z;
		
		return this;
	}
	
	public Vector3f mul(float x, float y, float z)
	{
		return new Vector3f(this.x * x, this.y * y, this.z * z);
	}
	
	public Vector3f mul(float value)
	{
		return new Vector3f(this.x * value, this.y * value, this.z * value);
	}
	
	public Vector3f div(Vector3f vector)
	{
		return new Vector3f(this.x / vector.getX(), this.y / vector.getY(), this.getZ() / vector.getZ());
	}
	
	public Vector3f div(float value)
	{
		return new Vector3f(this.x / value, this.y / value, this.z / value);
	}
	
	public Vector3f abs()
	{
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public boolean equals(Vector3f v)
	{
		if (x == v.getX() && y == v.getY() && z == v.getZ())
			return true;
		else return false;
	}
	
	public String toString()
	{
		return "[" + this.x + "," + this.y + "," + this.z + "]";
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
	
	
	
}
