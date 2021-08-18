package project03;

public class RouteFinder {
	// Variables for storing departure/destination names
	private String depart;
	private String destination;
	
	// ArrayLists for storing data from read files
	private ArrayList<TownNode> townsFile;
	private ArrayList<AttrNode> attractionsFile;
	
	// ArrayList for storing attraction locations
	private ArrayList<String> attractions;
	private ArrayList<String> attrLocs = new ArrayList<String>();
	
	// Crucial ArrayList for pathing
	private ArrayList<TownNode> path = new ArrayList<TownNode>();
	
	// Default constructor
	public RouteFinder() {
		depart = null;
		destination = null;
	}
	
	// Constructor
	public RouteFinder(String start, String end,
			ArrayList<TownNode> towns, ArrayList<AttrNode> attr, ArrayList<String> attrs) {
		depart = start;
		destination = end;
		townsFile = towns;
		attractionsFile = attr;
		attractions = attrs;
		attrsLoc();
		System.out.println(depart);
	}
	
	// Public call to get the route
	public void route() {
		try {
			String start = depart;
			
			calculateRoute(depart);
			reOrderAttrs();
			for(int i=0;i<attrLocs.size();i++ ) {
				calculateRoute(start);
				try {
					printPath(start, attrLocs.get(i));
				} catch(Exception err) {
					err.printStackTrace();
				}
				start = attrLocs.get(i);
			}
			calculateRoute(destination);
			printPath(start, destination);
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	// Method to order the attractions by distance from start
	private void reOrderAttrs() {
		int pos = 0;
		for(int i=0;i<path.size();i++) {
			for(int j=0;j < attractions.size();j++) {
				try {
					if(path.get(i).town.equals(attractions.get(j))) {
						attractions.add(attractions.get(j), pos);
						attractions.remove(attractions.size());
						pos++;
					}
				} catch(Exception err) {
					err.printStackTrace();
				}
			}
		}
		System.out.println(attractions);
	}
	
	// Method to get the locations of all user inputted attractions
	private void attrsLoc() {
		for(int i=0;i<attractions.size();i++) {
			for(int j=0;j<attractionsFile.size();j++) {
				try {
					if(attractionsFile.get(j).attraction.equals(attractions.get(i))) {
						attrLocs.add(attractionsFile.get(j).location);
						System.out.println(attractionsFile.get(j).location);
					}
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		}
	}
	
	// Method to find the departure node
	private TownNode findDepartNode(String start) {
		// Loop through towns
		for(int i=0;i<townsFile.size();i++) {
			try {
				// If the current town is equal to the argued start point, return it
				if(townsFile.get(i).town.equals(start)) {
					return townsFile.get(i);
				} else {
//					System.out.println(townsFile.get(i).town + " :: " + node.start + " & " + node.end);
				}
			} catch(Exception err) {
				err.printStackTrace();
			}
		}
		return null;
	}
	
	// Crucial method for calculating route and performing Dijkstra's algorithm
	public void calculateRoute(String start) throws Exception {
		// Throw exception if there is no file to read the towns from
		if(townsFile.size() == 0) {
			throw new Exception("No roads file has been provided to routeFinder");
		}
		
		// Throw exception if ther ei sno file to read the attractions from
		if(attractionsFile.size() == 0) {
			throw new Exception("No attractions file has been provided to routeFinder");
		}
		
		
		// ArrayList to store the neighbors
		ArrayList<TownNode> neighbors;
		// TownNode current, begins at argued starting node
		TownNode current = findDepartNode(start);
		// Add it to the path
		path.add(current);
		// TownNode to store a neighbor
		TownNode neighbor;
		
		// Loop through every town
		while(current != null) {
			// Add current to path
			path.add(current);
			// Get the neighbors of current town
			neighbors = getNeighbors(current);
			
			// Loop through neighbors
			for(int j=0;j<neighbors.size();j++) {
				// Store one neighbor (type: TownNode) at a time
				neighbor = neighbors.get(j);
				// Find the town node for the neighbor (or create one if does not exist yet)
				
				// Prim's algorithm condition
				if(current.minutes > neighbor.minutes) {
					// Set neighbors path to current node
					neighbor.path = current;
					// Set neighbors minutes to current minutes
					neighbor.minutes = current.minutes;
					// Set neighbors miles to current miles
					neighbor.miles = current.miles;
				}
			}
			// Set node to be known
			current.known = true;
			// Change to least cost unknown town
			current = leastCostUnknownVertex();
		}

	}
	
	// Method to find the least cost unknown vertex
	private TownNode leastCostUnknownVertex() {
		// TownNode for storing the node with the least cost
		TownNode leastCost = new TownNode();
		// Set minutes to max value
		leastCost.minutes = Integer.MAX_VALUE;
		// Initialize current node for looping purposes
		TownNode current = null;
		// Boolean for knowing if no lower cost vertex was found
		boolean updated = false;
		
		// Loop through towns
		for(int i = 0;i<townsFile.size();i++) {
			try { // Get new town
				current = townsFile.get(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// If unknown and less than leastCost
			if(!current.known &&
					current.minutes < leastCost.minutes) {
				// Set to leastCost
				leastCost = current;
				// Let know that leastCost changed
				updated = true;
			}
		}
		
		// Only return if leastCost changed
		if(updated) {
			return leastCost;
		}
		
		return null;
	}
	
	// Method to get neighbors of an argued TownNode
		private ArrayList<TownNode> getNeighbors(TownNode node) {
			// ArrayList for storing neighbors (type: TownNode)
			ArrayList<TownNode> neighbors = new ArrayList<TownNode>();
			
			try {
				// TownNode variable for looping purposes
				TownNode current = null;
				// Loop through roads
				for(int i=0;i<townsFile.size();i++) {
					// Update current
					current = townsFile.get(i);
					
					// If road has the argued node's town on it, add to list
					if(current.path.town.equals(node.town)) {
						neighbors.add(current);
					}
				}
			} catch(Exception err) {
				err.printStackTrace();
			}
			
			return neighbors;
		}
	
	// Method to find a TownNode in the path from it's town name
	private TownNode findTownNode(String town) {
		TownNode current;
		for(int i=0;i<townsFile.size();i++) {
			try {
				current = townsFile.get(i);
				if(current.town.equals(town)) {
					return current;
				}
			} catch(Exception err) {
				err.printStackTrace();
			}
		}
		return null;
	}

	// Method to print the path from argued start to end (must be calculated first); 
	public void printPath(String start, String end) {
		TownNode current = findTownNode(end);
		int miles = 0;
		int minutes = 0;
		try {
			miles = current.miles;
			minutes = current.minutes;
		} catch(Exception err) {
			err.printStackTrace();
		}
		
		while(current.town != depart) {
			current = current.path;
			miles += current.miles;
			minutes += current.minutes;
			System.out.println("* " + current.town + " -> " + current.path.town);
		}
		
		System.out.println("total miles: " + miles);
		System.out.println("total minutes: " + minutes);
	}
	
}
