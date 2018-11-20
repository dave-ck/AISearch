import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TSP {

	public static void main(String[] args) throws Exception {
		greedyFile();
	}

	// done
	public static FullTour Greedy(Graph g) throws Exception {
		PartialTour pt = new PartialTour(g);
		pt.append(0);
		while (pt.greedyAppend()) {
			// System.out.println(pt);
		}
		return new FullTour(pt);
	}

	public static FullTour simulatedAnnealing(Graph g) {
		return null;

	}

	public static void greedyFile() throws Exception {
		Collection<String> fileNumbers = Arrays.asList("012", "017", "021", "026", "042", "048", "058", "175", "180",
				"535");
		for (String ext : fileNumbers) {
			String fileName = "NEWAISearchfile" + ext + ".txt";
			System.out.println(fileName);
			Graph g = new Graph(fileName);
			new File("Greedy").mkdirs();
			String outputFileName = "tourNEWAISearchfile" + ext + ".txt";
			PrintWriter writer = new PrintWriter("Greedy/" + outputFileName, "UTF-8");
			writer.println(Greedy(g));
			writer.close();
		}

	}
}
