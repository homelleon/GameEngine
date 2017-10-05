package object.voxel.octree;

import object.entity.entity.IEntity;

public class OctreeNode {

	private boolean hasParent = false;
	private OctreeNode parent;
	private boolean hasChildren = false;
	private OctreeNode[] childrens;
	private float scale;
	private IEntity entity;

	public OctreeNode(float scale, OctreeNode parent, boolean hasChildren) {
		this.scale = scale;
		this.hasParent = true;
		this.parent = parent;
		this.hasChildren = hasChildren;
		if (this.hasChildren) {
			this.childrens = new OctreeNode[Octree.MAX_CHILD_COUNT];
			for (int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
				this.childrens[i] = new OctreeNode(this.scale / 8);
			}
		}
	}

	public OctreeNode(float scale) {
		this.scale = scale;
		this.hasParent = false;
		this.hasChildren = false;
	}

	public boolean addChildrens() {
		boolean isSucced = false;
		if (!hasChildren) {
			this.hasChildren = true;
			this.childrens = new OctreeNode[Octree.MAX_CHILD_COUNT];
			for (int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
				this.childrens[i] = new OctreeNode(this.scale / 8);
			}
			isSucced = true;
		}
		return isSucced;
	}

	public boolean deleteChildrens() {
		boolean isSucced = false;
		if (hasChildren) {
			for (int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
				this.childrens[i] = null;
			}
			this.childrens = null;
			isSucced = true;
		}
		return isSucced;
	}

	public boolean CheckScallable(float radius) {
		boolean isCanBeScaled = false;
		if (radius < (this.scale / 8) * Octree.SCALE) {
			isCanBeScaled = true;
		}
		return isCanBeScaled;
	}
	
	public float getScale() {
		return this.scale;
	}

	public void setEntity(IEntity entity) {
		this.entity = entity;
	}

}
