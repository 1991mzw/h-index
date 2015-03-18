
public class Edge {
	
	private int weight = 0;
	private long edgeID = 0;
	private long node1 = 0;
	private long node2 = 0;

	public int getWeight() {
		return weight;
	}
	public void addWeight(int weight) {
		this.weight++;
	}
	public long getEdgeID() {
		return edgeID;
	}
	public void setEdgeID(long edgeID) {
		this.edgeID = edgeID;
	}
	public long getNode1() {
		return node1;
	}
	public void setNode1(long node1) {
		this.node1 = node1;
	}
	public long getNode2() {
		return node2;
	}
	public void setNode2(long node2) {
		this.node2 = node2;
	}

}
