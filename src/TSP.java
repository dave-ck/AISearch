import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TSP {
	
	public static void main(String[] args) throws Exception {
		ArrayList<FullTour> greedyTours = new ArrayList<>();
		ArrayList<FullTour> annealingTours = new ArrayList<>();
		ArrayList<FullTour> hillClimbingTours = new ArrayList<>();
		for(Graph graph : readGraphs()){
			System.out.println("Currently processing graph of size "+graph.getSize());
			//Greedy
			greedyTours.add(StartPointGenerator.Greedy(graph));
			// Hill Climbing
			hillClimbingTours.add(SimAnnealer.HillClimb(StartPointGenerator.Greedy(graph)));
			//Annealing
			//hillClimbingTours.add(SimAnnealer.SimAnneal(StartPointGenerator.Greedy(graph)));
		}
		System.out.println("writeToFile called");
		
		writeToFile("Greedy", greedyTours);
		writeToFile("HillClimb", hillClimbingTours);
		writeToFile("Annealing", annealingTours);
	}
	
	//TODO: change ...file58.txt to ...file058.txt
	public static void writeToFile(String pathName, ArrayList<FullTour> tours) throws Exception {
		new File(pathName).mkdirs();
		for (FullTour tour : tours) {
			String outputFileName = "tourNEWAISearchfile" + tour.size() + ".txt";
			PrintWriter writer = new PrintWriter(pathName + "/" + outputFileName, "UTF-8");
			writer.println(tour);
			System.out.println("Successfully printed tour" + outputFileName);
			writer.close();
		}
	}
	
	
	public static ArrayList<Graph> readGraphs() throws Exception {
		Collection<String> fileNumbers = Arrays.asList("012", "017", "021", "026", "042", "048", "058", "175", "180",
				"535");
		ArrayList<Graph> graphs = new ArrayList<>();
		for (String ext : fileNumbers) {
			String fileName = "NEWAISearchfile" + ext + ".txt";
			System.out.println("Reading graph from: " + fileName);
			Graph g = new Graph(fileName);
			graphs.add(g);
		}
		return graphs;
	}
	
}
