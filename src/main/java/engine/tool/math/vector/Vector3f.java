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
	
	public Vector3f(Vector3f v)
	{
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	public Vector3f(Vector4f plane) {
		this.x = plane.x;
		this.y = plane.y;
		this.z = plane.z;
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
	
	public float dot(Vector3f r)
	{
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public static float dot(Vector3f vec, Vector3f r)
	{
		return vec.x * r.getX() + vec.y * r.getY() + vec.z * r.getZ();
	}
	
	public Vector3f cross(Vector3f r)
	{
		float x = this.y * r.getZ() - this.z * r.getY();
		float y = this.z * r.getX() - this.x * r.getZ();
		float z = this.x * r.getY() - this.y * r.getX();
		setX(x);
		setY(y);
		setZ(z);
		
		return this;
	}
	
	public static Vector3f cross(Vector3f vec, Vector3f r) {
		float x = vec.y * r.z - vec.z * r.y;
		float y = vec.z * r.x - vec.x * r.z;
		float z = vec.x * r.y - vec.y * r.x;
		
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
	
	public static Vector3f normalize(Vector3f vec)
	{
		float length = vec.length();
		
		vec.x /= length;
		vec.y /= length;
		vec.z /= length;
		
		return vec;
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
	
	public Vector3f add(Vector3f r)
	{
		this.x = this.x + r.x;
		this.y = this.y + r.y;
		this.z = this.z + r.z;
		
		return this;
	}
	
	public static Vector3f add(Vector3f l, Vector3f r)
	{
		return new Vector3f(l.x + r.x, l.y + r.y, l.z + r.z);
	}
	
	public Vector3f add(float r)
	{
		this.x = this.x + r;
		this.y = this.y + r;
		this.z = this.z + r;
		
		return this;
	}
	
	
	public Vector3f sub(Vector3f r)
	{
		this.x = this.x - r.x;
		this.y = this.y - r.y;
		this.z = this.z - r.z;
		
		return this;
	}
	
	public Vector3f sub(float r)
	{
		this.x = this.x - r;
		this.y = this.y - r;
		this.z = this.z - r;
		
		return this;
	}
	
	public static Vector3f sub(Vector3f l, Vector3f r)
	{
		return new Vector3f(l.x - r.x, l.y - r.y, l.z - r.z);
	}
	
	public Vector3f mul(Vector3f r)
	{
		this.x = this.x * r.x;
		this.y = this.y * r.y;
		this.z = this.z * r.z;
		
		return this;
	}
	
	public Vector3f mul(float x, float y, float z)
	{
		return new Vector3f(this.x * x, this.y * y, this.z * z);
	}
	
	public Vector3f mul(float r)
	{
		return new Vector3f(this.x * r, this.y * r, this.z * r);
	}
	
	public Vector3f div(Vector3f r)
	{
		return new Vector3f(this.x / r.getX(), this.y / r.getY(), this.getZ() / r.getZ());
	}
	
	public Vector3f div(float r)
	{
		return new Vector3f(this.x / r, this.y / r, this.z / r);
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
