import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph {

	private String name;
	private int[][] edgeWeights;
	private int size;
	
	public Graph(String filename) throws FileNotFoundException{
		File file = new File(filename);		
		Scanner scanner = new Scanner(file);
		String scannedString = "";
		scanner.nextLine();
		String sizeLine = scanner.nextLine();
		size = Integer.parseInt(sizeLine.substring(7,sizeLine.length()-1));
		//create a matrix n*n
		edgeWeights = new int[size][size];
		//make all the edge weights into a one-line string
		while(scanner.hasNextLine()){
			scannedString += scanner.nextLine();
		}
		scanner.close();
		//for each element in the input, do fancy things
		int x=0, y=0, z=0;
		for(String i : scannedString.split(",")){
			int weight = Integer.parseInt(i);
			if(x==y){
				x++;
			}
			edgeWeights[x][y]= weight;
			edgeWeights[y][x]= weight;
			x++;
			if(x==size){
				y++;
				z++;
				x=z;
			}
		}
		
	}
	
	public void PrintEdgeWeights(){
		String out = "";
		for(int[] row: edgeWeights){
			out += "\n";
			for(int weight: row){
				out += weight + ",";
			}
		}
		System.out.println(out);		
	}
	
}
