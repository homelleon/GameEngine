package manager.octree;

import java.util.ArrayList;
import java.util.List;

/**
 * Common node class of any tree system.
 * 
 * @author homelleon
 */
public class Node {
	
	private Node parent;
	private List<Node> children;
	private boolean isLeaf;
	
	/**
	 * Constucts node and initialize its children.
	 */
	public Node() {
		this.isLeaf = true;
		this.setChildren(new ArrayList<Node>());
	}
	
	/**
	 * Adds child in children list of current node.
	 * 
	 * @param child
	 */
	public void addChildren(Node child) {
		if(this.isLeaf) {
			this.isLeaf = false;
		}
		child.setParent(this);
		this.children.add(child);
	}

	/**
	 * Gets parent node of current node.
	 * 
	 * @return {@link Node} object
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets parent node for current node.
	 * 
	 * @param parent {@link Node} object
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * Gets list of children of current node.
	 * 
	 * @return {@link List}<{@link Node}> array of children nodes
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * Sets list of nodes as childre for current node.
	 * 
	 * @param children {@link List}<{@link Node}> array of nodes
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	/**
	 * Removes all children from current node.
	 */
	public void removeChildren() {
		if(!this.isLeaf) {
			this.isLeaf = true;
		}
		if(this.children.size() != 0) {
			this.children.clear();
		}
	}
	
	/**
	 * Udates current node and its children.
	 */
	public void update() {
		this.children.forEach(Node::update);
	}

}
