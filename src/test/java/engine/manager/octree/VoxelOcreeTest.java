package manager.octree;

import core.debug.EngineDebug;
import tool.math.vector.Vector3f;

public class VoxelOcreeTest {
	public static void main(String[] args) {
		VoxelOctree octree = new VoxelOctree(new Vector3f(0,0,0));
		EngineDebug.println(octree.getChildLocationByIndex(0));
	}
}
