package tool.math.vector;

public class Vector2f extends Vector {
	
	public float x;
	public float y;
	
	public Vector2f() {
		this.setX(0);
		this.setY(0);
	}
	
	public Vector2f(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	
	public Vector2f(Vector2f v) {
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public float length() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}
	
	public Vector2f normalize()	{
		float length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2f add(Vector2f r) {
		x += r.x;
		y += r.y;
		
		return this;
	}
	
	public Vector2f add(float r) {
		x += r;
		y += r;
		
		return this;
	}
	
	public static Vector2f add(Vector2f l, Vector2f r) {
		return new Vector2f(l.x + r.x, l.y + r.y);
	}
	
	public Vector2f sub(Vector2f r) {
		this.x -= r.getX();
		this.y -= r.getY();
		
		return this;
	}
	
	public Vector2f sub(float r) {
		this.x -= r;
		this.y -= r;
		
		return this;
	}
	
	public static Vector2f sub(Vector2f l, Vector2f r) {
		return new Vector2f(l.x - r.x, l.y - r.y);
	}
	
	public Vector2f mul(Vector2f r)	{
		x *= r.getX();
		y *= r.getY();
		
		return this;
	}
	
	public Vector2f mul(float r) {
		x *= r;
		y *= r;
		
		return this;
	}
	
	public static Vector2f mul(Vector2f l, Vector2f r) {		
		return new Vector2f(l.x * r.x, l.y * r.y);
	}
	
	public Vector2f div(Vector2f r) {
		this.x = this.x / r.x;
		this.y = this.y / r.y;
		
		return this;
	}
	
	public Vector2f div(float r) {
		setX(this.x / r);
		setY(this.y / r);
		
		return this;
	}
	
	public static Vector2f div(Vector2f l, Vector2f r) {
		return new Vector2f(l.x / r.x, l.y / r.y);
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
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
	
	@Override
	public Vector2f clone() {
		return new Vector2f(x, y);		
	}
	
}
