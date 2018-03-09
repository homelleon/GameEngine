package tool.math.vector;

public class Color {
	
	public static final float RGB_SIZE = 255;
	
	public float r;
	public float g;
	public float b;
	
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Color(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;		
	}
	
	public Color(Vector3f vector) {
		this.r = vector.x;
		this.g = vector.y;
		this.b = vector.z;
	}
	
	public Vector3f getVector() {
		return new Vector3f(r, g, b);
	}
	
	public Color getOGL() {		
		return new Color(r / RGB_SIZE, g / RGB_SIZE, b / RGB_SIZE);
	}
	
	public Vector3f getOGLVector() {
		return this.getOGL().getVector();
	}
	
	public Color getRGB() {
		return new Color(r * RGB_SIZE, g * RGB_SIZE, b * RGB_SIZE);
	}
	
	public Color clone() {
		return new Color(this);
	}

}
