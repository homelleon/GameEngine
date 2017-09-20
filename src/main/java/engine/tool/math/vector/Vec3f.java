package tool.math.vector;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import tool.math.Quaternion;

public class Vec3f extends Vec{
	
	public float x;
	public float y;
	public float z;
	
	public Vec3f()
	{
		this.setX(0);
		this.setY(0);
		this.setZ(0);
	}
	
	public Vec3f(float x, float y, float z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	public Vec3f(Vec3f v)
	{
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	public Vec3f(Vector4f plane) {
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
	public Vec3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	public Vector3f getVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
	public float dot(Vec3f r)
	{
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public static float dot(Vec3f vec, Vec3f r)
	{
		return vec.x * r.getX() + vec.y * r.getY() + vec.z * r.getZ();
	}
	
	public Vec3f cross(Vec3f r)
	{
		float x = this.y * r.getZ() - this.z * r.getY();
		float y = this.z * r.getX() - this.x * r.getZ();
		float z = this.x * r.getY() - this.y * r.getX();
		
		return new Vec3f(x,y,z);
	}
	
	public static Vec3f cross(Vec3f vec, Vec3f r) {
		float x = vec.y * r.z - vec.z * r.y;
		float y = vec.z * r.x - vec.x * r.z;
		float z = vec.x * r.y - vec.y * r.x;
		
		return new Vec3f(x,y,z);
	}
	
	public Vec3f scale(float scale) {

		x *= scale;
		y *= scale;
		z *= scale;

		return this;

	}
	
	public Vec3f normalize()
	{
		float length = this.length();
		
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vec3f rotate(float angle, Vec3f axis)
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
		
		x = w.x;
		y = w.y;
		z = w.z;
		
		return this;
	}
	
	public Vec3f add(Vec3f r)
	{
		return new Vec3f(this.x + r.getX(), this.y + r.getY(), this.z + r.getZ());
	}
	
	public static Vec3f add(Vec3f l, Vec3f r)
	{
		return new Vec3f(l.x + r.getX(), l.y + r.getY(), l.z + r.getZ());
	}
	
	public Vec3f add(float r)
	{
		return new Vec3f(this.x + r, this.y + r, this.z + r);
	}
	
	
	public Vec3f sub(Vec3f r)
	{
		return new Vec3f(this.x - r.getX(), this.y - r.getY(), this.z - r.getZ());
	}
	
	public Vec3f sub(float r)
	{
		return new Vec3f(this.x - r, this.y - r, this.z - r);
	}
	
	public static Vec3f sub(Vec3f l, Vec3f r)
	{
		return new Vec3f(l.x - r.getX(), l.y - r.getY(), l.z - r.getZ());
	}
	
	public Vec3f mul(Vec3f r)
	{
		return new Vec3f(this.x * r.getX(), this.y * r.getY(), this.z * r.getZ());
	}
	
	public Vec3f mul(float x, float y, float z)
	{
		return new Vec3f(this.x * x, this.y * y, this.z * z);
	}
	
	public Vec3f mul(float r)
	{
		return new Vec3f(this.x * r, this.y * r, this.z * r);
	}
	
	public Vec3f div(Vec3f r)
	{
		return new Vec3f(this.x / r.getX(), this.y / r.getY(), this.getZ() / r.getZ());
	}
	
	public Vec3f div(float r)
	{
		return new Vec3f(this.x / r, this.y / r, this.z / r);
	}
	
	public Vec3f abs()
	{
		return new Vec3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public boolean equals(Vec3f v)
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
