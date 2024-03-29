import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class GeneticSolver {
	private Graph graph;
	private BoundedPriorityQueue<FullTour> population;
	private int populationSize;
	private FullTour bestTour;
	private int bestWeight;
	private int proliferation; //constant added (or subtracted) from population to determine number in each generation
	private Random random;
	private int parentsPerChild;
	private FullTourWeightComparator comparator;
	private double radiation;
	private int graphSize;
	private int worldAge;
	
	public GeneticSolver(Graph g, int populationSize, int proliferation, int parentsPerChild, double radiation){
		this.comparator = new FullTourWeightComparator();
		this.population = new BoundedPriorityQueue<>(populationSize, comparator);
		this.populationSize = populationSize;
		this.graph = g;
		this.proliferation = proliferation;
		this.random = new Random();
		this.parentsPerChild = parentsPerChild;
		this.bestWeight = g.getMaxWeight()*g.getSize();
		this.radiation = radiation;
		this.graphSize = graph.getSize();
		
	}
	
	public void greedyPopulate() throws Exception{
		while (population.size()< population.getBound()){
			//will include some (sometimes many) duplicates in the start population
			for (int i = 0; i < graph.getSize(); i++){
				population.add(StartPointGenerator.Greedy(graph, i));
			}
		}
	}
	
	public void randomPopulate() throws Exception{
		while (population.size()< population.getBound()){
			//add a new random tour to population
			population.add(StartPointGenerator.random(graph));
		}
	}
	
	public void updateBestTour(){
		if (bestWeight > population.get(0).getWeight()){
			bestTour = population.get(0);
			bestWeight = bestTour.getWeight();
			System.out.println("World age: " + worldAge + " New best tour found: " + bestWeight);
		}
	}
	
	public HashMap<Integer, ArrayList<Integer>> generateEdgeMap(Collection<FullTour> parents){
		HashMap<Integer, ArrayList<Integer>> edgeMap = new HashMap<Integer, ArrayList<Integer>>();
		for(FullTour parent : parents){
			for (int city: parent.getCities()){
				if (!edgeMap.containsKey(city)){
					edgeMap.put(city, new ArrayList<>());
				}
				edgeMap.get(city).addAll(parent.neighbors(city));
			}
		}
		return edgeMap;
	}
	
	public FullTour edgeMapChild(Collection<FullTour> parents, int startCity) throws Exception{
		HashMap<Integer, ArrayList<Integer>> edgeMap = generateEdgeMap(parents);
		PartialTour partialTour = new PartialTour(graph);
		partialTour.append(startCity);
		int currentCity = startCity;
		while (!partialTour.isComplete()){
			// identify best neighbor
			int bestNeighbor = edgeMap.get(currentCity).get(0);
			int bestMetaNeighborScore = edgeMap.get(bestNeighbor).size();
			for(int candidate: edgeMap.get(currentCity)){
				if (bestMetaNeighborScore > edgeMap.get(candidate).size()){
					//update bestNeighbor to be candidate
					bestNeighbor = candidate;
					bestMetaNeighborScore = edgeMap.get(bestNeighbor).size();
				}
			}
			partialTour.append(bestNeighbor);
			currentCity=bestNeighbor;
		}
		return new FullTour(partialTour);
	}
	
	//for testing
	public void printPopWeights(){
		String out = "";
		for(FullTour i : population.subList(0, 30)){
			out += i.getWeight() + ",";
		}
		System.out.println(out + "\n");
	}
	
	
	public FullTour randomParent(){
		double val = random.nextDouble();
		val *= val;
		int index = (int) Math.floor(val*population.size());
		return population.get(index);
	}
	
	public void setRadiation(double radiation){
		this.radiation = radiation;
	}
	
	public void setProliferation(int proliferation){
		this.proliferation = proliferation;
	}
	
	public void tick() throws Exception{
		BoundedPriorityQueue<FullTour> newGeneration = new BoundedPriorityQueue<>(populationSize, comparator);
		ArrayList<ArrayList<FullTour>> listOfParentPairs = new ArrayList<>();
		for (int i = 0; i<proliferation; i++){
			//randomly select parents, with higher probability for parents of higher fitness
			ArrayList<FullTour> parents = new ArrayList<>();
			for(int j = 0; j < parentsPerChild; j++){
				parents.add(randomParent());
			}
			listOfParentPairs.add(parents);
			//2-opt the child to induce randomness
		}
		listOfParentPairs.parallelStream().forEach( (parents) -> {
			try {
				FullTour child = edgeMapChild(parents, 1);
				//2-opt the child to induce randomness
				while (random.nextDouble()<radiation){
					child = child.randomReversedSuccessor();
				}
				newGeneration.add(child);
				//swap out for population to preserver parents in population
				//population.add(edgeMapChild(parents, 1));
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
		});
		population = newGeneration;
		updateBestTour();
		worldAge++;
	}
	
	
	public void run(int generations) throws Exception{
		for(int i = 0; i < generations; i++){
			this.tick();
			//System.out.println("Generation: " + i + "; best \"live\" weight: " + population.get(0).getWeight() +
			//		"; best weight overall: " + bestWeight);
			//printPopWeights();
		}
	}
	
	public FullTour getBestTour(){
		return bestTour;
	}
	
}
