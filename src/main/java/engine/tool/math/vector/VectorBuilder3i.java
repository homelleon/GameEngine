package tool.math.vector;

/**
 * Builder class for Vector3i with int values.
 * 
 * @author homelleon
 * @version 1.0
 * @see IVectorBuilder3
 */
public class VectorBuilder3i implements IVectorBuilder3<Integer, Vector3i> {
	
	private int x;
	private int y;
	private int z;

	@Override
	public IVectorBuilder3<Integer, Vector3i> setX(Integer x) {
		this.x = x;
		return this;
	}

	@Override
	public IVectorBuilder3<Integer, Vector3i> setY(Integer y) {
		this.y = y;
		return this;
	}

	@Override
	public IVectorBuilder3<Integer, Vector3i> setZ(Integer z) {
		this.z = z;
		return this;
	}

	@Override
	public Vector3i build() {
		return new Vector3i(x,y,z);
	}

}
