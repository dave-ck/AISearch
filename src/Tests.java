import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Tests {
	
	public static void main(String[] args) throws Exception {
		Graph g = new Graph("NEWAISearchfile535.txt");
		GeneticSolver genGreedy = new GeneticSolver(g, 100, 120, 2, 0.1);
		genGreedy.greedyPopulate();
		genGreedy.run(30);
		
		GeneticSolver genRandom = new GeneticSolver(g, 100, 120, 2, 0.1);
		genRandom.randomPopulate();
		genRandom.run(30);
		
		
	}
	
	
	public static void test1() throws FileNotFoundException {
		Graph g = new Graph("NEWAISearchfile012.txt");
		ArrayList<Integer> fullList = new ArrayList<>();
		BoundedPriorityQueue<Integer> priorityQueue =
				new BoundedPriorityQueue<Integer>(500, Comparator.<Integer>naturalOrder());
		Random r = new Random();
		for (int i = 0; i < 60000; i++) {
			int value = r.nextInt(500);
			priorityQueue.add(value);
			fullList.add(value);
		}
		System.out.println(priorityQueue);
		double startTemp = 10, alpha = 0.1, beta = 1.000001, approxZero = 0.005;
		
		SimAnnealer anne = new SimAnnealer(startTemp, beta, approxZero);
		
		System.out.println(anne.scheduleLength());
		ArrayList<FullTour> tours = new ArrayList<>();
		
		try {
			for (Graph graph : TSP.readGraphs()) {
				tours.add(new FullTour(g));
			}
			TSP.writeToFile("TestGraphs", tours);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
