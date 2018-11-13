import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph implements Cloneable {

	private int[][] edgeWeights;
	private int size;
	private String filename;
	private int maxWeight;

	public Graph(String filename) throws FileNotFoundException {
		this.filename = filename;
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		String scannedString = "";
		scanner.nextLine();
		String sizeLine = scanner.nextLine();
		size = Integer.parseInt(sizeLine.substring(7, sizeLine.length() - 1));
		// create a matrix n*n
		edgeWeights = new int[size][size];
		// make all the edge weights into a one-line string
		while (scanner.hasNextLine()) {
			scannedString += scanner.nextLine();
		}
		scanner.close();
		maxWeight = 0;
		// for each element in the input, do fancy things
		int x = 0, y = 0, z = 0;
		for (String i : scannedString.split(",")) {
			int weight = Integer.parseInt(i);
			if(weight>maxWeight){
				maxWeight = weight;
			}
			if (x == y) {
				x++;
			}
			edgeWeights[x][y] = weight;
			edgeWeights[y][x] = weight;
			x++;
			if (x == size) {
				y++;
				z++;
				x = z;
			}
		}
	}

	public Graph(int size, int[][] edgeWeights) {
		this.size = size;
		this.edgeWeights = edgeWeights;
	}

	public int weight(int from, int to) {
		return edgeWeights[from][to];
	}

	public int[][] matrix() {
		return edgeWeights;
	}

	public void deleteNode(int node) {
		// delete a node from the graph, along with all incoming and outgoing
		// edges
	}

	public Graph clone() {
		int[][] cloneWeights = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cloneWeights[i][j] = edgeWeights[i][j];
			}
		}
		return new Graph(size, cloneWeights);
	}

	public void PrintEdgeWeights() {
		String out = "";
		for (int[] row : edgeWeights) {
			out += "\n";
			for (int weight : row) {
				out += weight + ",";
			}
		}
		System.out.println(out);
	}

	public String getFilename() {
		return filename;
	}


	public int[][] getEdgeWeights() {
		return edgeWeights;
	}

	public void setEdgeWeights(int[][] edgeWeights) {
		this.edgeWeights = edgeWeights;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}

}
