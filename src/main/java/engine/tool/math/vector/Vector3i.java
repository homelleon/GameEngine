package tool.math.vector;

public class Vector3i extends Vector {

	public int x;
	public int y;
	public int z;

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "Vector3i" + "[" + x + "," + y + "," + z + "]";
	}
	
	@Override
	public Vector3i clone() {
		return new Vector3i(x, y, z);		
	}

}
