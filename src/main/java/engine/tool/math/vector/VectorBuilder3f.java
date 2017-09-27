package tool.math.vector;

/**
 * Builder class for Vec3f with float values.
 * 
 * @author homelleon
 * @version 1.0
 * @see IVectorBuilder3
 */
public class VectorBuilder3f implements IVectorBuilder3<Float, Vector3fF> {
	
	private float x;
	private float y;
	private float z;

	@Override
	public IVectorBuilder3<Float, Vector3fF> setX(Float x) {
		this.x = x;
		return this;
	}

	@Override
	public IVectorBuilder3<Float, Vector3fF> setY(Float y) {
		this.y = y;
		return this;
	}

	@Override
	public IVectorBuilder3<Float, Vector3fF> setZ(Float z) {
		this.z = z;
		return this;
	}

	@Override
	public Vector3fF build() {
		return new Vector3fF(x,y,z);
	}
	
}