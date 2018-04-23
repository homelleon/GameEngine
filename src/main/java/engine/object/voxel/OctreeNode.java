package object.voxel;

import object.entity.Entity;

public class OctreeNode {

	private boolean hasParent = false;
	private OctreeNode parent;
	private boolean hasChildren = false;
	private OctreeNode[] childrens;
	private float scale;
	private Entity entity;

	public OctreeNode(float scale, OctreeNode parent, boolean hasChildren) {
		this.scale = scale;
		this.hasParent = true;
		this.parent = parent;
		this.hasChildren = hasChildren;
		
		if (!this.hasChildren) return;
		childrens = new OctreeNode[Octree.MAX_CHILD_COUNT];
		for (int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
			childrens[i] = new OctreeNode(scale / 8);
		}
	}

	public OctreeNode(float scale) {
		this.scale = scale;
		this.hasParent = false;
		this.hasChildren = false;
	}

	public boolean addChildrens() {
		if (hasChildren) return false;
		hasChildren = true;
		childrens = new OctreeNode[Octree.MAX_CHILD_COUNT];
		for (int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
			childrens[i] = new OctreeNode(scale / 8);
		}		
		return true;
	}

	public boolean deleteChildrens() {
		if (!hasChildren) return false;
		for (int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
			childrens[i] = null;
		}
		childrens = null;
		return true;
	}

	public boolean CheckScallable(float radius) {
		return (radius < (scale / 8) * Octree.SCALE);
	}
	
	public float getScale() {
		return scale;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
