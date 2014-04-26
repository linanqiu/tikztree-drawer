import java.util.Stack;

public class Builder {

	private Node head;
	private String label;

	public static final String TIKZHEADER = "\\begin{figure}[H] \n\\centering\n\\begin{tikzpicture}[grow=down, sloped]";
	public static final String TIKZFOOTER = ";\n\\end{tikzpicture}\n\\caption{CAPTION HERE}\n\\end{figure}";

	public static final String LABEL = "\\node[LABEL] {NAME}";
	public static final String CHILD = "child {\nnode[LABEL] {NAME}";
	public static final String CHILDCLOSE = "edge from parent\nnode[above] {EDGETITLE} \n}";

	public static final String CHILDEND = "child {\n node[end, label=below:{NAME}] {} \n edge from parent \n node[above] {EDGETITLE} \n }";

	public Builder(String label, Node head) {
		this.label = label;
		this.head = head;
	}

	public String getCode() {
		Node current = null;
		String code = LABEL.replaceAll("LABEL", label).replaceAll("NAME",
				head.getName());

		Stack<Node> stack = new Stack<Node>();

		for (Node headchild : head.getChildren()) {
			stack.add(headchild);
		}

		while (!stack.isEmpty()) {
			current = stack.pop();

			if (current.isTerminating()) {
				code = code
						+ "\n"
						+ CHILDCLOSE.replaceAll("EDGETITLE",
								current.getEdgeName());
			} else {

				if (!current.isVisited()) {
					current.visit();

					if (current.getChildren().size() != 0) {
						stack.push(new Node(current.getEdgeName()));

						// not end
						code = code
								+ "\n"
								+ CHILD.replaceAll("LABEL", label).replaceAll(
										"NAME", current.getName());

						for (Node child : current.getChildren()) {
							stack.push(child);
						}
					} else {
						code = code + "\n" + CHILDEND.replaceAll("NAME", current.getName()).replaceAll("EDGETITLE", current.getEdgeName());
					}
				}
			}
		}
		code = TIKZHEADER + "\n" + code;
		code = code + TIKZFOOTER;

		return code;
	}

	public static void main(String[] args) {

		// constructing tree
		Node head = new Node(null, "Investor A", "noedge");

		Node node1_1 = new Node(head, "Investor B", "Run");
		Node node1_2 = new Node(head, "Investor B", "Don't Run");
		head.addChild(node1_1);
		head.addChild(node1_2);

		Node node1_1_1 = new Node(node1_1, "Government", "Run");
		Node node1_1_2 = new Node(node1_1, "Government", "Don't Run");
		node1_1.addChild(node1_1_1);
		node1_1.addChild(node1_1_2);

		Node node1_2_1 = new Node(node1_2, "Government", "Run");
		Node node1_2_2 = new Node(node1_2, "Government", "Don't Run");
		node1_2.addChild(node1_2_1);
		node1_2.addChild(node1_2_2);
		
		Node node1_1_1_1 = new Node(node1_1_1, "(0, 0, 2)", "Devalue");
		Node node1_1_1_2 = new Node(node1_1_1, "(-1, -1, -3)", "Default");
		node1_1_1.addChild(node1_1_1_1);
		node1_1_1.addChild(node1_1_1_2);
		
		Node node1_1_2_1 = new Node(node1_1_2, "(0, -1, -1)", "Devalue");
		Node node1_1_2_2 = new Node(node1_1_2, "(-1, 1, -2)", "Default");
		node1_1_2.addChild(node1_1_2_1);
		node1_1_2.addChild(node1_1_2_2);
		
		Node node1_2_1_1 = new Node(node1_2_1, "(-1, 0, -1)", "Devalue");
		Node node1_2_1_2 = new Node(node1_2_1, "(1, -1, -2)", "Default");
		node1_2_1.addChild(node1_2_1_1);
		node1_2_1.addChild(node1_2_1_2);
		
		Node node1_2_2_1 = new Node(node1_2_2, "(0, 0, 0)", "Devalue");
		Node node1_2_2_2 = new Node(node1_2_2, "(1, 1, 1)", "Default");
		node1_2_2.addChild(node1_2_2_1);
		node1_2_2.addChild(node1_2_2_2);

		// build code
		Builder builder = new Builder("player", head);
		System.out.println(builder.getCode());
		
	}
}
