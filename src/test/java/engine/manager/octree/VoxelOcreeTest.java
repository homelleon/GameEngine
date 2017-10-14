package manager.octree;

import java.util.stream.IntStream;

import core.debug.EngineDebug;
import tool.math.vector.Vector3f;

public class VoxelOcreeTest {
	public static void main(String[] args) {
		VoxelOctree octree = new VoxelOctree(new Vector3f(0,0,0));
		IntStream.range(0, 8).forEach(i -> {
				EngineDebug.print(i + " ");
			}
		);
	}
}
