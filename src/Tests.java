import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Tests {

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph("NEWAISearchfile012.txt");
		g.PrintEdgeWeights();
		
		
	}

}
