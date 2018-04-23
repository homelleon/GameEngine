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
	private Vertex dublicateVertex = null;
	
	public Vertex(){}
	
	public Vertex(Vector3f pos)	{
		setPos(pos);
		setTextureCoord(new Vector2f(0,0));
		setNormal(new Vector3f(0,0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texture) {
		setPos(pos);
		setTextureCoord(texture);
		setNormal(new Vector3f(0,0,0));
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
	
	public void setDubilcateVertex(Vertex vertex) {
		this.dublicateVertex = vertex;
	}
	
	public Vertex getDublicateVertex() {
		return dublicateVertex;
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
	
	public boolean equals(Object obj) {
		if (this == obj) return true;		
		if (obj == null || getClass() != obj.getClass() || this.hashCode() != obj.hashCode()) return false;
		
		Vertex other = (Vertex) obj;
		return (this.getPos().equals(other.getPos()) &&
				this.getNormal().equals(other.getNormal()) &&
				this.getTextureCoord().equals(other.getTextureCoord()));
		
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getPos().hashCode();
		result = prime * result + this.getNormal().hashCode();
		result = prime * result + this.getTextureCoord().hashCode();
		return result;
	}
	
}