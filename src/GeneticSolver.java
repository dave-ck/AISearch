import java.util.ArrayList;
import java.util.HashMap;

public class GeneticSolver {
	private Graph graph;
	private BoundedPriorityQueue<FullTour> population;
	private FullTour bestTour;
	private int bestWeight;
	
	public GeneticSolver(Graph g, int populationSize){
		FullTourWeightComparator comparator = new FullTourWeightComparator();
		population = new BoundedPriorityQueue<FullTour>(populationSize, comparator);
		this.graph = g;
	}
	
	public void greedyPopulate() throws Exception{
		for (int i = 0; i < graph.getSize(); i++){
			population.add(StartPointGenerator.Greedy(graph, i));
		}
	}
	
	public void updateBestTour(){
		if (bestWeight > population.get(0).computeWeight()){
			bestTour = population.get(0);
			bestWeight = bestTour.computeWeight();
		}
	}
	
	public void generateChildren(){
	
	}
	
	public  FullTour edgeRecombinationCrossover(FullTour daddy, FullTour mommy){
		FullTour child = new FullTour(graph);
		HashMap<Integer, ArrayList<Integer>> edgeMap = new HashMap<>();
		return child;
	}
}
