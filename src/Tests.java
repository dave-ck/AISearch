import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Tests {
	
	public static void main(String[] args) throws Exception {
		parameterize_genetic();
	
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
			TSP.writeTours("TestGraphs", tours);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void parameterize_genetic() throws Exception{
		Graph g = new Graph("NEWAISearchfile535.txt");
		int eachSetup_seconds = 10;
		int eachSetup_millis = 1000*eachSetup_seconds;
		ArrayList<Integer> proliferations = new ArrayList<>(Arrays.asList(1000, 1500, 2000, 3000, 5000));
		ArrayList<Double> radiations = new ArrayList<>(Arrays.asList(0.3, 0.4, 0.45));
		for (int pro : proliferations){
			for (double rad : radiations){
				double time = System.currentTimeMillis();
				int iterations = 0;
				GeneticSolver genGreedy = new GeneticSolver(g, 1000, pro, 2, rad);
				genGreedy.greedyPopulate();
				while (System.currentTimeMillis() < time + eachSetup_millis){
					genGreedy.run(1);
					iterations++;
				}
				System.out.println("Greedy pop, " + iterations +" iterations, pro: " + pro + " rad: " + rad + " weight: " + genGreedy.getBestTour().getWeight());
			}
		}
		
		
		
		GeneticSolver genRandom = new GeneticSolver(g, 1000, 1200, 2, 0.3);
		genRandom.randomPopulate();
		genRandom.run(10);
		System.out.println("Best for random pop:" + genRandom.getBestTour().getWeight());
		
		
		
	}
	
	public static void bestGreedyStart() throws Exception{
		ArrayList<FullTour> bests = new ArrayList<>();
		for (Graph g : TSP.readGraphs()){
			for (int i = 0; i<g.getSize(); i++){
				FullTour greedy = StartPointGenerator.Greedy(g, i);
				System.out.println("Size, "+ g.getSize() + ", Startcity, "+ i+ ", Weight, " + greedy.getWeight());
			}
		}
	}
	
	public static void timer_test(int pow){
		double time = System.currentTimeMillis();
		int bound = (int) Math.pow(535, pow);
		int t = 0;
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(0);
		System.out.println("Bound: "+bound);
		for (int i = 0; i < bound; i++){
			t++;
			arr.add(t);
			arr.remove(0);
		}
		System.out.println(arr.get(0));
		double timeTaken = System.currentTimeMillis()-time;
		System.out.println("Time taken for pow = " + pow + ": " + (timeTaken/1000) + "\n");
	}
}
