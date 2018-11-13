
public class TSP {

	public static void main(String[] args) throws Exception{
		Graph g = new Graph("NEWAISearchfile012.txt");
		System.out.println(Greedy(g));
	}

	public static FullTour Greedy(Graph g) throws Exception{
		PartialTour pt = new PartialTour(g);
		pt.append(0);
		while(pt.greedyAppend()){
			System.out.println(pt);
		}
		return new FullTour(pt);
	}

}
