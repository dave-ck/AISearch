import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

public class TSP {
	
	public static void main(String[] args) throws Exception {
		//masterCall();     //writes tour files for every implemented algorithm
		writeToFile("Annealing", parallelAnnealing());
	}
	
	public static void testCall() throws Exception{
		double startTemp = 3, alpha = 0.1, beta = 1.00001 , approxZero=0.00001;
		SimAnnealer anne = new SimAnnealer(startTemp, beta, approxZero);
		ArrayList<Graph> graphs = readGraphs();
		Graph testGraph = graphs.get(1);
		FullTour boundedSearch = StartPointGenerator.gatedSearch(testGraph);
		FullTour greedy = StartPointGenerator.Greedy(testGraph, 0);
		FullTour annealGreedy = anne.SimAnneal(greedy);
		FullTour annealLookAhead = anne.SimAnneal(boundedSearch);
		System.out.println("Weight for bounded search: " + boundedSearch.computeWeight());
		System.out.println("Weight for greedy: " + greedy.computeWeight());
		System.out.println("Weight for annealed greedy: " + annealGreedy.computeWeight());
		System.out.println("Weight for annealed lookahead: " + annealLookAhead.computeWeight());
		ArrayList results = new ArrayList<FullTour>();
		results.add(greedy);
		writeToFile("Greedy", results);
	}
	
	
	public static void timeComparison() throws Exception{
		double startTime = System.currentTimeMillis();
		try {
			testCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		double singleThreadTime = System.currentTimeMillis()-startTime;
		startTime = System.currentTimeMillis();
		writeToFile("ParallelOutput", parallelAnnealing());
		
		double parallelTimeTaken = System.currentTimeMillis()-startTime;
		System.out.println("Time to run in parallel: " + parallelTimeTaken + " ms");
		System.out.println("Per tour: " + (parallelTimeTaken/12) + " ms");
		System.out.println("Time taken to run \"normally\": " + singleThreadTime + " ms");
	}
	
	public static Vector<FullTour> parallelAnnealing() throws Exception{
		ArrayList<Graph> graphs = readGraphs();
		Vector<FullTour> results = new Vector<>();
		double startTemp = 10, alpha = 0.1, beta = 1.0000005  , approxZero=0.001;
		graphs.parallelStream().forEach( (graph) -> {
			try {
				SimAnnealer anne = new SimAnnealer(startTemp, beta, approxZero);
				FullTour result = anne.SimAnneal(StartPointGenerator.Greedy(graph, 0));
				results.add(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		return results;
	}
	
	public static FullTour annealTest() throws Exception {
		ArrayList<Graph> graphs = readGraphs();
		Graph graph = graphs.get(1);
		//found there are seldom changes made at temperatures under 0.05 --> set approxZero to 0.01 at the lowest;
		// seldom changes made at temperatures above 5 --> set startTemp to 7 at the highest;
		// beta as small as possible with good runtime
		double startTemp = 10, alpha = 0.1, beta = 1.0000001  , approxZero=0.001;
		SimAnnealer anne = new SimAnnealer(startTemp, beta, approxZero);
		FullTour result = anne.SimAnneal(StartPointGenerator.Greedy(graph, 0));
		System.out.println("Params: startTemp = " + startTemp + " beta = " + beta + " approxZero = " + approxZero);
		System.out.println("Result weight:" + result.computeWeight());
		System.out.println("Greedy weight:" + StartPointGenerator.Greedy(graph, 0).computeWeight());
		System.out.println("Graph size: " + graph.getSize());
		return result;
	}
	
	public static void masterCall() throws Exception {
		SimAnnealer anne = new SimAnnealer(1000, 1.02, 0.01);
		ArrayList<FullTour> greedyTours = new ArrayList<>();
		ArrayList<FullTour> annealingTours = new ArrayList<>();
		ArrayList<FullTour> hillClimbingTours = new ArrayList<>();
		for (Graph graph : readGraphs()) {
			System.out.println("Currently processing graph of size " + graph.getSize());
			//Greedy
			greedyTours.add(StartPointGenerator.Greedy(graph, 0));
			// Hill Climbing
			hillClimbingTours.add(anne.HillClimb(StartPointGenerator.Greedy(graph, 0)));
			//Annealing
			annealingTours.add(anne.SimAnneal(StartPointGenerator.Greedy(graph, 0)));
		}
		System.out.println("writeToFile called");
		
		writeToFile("Greedy", greedyTours);
		writeToFile("HillClimb", hillClimbingTours);
		writeToFile("Annealing", annealingTours);
	}
	
	//TODO: change ...file58.txt to ...file058.txt
	public static void writeToFile(String pathName, Collection<FullTour> tours) throws Exception {
		System.out.println("Printing tours...");
		new File(pathName).mkdirs();
		for (FullTour tour : tours) {
			String x = "";
			if (tour.size() < 99){
				x += "0";
			}
			String outputFileName = "tourNEWAISearchfile" + x + tour.size() + ".txt";
			PrintWriter writer = new PrintWriter(pathName + "/" + outputFileName, "UTF-8");
			writer.println(tour);
			writer.close();
		}
		System.out.println("Successfully printed tours");
	}
	
	
	public static ArrayList<Graph> readGraphs() throws Exception {
		Collection<String> fileNumbers = Arrays.asList("012", "017", "021", "026", "042", "048", "058", "175", "180",
				"535");
		ArrayList<Graph> graphs = new ArrayList<>();
		for (String ext : fileNumbers) {
			String fileName = "NEWAISearchfile" + ext + ".txt";
			Graph g = new Graph(fileName);
			graphs.add(g);
		}
		System.out.println("Graphs were read successfully");
		return graphs;
	}
	
}
