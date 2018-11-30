import java.util.HashSet;

public class PartialTour extends Tour {
	
	private HashSet<Integer> unvisitedCities;
	private int weight;
	private int tail;
	
	// done & tested
	public PartialTour(Graph g) {
		super(g);
		this.unvisitedCities = new HashSet<Integer>();
		/*
		 * cities referred to by the index they are held at (n-1 where n is the
		 * of the city in the input file)
		 */
		for (int i = 0; i < getMatrix().length; i++) {
			unvisitedCities.add(i);
		}
	}
	
	
	public FullTour FullTour() {
		if (isComplete()) {
			return new FullTour(getGraph(), getCities());
		}
		System.out.println("An error occurred, returning ordered tour");
		return new FullTour(getGraph());
	}
	
	// getter
	public HashSet<Integer> getUnvisitedCities() {
		return unvisitedCities;
	}
	
	// done & tested (not for weight functionality)
	public boolean append(int target) {
		if (unvisitedCities.remove(target)) {
			/*
			 * This code is made redundant by the computeWeight() method - could
			 * be redone if there is a need to optimize, but computing weight is
			 * O(n) --> not an issue
			 *
			 */
			/**if (!getCities().isEmpty()) {
			 weight += getGraph().weight(tail, target);
			 }**/
			
			getCities().add(target);
			this.tail = target;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean greedyAppend() {
		if (this.getCities().size() == 0) {
			return false;
		}
		int bestNeighbor = 0;
		int bestWeight = getGraph().getMaxWeight();
		for (int i : unvisitedCities) {
			if (bestWeight >= getGraph().weight(tail, i)) {
				bestWeight = getGraph().weight(tail, i);
				bestNeighbor = i;
			}
		}
		append(bestNeighbor);
		if (this.isComplete()) {
			return false;
		}
		return true;
	}
	
	
	private int getTail() {
		return getCities().get(getCities().size() - 1);
	}
	
	private int heuristic2ahead() {
		//find the 2 edges out that are "cheapest", sum together
		//int bestI, bestJ;
		int bestWeight = getGraph().getMaxWeight() * 2;
		for (int i : unvisitedCities) {
			for (int j : unvisitedCities) {
				int currentWeight = getGraph().weight(getTail(), i) + getGraph().weight(i, j);
				if (i != j && currentWeight < bestWeight) {
					//bestI = i;
					//bestJ = j;
					bestWeight = currentWeight;
				}
				
			}
		}
		return bestWeight;
	}
	
	// done & tested
	public PartialTour extend(int target) {
		PartialTour newTour = new PartialTour(getGraph());
		for (int i : getCities()) {
			newTour.append(i);
		}
		newTour.append(target);
		return newTour;
	}
	
	// done & tested
	public PartialTour backTrack(int distance) {
		if (getCities().size() < distance) {
			return new PartialTour(getGraph());
		}
		PartialTour newTour = new PartialTour(getGraph());
		for (int i : getCities().subList(0, getCities().size() - distance)) {
			newTour.append(i);
		}
		return newTour;
	}
	
	// done
	// incorrect implementation
	public String toString() {
		String stringForm = super.toString();
		stringForm += "\nUnvisited cities: ";
		for (Integer i : unvisitedCities) {
			stringForm += i + ",";
		}
		return stringForm;
	}
	
}
