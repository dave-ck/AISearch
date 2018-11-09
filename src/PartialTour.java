import java.util.ArrayList;
import java.util.HashSet;

public class PartialTour {

	private ArrayList<Integer> cities;
	private HashSet<Integer> unvisitedCities;
	private Graph graph;
	private int weight;
	private int root;
	private int head;

	// done & tested
	public PartialTour(Graph g) {
		this.cities = new ArrayList<Integer>();
		this.graph = g;
		this.weight = 0;
		this.unvisitedCities = new HashSet<Integer>();
		/*
		 * cities referred to by the index they are held at (n-1 where n is the
		 * of the city in the input file)
		 */
		for (int i = 0; i < g.matrix().length; i++) {
			unvisitedCities.add(i);
		}
	}

	// done
	public int getWeight() {
		return weight;
	}
	
	//getter
	public ArrayList<Integer> getCities() {
		return cities;
	}

	//getter
	public HashSet<Integer> getUnvisitedCities() {
		return unvisitedCities;
	}

	//getter
	public Graph getGraph() {
		return graph;
	}
	
	// done & tested
	public boolean isComplete() {
		return graph.matrix().length == cities.size();
	}

	// done & tested
	public int size() {
		return cities.size();
	}
	
	//done & tested
	public boolean visits(int city){
		return cities.contains(city);
	}
	
	//done & tested (not for weight functionality)
	public boolean append(int target) {
		if (unvisitedCities.remove(target)) {
			if(cities.isEmpty()){
				this.root = target;
			}
			else{
				this.weight += graph.weight(head, target);
			}
			cities.add(target);
			this.head=target;
			return true;
		} else {
			return false;
		}
	}

	// done & tested
	public PartialTour extend(int target) {
		PartialTour newTour = new PartialTour(graph);
		for (int i : cities) {
			newTour.append(i);
		}
		newTour.append(target);
		return newTour;
	}

	// done & tested
	public PartialTour backTrack(int distance) {
		if (cities.size() < distance) {
			return new PartialTour(graph);
		}
		PartialTour newTour = new PartialTour(graph);
		for (int i : cities.subList(0, cities.size() - distance)) {
			newTour.append(i);
		}
		return newTour;
	}

	// done
	// incorrect implementation
	public String toString() {
		String stringForm = "NAME = " + graph.getFilename() + ",\nTOURSIZE = " + cities.size() + ",\nLENGTH = " + this.getWeight() + ",\n";
		for (Integer i : cities) {
			stringForm += i + ",";
		}
		//remove below to produce output file
		stringForm += "\nUnvisited cities: ";
		for (Integer i : unvisitedCities) {
			stringForm += i + ",";
		}
		return stringForm;
	}

	

}
