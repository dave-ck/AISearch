import java.lang.reflect.Array;
import java.util.ArrayList;

public class StartPointGenerator {
	
	// done
	public static FullTour Greedy(Graph g) throws Exception {
		PartialTour pt = new PartialTour(g);
		pt.append(0);
		while (pt.greedyAppend()) {
			// System.out.println(pt);
		}
		return new FullTour(pt);
	}
	
	
}
