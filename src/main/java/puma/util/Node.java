package puma.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import puma.util.exceptions.rbac.NodeException;

public class Node<T extends Object> implements Serializable {
	private static final long serialVersionUID = 5123589656604472021L;
	private T containedObject;
	private Node<T> parent;
	private Set<Node<T>> children;
	
	public Node(T containedObject) {
		this.containedObject = containedObject;
		this.parent = null;
		this.children = new HashSet<Node<T>>();
	}
	
	public Node(T containedObject, Node<T> parentNode) {
		this(containedObject);
		this.parent = parentNode;
	}
	
	public void addChild(Node<T> child) throws NodeException {
		if (this.collect().contains(child))
			throw new NodeException(child.toString());
		if (child.collect().contains(this))
			throw new NodeException(child.toString());
		this.children.add(child);
	}
	
	public void removeChild(Node<T> child) throws NodeException {
		if (this.children.contains(child))
			this.children.remove(child);
		else
			throw new NodeException(child.toString());
	}
	
	public List<Node<T>> collect() {
		List<Node<T>> result = new ArrayList<Node<T>>();
		if (this.children.size() == 0)
			result.add(this);
		else
			for (Node<T> nextNode: this.children)
				result.addAll(nextNode.collect());
		return result;			
	}
	
	public Node<T> getParent() {
		return this.parent;
	}
	
	public Set<Node<T>> getChildren() {
		return this.children;
	}
	
	public T getContainedObject() {
		return this.containedObject;
	}
	
	@Override
	public String toString() {
		return this.containedObject.toString();
	}
	
}
