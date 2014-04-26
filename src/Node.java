import java.util.ArrayList;

public class Node {

	private Node parent;
	private ArrayList<Node> children;
	private String name;
	private String edgeName;
	private boolean visited;
	private boolean isTerminating;

	public Node(Node parent, String name, String edgeName) {
		this.parent = parent;
		this.children = new ArrayList<Node>();
		this.name = name;
		this.edgeName = edgeName;
		this.visited = false;
		isTerminating = false;
	}

	public Node(String edgeName) {
		this.edgeName = edgeName;
		isTerminating = true;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public Node getParent() {
		return parent;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public String getEdgeName() {
		return edgeName;
	}

	public void visit() {
		this.visited = true;
	}

	public boolean isVisited() {
		return visited;
	}

	public boolean isTerminating() {
		return isTerminating;
	}
}
