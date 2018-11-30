import java.util.ArrayList;
import java.util.Random;

public class FullTour extends Tour {
	
	public FullTour(Graph g) {
		super(g);
		ArrayList<Integer> cities = new ArrayList<>();
		for (int i = 0; i < g.getSize(); i++){
			cities.add(i);
		}
		this.setCities(cities);
	}
	
	public FullTour(Graph g, ArrayList<Integer> cities) {
		super(g, cities);
	}
	
	public FullTour(PartialTour pt) throws Exception {
		super(pt.getGraph());
		if (pt.isComplete()) {
			setCities(pt.getCities());
		} else {
			throw new Exception("Incomplete partial tour cannot be passed as a full tour");
		}
	}
	
	public String toString() {
		String stringForm = "NAME = " + getGraph().getFilename().substring(3, getGraph().getFilename().length() - 4) + ",\nTOURSIZE = " + getCities().size() + ",\nLENGTH = " + this.computeWeight() + ",\n";
		for (Integer i : getCities()) {
			stringForm += (i + 1) + ",";  //+1 to account for city indexing starting at 1, not 0
		}
		stringForm = stringForm.substring(0, stringForm.length() - 1); //delete the last comma
		return stringForm;
	}
	
	public ArrayList<FullTour> getSuccessors() {
		ArrayList<FullTour> successors = new ArrayList<>();
		for (int i = 0; i < this.size(); i++) {
			for (int j = 0; j < this.size(); j++) {
				successors.add(swapped(i, j));
			}
		}
		return successors;
	}
	
	public FullTour randomSwappedSuccessor() {
		Random r = new Random();
		int i = 0;
		int j = 0;
		while(i==j){
			i = r.nextInt(this.size());
			j = r.nextInt(this.size());
		}
		return swapped(i,j);
	}
	
	public FullTour randomReversedSuccessor(){
		Random r = new Random();
		int i = 0;
		int j = 0;
		while(i==j){
			i = r.nextInt(this.size());
			j = r.nextInt(this.size());
		}
		return subReversed(i, j);
	}
	
	
	public FullTour clone() {
		ArrayList<Integer> cloneCities = new ArrayList<>();
		//may cause issues as Integer is Object-type
		cloneCities.addAll(this.getCities());
		return new FullTour(this.getGraph(), cloneCities);
	}
	
	public FullTour swapped(int cityIndex1, int cityIndex2) {
		FullTour swappedTour = this.clone();
		swappedTour.getCities().set(cityIndex2, this.getCities().get(cityIndex1));
		swappedTour.getCities().set(cityIndex1, this.getCities().get(cityIndex2));
		return swappedTour;
	}
	
	public FullTour subReversed(int cityIndex1, int cityIndex2){
		if(cityIndex1>cityIndex2){
			int temp = cityIndex1;
			cityIndex1 = cityIndex2;
			cityIndex2 = temp;
		}
		// TODO: reread and verify soundness
		FullTour subReversed = this.clone();
		ArrayList<Integer> subCities = new ArrayList<>();
		subCities.addAll(this.getCities().subList(0, cityIndex1));
		for(int i : this.getCities().subList(cityIndex1, cityIndex2)){
			subCities.add(cityIndex1, i);
		}
		subCities.addAll(this.getCities().subList(cityIndex2, this.getCities().size()));
		subReversed.setCities(subCities);
		return subReversed;
	}
	
	public ArrayList<Integer> neighbors(int city){
		ArrayList<Integer> neighbors = new ArrayList<>();
		getCities().indexOf(city);
		return neighbors;
	}
}
