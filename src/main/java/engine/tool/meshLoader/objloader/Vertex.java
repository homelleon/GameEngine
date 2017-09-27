package tool.meshLoader.objloader;

import tool.math.vector.Vector2f;
import tool.math.vector.Vector3fF;

public class Vertex {

	public static final int BYTES = 14 * Float.BYTES;
	public static final int FLOATS = 14;
	
	private Vector3fF pos;
	private Vector3fF normal;
	private Vector2f textureCoord;
	private Vector3fF tangent;
	private Vector3fF bitangent;
	
	public Vertex(){	
	}
	
	public Vertex(Vector3fF pos)
	{
		this.setPos(pos);
		this.setTextureCoord(new Vector2f(0,0));
		this.setNormal(new Vector3fF(0,0,0));
	}
	
	public Vertex(Vector3fF pos, Vector2f texture)
	{
		this.setPos(pos);
		this.setTextureCoord(texture);
		this.setNormal(new Vector3fF(0,0,0));
	}

	public Vector3fF getPos() {
		return pos;
	}

	public void setPos(Vector3fF pos) {
		this.pos = pos;
	}

	public Vector2f getTextureCoord() {
		return textureCoord;
	}

	public void setTextureCoord(Vector2f texture) {
		this.textureCoord = texture;
	}


	public Vector3fF getNormal() {
		return normal;
	}

	public void setNormal(Vector3fF normal) {
		this.normal = normal;
	}

	public Vector3fF getTangent() {
		return tangent;
	}

	public void setTangent(Vector3fF tangent) {
		this.tangent = tangent;
	}

	public Vector3fF getBitangent() {
		return bitangent;
	}

	public void setBitangent(Vector3fF bitangent) {
		this.bitangent = bitangent;
	}
}
