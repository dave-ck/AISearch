import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tests {

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph("NEWAISearchfile012.txt");
		//g.PrintEdgeWeights();
		PartialTour pt = new PartialTour(g);
		for(int i = 0; i < 12; i++){
			pt.append(i);
		}
		System.out.println(pt);
		System.out.println(pt.size());
		//System.out.println(pt.backTrack(3));
	}

}
