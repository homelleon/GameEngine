package tool.math.vector;

/**
 * Builder class for Vec3f with float values.
 * 
 * @author homelleon
 * @version 1.0
 * @see IVectorBuilder3
 */
public class VectorBuilder3f implements IVectorBuilder3<Float, Vec3f> {
	
	private float x;
	private float y;
	private float z;

	@Override
	public IVectorBuilder3<Float, Vec3f> setX(Float x) {
		this.x = x;
		return this;
	}

	@Override
	public IVectorBuilder3<Float, Vec3f> setY(Float y) {
		this.y = y;
		return this;
	}

	@Override
	public IVectorBuilder3<Float, Vec3f> setZ(Float z) {
		this.z = z;
		return this;
	}

	@Override
	public Vec3f build() {
		return new Vec3f(x,y,z);
	}
	
}