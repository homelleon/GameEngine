package manager.octree;

import tool.math.vector.Vector3f;
import tool.math.vector.Vector3i;

public class VoxelOctree extends Node {
	
	private float voxelSize = 5f;
	private int nodeSize = 8;
	private int visualLevel = 2;
	private Vector3f position;
	
	public VoxelOctree(Vector3f position) {
		super();
		this.position = position;
		for(int x = 0; x < nodeSize; x++) {
			for(int y = 0; y < nodeSize; y++) {
				for(int z = 0; z < nodeSize; z++) {
					this.addChild(new Voxel(8, new Vector3i(x,y,z), position, visualLevel));
				}					
			}
		}
	}
	
	public Voxel getChildByLocation(Vector3i location) {
		int step = nodeSize / 4;
		int index = location.x * (int) Math.pow(step, 2) + location.y * step + location.z;  
		return (Voxel) this.getChildren().get(index);
	}
}
