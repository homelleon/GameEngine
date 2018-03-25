package tool.math.vector;

/**
 * Builder class for Vector3i with int values.
 * 
 * @author homelleon
 * @version 1.0
 * @see VectorBuilder3
 */
public class VectorBuilder3i implements VectorBuilder3<Integer, Vector3i> {
	
	private int x;
	private int y;
	private int z;

	@Override
	public VectorBuilder3<Integer, Vector3i> setX(Integer x) {
		this.x = x;
		return this;
	}

	@Override
	public VectorBuilder3<Integer, Vector3i> setY(Integer y) {
		this.y = y;
		return this;
	}

	@Override
	public VectorBuilder3<Integer, Vector3i> setZ(Integer z) {
		this.z = z;
		return this;
	}

	@Override
	public Vector3i build() {
		return new Vector3i(x,y,z);
	}

	@Override
	public VectorBuilder3<Integer, Vector3i> set(Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

}
