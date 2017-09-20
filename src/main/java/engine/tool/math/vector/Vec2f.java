package tool.math.vector;

import org.lwjgl.util.vector.Vector2f;

public class Vec2f extends Vec{
	
	public float x;
	public float y;
	
	public Vec2f()
	{
		this.setX(0);
		this.setY(0);
	}
	
	public Vec2f(float x, float y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public Vec2f(Vec2f v)
	{
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public float length()
	{
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public Vector2f getVector2f() {
		return new Vector2f(this.x, this.y);
	}
	
	public float dot(Vec2f r)
	{
		return x * r.getX() + y * r.getY();
	}
	
	public Vec2f normalize()
	{
		float length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vec2f add(Vec2f r)
	{
		return new Vec2f(this.x + r.getX(), this.y + r.getY());
	}
	
	public Vec2f add(float r)
	{
		return new Vec2f(this.x + r, this.y + r);
	}
	
	public static Vec2f add(Vec2f l, Vec2f r) {
		return new Vec2f(l.x + r.x, l.y + r.y);
	}
	
	public Vec2f sub(Vec2f r)
	{
		return new Vec2f(this.x - r.getX(), this.y - r.getY());
	}
	
	public Vec2f sub(float r)
	{
		return new Vec2f(this.x - r, this.y - r);
	}
	
	public Vec2f mul(Vec2f r)
	{
		return new Vec2f(this.x * r.getX(), this.y * r.getY());
	}
	
	public Vec2f mul(float r)
	{
		return new Vec2f(this.x * r, this.y * r);
	}
	
	public Vec2f div(Vec2f r)
	{
		return new Vec2f(this.x / r.getX(), this.y / r.getY());
	}
	
	public Vec2f div(float r)
	{
		return new Vec2f(this.x / r, this.y / r);
	}
	
	public String toString()
	{
		return "[" + this.x + "," + this.y + "]";
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		y = y;
	}
	
	
	
}
