/*******************************************************************************
 * Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet 
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 ******************************************************************************/
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
