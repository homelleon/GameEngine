package object.voxel;

public class Octree {

	public boolean head;
	public Octree[] children;

	public final static int MAX_CHILD_COUNT = 8;
	public final static float SCALE = 50f;
	public final static int MAX_DEPTH = 4;

	public Octree() {
		head = false;
		children = new Octree[8];
	}

	public boolean getHead() {
		return head;
	}

	public void setHead(boolean head) {
		this.head = head;
	}

	public Octree getChild(byte ndx) {
		return children[ndx];
	}

	public void setChild(byte ndx, Octree child) {
		children[ndx] = child;
	}

}
