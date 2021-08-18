package project03;

/*
 * Node for storing attraction name, and location (town)
 */

public class AttrNode {
	public String attraction;
	public String location;
	public boolean known = false;
	
	// Default constructor
	public AttrNode() {
		attraction = null;
		location = null;
	}
	
	// Constructor
	public AttrNode(String[] data) {
		try {
			attraction = data[0];
			location = data[1];
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
}
