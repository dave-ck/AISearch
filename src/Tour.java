import java.util.ArrayList;

public class Tour {
	private ArrayList<Integer> cities;
	private Graph graph;
	private int[][] matrix;
	private int weight;
	private boolean weightCorrect;

	public Tour(Graph g) {
		cities = new ArrayList<Integer>();
		this.graph = g;
		this.matrix = g.matrix();
	}

	public Tour(Graph g, ArrayList<Integer> cities) {
		this.cities = cities;
		this.graph = g;
		this.matrix = graph.matrix();
	}
	
	public ArrayList<Integer> getCities() {
		return cities;
	}

	public void setCities(ArrayList<Integer> cities) {
		this.cities = cities;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	public boolean isComplete() {
		return graph.matrix().length == cities.size();
	}

	public int size() {
		return cities.size();
	}

	public String toString() {
		String stringForm = "";
		for (Integer i : cities) {
			stringForm += i + ",";
		}
		return stringForm;
	}

	public void computeWeight() {
		if (size()==0){
			weight =  0;
			return;
		}
		weight = 0;
		int from = getRoot();
		int to = -1;
		for (int i : cities) {
			to = i;
			weight += matrix[from][to];
			from = to;
		}
		if (isComplete()) {
			weight += matrix[to][getRoot()];
		}
	}

	public int getWeight() {
		if (!weightCorrect){
			computeWeight();
			weightCorrect = true;
		}
		return weight;
	}
	
	public void incorrectWeight(){
		this.weightCorrect = false;
	}
	
	public void setWeight(int newWeight){
		this.weight = newWeight;
	}
	
	public boolean visits(int city){
		return cities.contains(city);
	}
	
	public boolean validate(){
		//check there are no duplicates
		boolean valid = true;
		for(int i: cities){
			for(int j : cities){
				if(i==j){
					valid = false;
					System.out.println("node duplicate found: " + i);
					System.out.println(this);
				}
			}
		}
		return valid;
	}
	
	
	public int getRoot() {
		return cities.get(0);
	}
}
