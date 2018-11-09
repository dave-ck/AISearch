import java.util.ArrayList;

public class Tour {
	private ArrayList<Integer> cities;
	private ArrayList<Integer> unvisitedCities;
	private Graph g;

	public Tour(Graph g) {
		cities = new ArrayList<Integer>();
		unvisitedCities = new ArrayList<Integer>();
		/*
		 * cities referred to by the index they are held at (n-1 where n is the
		 * of the city in the input file)
		 */
		for (int i = 0; i < g.matrix().length; i++) {
			unvisitedCities.add(i);
		}
	}
	
	public Tour(Graph g, ArrayList<Integer> cities){
		
	}
	
	
	public boolean isComplete() {
		return g.matrix().length == cities.size();
	}

	public int size() {
		return cities.size();
	}

	public void append(int target) {
		//hella bodge, may cause issues
		if(unvisitedCities.remove(new Integer(target))){
			cities.add(target);
		}
		else{
			System.out.println("City " + target + " has either already been visited or is not in the graph");
		}
	}
	
	public String toString(){
		String stringForm = "";
		for(Integer i: cities){
			stringForm += i + ",";
		}		
		return stringForm;
	}
}
