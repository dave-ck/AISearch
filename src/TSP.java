import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TSP {
	
	public static void main(String[] args) throws Exception {
		//masterCall();     //writes tour files for every implemented algorithm
		testCall();
	}
	
	public static void testCall() throws Exception {
		ArrayList<Graph> graphs = readGraphs();
		Graph graph = graphs.get(9);
		//found there are seldom changes made at temperatures under 0.05 --> set approxZero to 0.01 at the lowest;
		// seldom changes made at temperatures above 30 --> set startTemp to 25 at the highest
		double startTemp = 10, beta = 1.0000005, approxZero=0.05;
		FullTour result = SimAnnealer.SimAnneal(StartPointGenerator.Greedy(graph), startTemp, beta, approxZero);
		System.out.println("Params: startTemp = " + startTemp + " beta = " + beta + " approxZero = " + approxZero);
		System.out.println("Result weight:" + result.computeWeight());
		System.out.println("Greedy weight:" + StartPointGenerator.Greedy(graph).computeWeight());
	}
	
	public static void masterCall() throws Exception {
		ArrayList<FullTour> greedyTours = new ArrayList<>();
		ArrayList<FullTour> annealingTours = new ArrayList<>();
		ArrayList<FullTour> hillClimbingTours = new ArrayList<>();
		for (Graph graph : readGraphs()) {
			System.out.println("Currently processing graph of size " + graph.getSize());
			//Greedy
			greedyTours.add(StartPointGenerator.Greedy(graph));
			// Hill Climbing
			hillClimbingTours.add(SimAnnealer.HillClimb(StartPointGenerator.Greedy(graph)));
			//Annealing
			annealingTours.add(SimAnnealer.SimAnneal(StartPointGenerator.Greedy(graph), 1000, 1.02, 0.01));
		}
		System.out.println("writeToFile called");
		
		writeToFile("Greedy", greedyTours);
		writeToFile("HillClimb", hillClimbingTours);
		writeToFile("Annealing", annealingTours);
	}
	
	//TODO: change ...file58.txt to ...file058.txt
	public static void writeToFile(String pathName, ArrayList<FullTour> tours) throws Exception {
		System.out.println("Printing tours...");
		new File(pathName).mkdirs();
		for (FullTour tour : tours) {
			String outputFileName = "tourNEWAISearchfile" + tour.size() + ".txt";
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
