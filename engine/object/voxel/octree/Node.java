package object.voxel.octree;

import object.entity.entity.Entity;

public class Node {
	
	private boolean hasParent = false;
	private Node parent;
	private boolean hasChildren = false;
	private Node[] childrens;
	private float scale;
	private Entity entity;
	
	public Node(float scale, Node parent, boolean hasChildren) {
		this.scale = scale;
		this.hasParent = true;
		this.parent = parent;
		this.hasChildren = hasChildren;
		if(this.hasChildren) {
			this.childrens = new Node[Octree.MAX_CHILD_COUNT];
			for(int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
				this.childrens[i] = new Node(this.scale / 8);
			}
		}
	}
	
	public Node(float scale) {
		this.scale = scale;
		this.hasParent = false;
		this.hasChildren = false;
	}
	
	public boolean addChildrens() {
		boolean isSucced = false;
		if(!hasChildren) {
			this.hasChildren = true;
			this.childrens = new Node[Octree.MAX_CHILD_COUNT];
			for(int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
				this.childrens[i] = new Node(this.scale / 8);
			}
			isSucced = true;
		}
		return isSucced;
	}
	
	public boolean deleteChildrens() {
		boolean isSucced = false;
		if(hasChildren) {
			for(int i = 0; i < Octree.MAX_CHILD_COUNT; i++) {
				this.childrens[i] = null;
			}
			this.childrens = null;
			isSucced = true;
		}
		return isSucced;
	}
	
	public boolean CheckScallable(float radius) {
		boolean isCanBeScaled = false;
		if(radius < (this.scale / 8) * Octree.SCALE) {
			isCanBeScaled = true;
		}
		return isCanBeScaled;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
