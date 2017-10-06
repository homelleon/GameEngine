package primitive.model.meshLoader.objloader;

import tool.math.vector.Vector2f;
import tool.math.vector.Vector3f;

public class Vertex {

	public static final int BYTES = 14 * Float.BYTES;
	public static final int FLOATS = 14;
	
	private Vector3f pos;
	private Vector3f normal;
	private Vector2f textureCoord;
	private Vector3f tangent;
	private Vector3f bitangent;
	
	public Vertex(){	
	}
	
	public Vertex(Vector3f pos)
	{
		this.setPos(pos);
		this.setTextureCoord(new Vector2f(0,0));
		this.setNormal(new Vector3f(0,0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texture)
	{
		this.setPos(pos);
		this.setTextureCoord(texture);
		this.setNormal(new Vector3f(0,0,0));
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector2f getTextureCoord() {
		return textureCoord;
	}

	public void setTextureCoord(Vector2f texture) {
		this.textureCoord = texture;
	}


	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public Vector3f getTangent() {
		return tangent;
	}

	public void setTangent(Vector3f tangent) {
		this.tangent = tangent;
	}

	public Vector3f getBitangent() {
		return bitangent;
	}

	public void setBitangent(Vector3f bitangent) {
		this.bitangent = bitangent;
	}
}