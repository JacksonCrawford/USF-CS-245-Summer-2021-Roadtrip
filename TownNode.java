package project03;

/*
 * A node for storing a town, the fastest route to it, and from which town (path)
 */

public class TownNode {
	String town;
	int miles;
	int minutes;
	TownNode path;
//	String path;
	boolean known = false;
	
	// Default Constructor
	public TownNode() {
		town = new String();
		miles = 0;
		minutes = 0;
	}
	
	// Constructor
	public TownNode(String town, int miles, int mins) {
		this.town = town;
		this.miles = miles;
		this.minutes = mins;
	}
	
	// Externally set the path
	public void setPath(TownNode pParam) {
		path = pParam;
	}
	
//	public void setPath(String pParam) {
//		path = pParam;
//	}
	
	// toString method
	public String toString() {
		System.out.println("[" + town + ", " +
				miles + ", " + minutes + "]");
		
		return new String();
	}
}
