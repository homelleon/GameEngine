package manager.octree;

import java.util.ArrayList;
import java.util.List;

import scene.Subject;
import tool.math.vector.Vector;

/**
 * Common node class of any tree system.
 * 
 * @author homelleon
 */
public class Node<T extends Vector> extends Subject<T> {
	
	private Node<T> parent;
	private List<Node<T>> children;
	private boolean isLeaf;
	
	/**
	 * Constucts node and initialize its children.
	 */
	public Node(String name) {
		super(name);
		isLeaf = true;
		setChildren(new ArrayList<Node<T>>());
	}
	
	/**
	 * Adds child in children list of current node.
	 * 
	 * @param child
	 */
	public void addChild(Node<T> child) {
		if (isLeaf)
			isLeaf = false;
		child.setParent(this);
		children.add(child);
	}

	/**
	 * Gets parent node of current node.
	 * 
	 * @return {@link Node} object
	 */
	public Node<T> getParent() {
		return parent;
	}

	/**
	 * Sets parent node for current node.
	 * 
	 * @param parent {@link Node} object
	 */
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	/**
	 * Gets list of children of current node.
	 * 
	 * @return {@link List}<{@link Node}> array of children nodes
	 */
	public List<Node<T>> getChildren() {
		return children;
	}

	/**
	 * Sets list of nodes as childre for current node.
	 * 
	 * @param children {@link List}<{@link Node}> array of nodes
	 */
	public void setChildren(List<Node<T>> children) {
		this.children = children;
	}
	
	/**
	 * Removes all children from current node.
	 */
	public void removeChildren() {
		if (!isLeaf) isLeaf = true;		
		if (children.size() != 0) children.clear();
	}
	
	/**
	 * Udates current node and its children.
	 */
	public void update() {
		children.forEach(Node::update);
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
