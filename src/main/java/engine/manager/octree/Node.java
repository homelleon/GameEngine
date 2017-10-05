package manager.octree;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private Node parent;
	private List<Node> children;
	private boolean isLeaf;
	
	public Node() {
		this.isLeaf = true;
		this.setChildren(new ArrayList<Node>());
	}
	
	public void addChildren(Node child) {
		if(this.isLeaf) {
			this.isLeaf = false;
		}
		child.setParent(this);
		this.children.add(child);
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public void removeChildren() {
		if(!this.isLeaf) {
			this.isLeaf = true;
		}
		if(this.children.size() != 0) {
			this.children.clear();
		}
	}
	
	public void update() {
		this.children.forEach(Node::update);
	}

}
