import java.io.FileNotFoundException;

public class Tests {

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph("NEWAISearchfile012.txt");
		//g.PrintEdgeWeights();
		PartialTour pt = new PartialTour(g);
		System.out.println(SimAnnealer.ExponentialSchedule(50, 1.002, 0.0001).size());
		
	}

}
