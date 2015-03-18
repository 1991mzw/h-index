import java.util.*;

public class FormGraph {
	
	private HashMap<Long, Node> nodeList = new HashMap<Long, Node>();
	private HashMap<Long, Edge> edgeList = new HashMap<Long, Edge>();
	
	public HashMap<Long, Node> getNodeList() {
		return nodeList;
	}
	public void addNode(Node node) {
		this.nodeList.put(node.getNodeID(), node);
	}
	public HashMap<Long, Edge> getEdgeList() {
		return edgeList;
	}
	public void addEdge(Edge edge) {
		this.edgeList.put(edge.getEdgeID(),edge);
	}
}