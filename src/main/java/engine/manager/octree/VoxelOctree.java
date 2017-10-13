package manager.octree;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import tool.math.vector.IVectorBuilder3;
import tool.math.vector.Vector3f;
import tool.math.vector.Vector3i;
import tool.math.vector.VectorBuilder3i;

public class VoxelOctree extends Node {
	
	private float voxelSize = 5f;
	private int nodeSize = 8;
	private int visualLimit = 2;
	private Vector3f position;
	
	public VoxelOctree(Vector3f position) {
		super();
		this.position = position;
		List<Node> voxels = IntStream.range(0, nodeSize).sorted()
				.mapToObj(index -> new Voxel(8, getChildLocationByIndex(index), position))
				.collect(Collectors.toList());
		this.setChildren(voxels);	
	}
	
	public Voxel getChildByLocation(Vector3i location) {
		int step = nodeSize / 4;
		int index = location.x * (int) Math.pow(step, 2) + location.y * step + location.z;  
		return (Voxel) this.getChildren().get(index);
	}
	
	public Vector3i getChildLocationByIndex(int index) {
		int x = (int) Math.floor(index / 6);
		int y = (int) Math.floor((index - x * 6) / 4);
		int z = index - (int) Math.floor(index / 2) * 2;
		IVectorBuilder3<Integer, Vector3i> vectorBuilder = new VectorBuilder3i();
		return vectorBuilder
				.setX(x)
				.setY(y)
				.setZ(z)
				.build();
	}
}
