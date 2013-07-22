package puma.util.exceptions.rbac;

public class NodeException extends Exception {
	private static final long serialVersionUID = -824007263482685964L;
	public NodeException(String node) {
		super("Could not modify the tree: Node " + node + " could not be modified.");
	}
}
