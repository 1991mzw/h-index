import java.util.ArrayList;

public class Node {
	
	private String name = null;
	private double weiInflu = 0;
	private long douInflu = 0;
	private long nodeID = 0;
	private ArrayList<Long> edgeList = new ArrayList<Long>();
	
	public Node(String name, long id) {
		this.name = name;
		this.nodeID = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeiInflu() {
		return weiInflu;
	}

	public void setWeiInflu(double weiInflu) {
		this.weiInflu = weiInflu;
	}

	public long getDouInflu() {
		return douInflu;
	}

	public void setDouInflu(long douInflu) {
		this.douInflu = douInflu;
	}

	public long getNodeID() {
		return nodeID;
	}

	public void setNodeID(long nodeID) {
		this.nodeID = nodeID;
	}

	public ArrayList<Long> getEdgeList() {
		return edgeList;
	}

	public void addEdgeList(long edge) {
		this.edgeList.add(edge);
	}

}
