import java.util.ArrayList;

public class FullTour extends Tour{

	public FullTour(Graph g) {
		super(g);
	}

	public FullTour(Graph g, ArrayList<Integer> cities){
		super(g, cities);
	}
	
	public FullTour(PartialTour pt) throws Exception{
		super(pt.getGraph());
		if(pt.isComplete()){
			setCities(pt.getCities());
		}
		else{
			throw new Exception("Incomplete partial tour cannot be passed as a full tour");
		}
	}

	public String toString(){
		String stringForm = "NAME = " + getGraph().getFilename().substring(3, getGraph().getFilename().length()-4) + ",\nTOURSIZE = " + getCities().size() + ",\nLENGTH = " + this.computeWeight() + ",\n";
		for (Integer i : getCities()) {
			stringForm += (i+1) + ",";  //+1 to account for city indexing starting at 1, not 0
		}
		stringForm = stringForm.substring(0, stringForm.length()-1); //delete the last comma
		return stringForm;	
	}
	
	
}
