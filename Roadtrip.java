package project03;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class Roadtrip {
	static ArrayList<TownNode> townList = readRoadFile("roads.csv");
	static ArrayList<AttrNode> attFile = readAttFile("attractions.csv");
	
	// Method to read the roads.csv file and store as TownNodes
	public static ArrayList<TownNode> readRoadFile(String filename) {
		ArrayList<TownNode> list = new ArrayList<TownNode>();
		TownNode node;
		TownNode node1;
		try {
			File data = new File(filename);
			Scanner inFile = new Scanner(data);
			inFile.useDelimiter("\n");
			
			String[] lineArr = new String[4];
			String line = new String();
			
			while(inFile.hasNextLine()) {
				line = inFile.next();
				lineArr = line.split(",");
				
				node = new TownNode(lineArr[0], Integer.parseInt(lineArr[2].trim()), Integer.parseInt(lineArr[3].trim()));
				node1 = new TownNode(lineArr[1], Integer.parseInt(lineArr[2].trim()), Integer.parseInt(lineArr[3].trim()));
//				System.out.println(node.town + " -> " + node1.town);
				
				node.setPath(node1);
				node1.setPath(node);
				
				if(!contains(list, lineArr[0])) {
					
					node.setPath(node);
					list.add(node);
				}
				if(!contains(list, lineArr[1])) {
					list.add(node1);
				}
				
			}
			
			inFile.close();
		} catch(FileNotFoundException err) {
			System.out.println("file not found!");
		} catch(NoSuchElementException err) {
			System.out.println("reached end of file " + filename);
		}
		
		return list;
	}

	// Method to check if a TownNode ArrayList contains a town
	private static boolean contains(ArrayList<TownNode> list, String town) {
		for(int i=0;i<list.size();i++) {
			try {
				if(list.get(i).town.equals(town)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	// Method to check if an AttrNode ArrayList contains an attraction
	private static boolean attContains(ArrayList<AttrNode> list, String attraction) {
		for(int i=0;i<list.size();i++) {
			try {
				if(list.get(i).attraction.equals(attraction)) {
					return true;
				}
			} catch (NullPointerException npe) {
//				System.out.println(townList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	// Method to read the attractions.csv file
	public static ArrayList<AttrNode> readAttFile(String filename) {
		ArrayList<AttrNode> list = new ArrayList<AttrNode>();
		try {
			File data = new File(filename);
			Scanner inFile = new Scanner(data);
			inFile.useDelimiter("\n");
			
			String[] lineArr = new String[2];
			String line = new String();
			
			while(inFile.hasNextLine()) {
				line = inFile.next();
				lineArr = line.split(",");
				list.add(new AttrNode(lineArr));
			}
			
//			System.out.println(list);
			
			inFile.close();
		} catch(FileNotFoundException err) {
			System.out.println("file not found!");
		} catch(NoSuchElementException err) {
			System.out.println("reached end of file " + filename);
		}
		
		return list;
	}
	
	// Main method that gets user input an passes to RouteFinder object 
	public static void main(String[] args) {
		ArrayList<String> attList = new ArrayList<String>();

		Scanner input = new Scanner(System.in);
		System.out.print("Name of starting city: ");
		String depart = input.nextLine().trim();
		
		while(!contains(townList, depart)) {
			System.out.println("City not found, please try again.");
			System.out.print("Name of starting city: ");
			depart = input.nextLine().trim();
		}
		
		System.out.print("Name of ending city: ");
		String destination = input.nextLine().trim();
		
		while(!contains(townList, destination)) {
			System.out.println("City not found, please try again.");
			System.out.print("Name of ending city: ");
			destination = input.nextLine().trim();
		}
		
		System.out.print("List an attration along the way (or ENOUGH to stop listing): ");
		String next = input.nextLine().trim();
		while(!next.equals("ENOUGH")) {
				if(!attContains(attFile, next)) {
					System.out.println("Attraction " + next + " unknown.");
				} else {
					attList.add(next);
				}
				System.out.print("List an attration along the way (or ENOUGH to stop listing): ");
				next = input.nextLine().trim();
		}
		
		input.close();
		
		RouteFinder router = new RouteFinder(depart, destination, townList, attFile, attList);
		try {
			router.route();
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
}
