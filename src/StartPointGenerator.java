public class StartPointGenerator {
	
	// done
	public static FullTour Greedy(Graph g, int startCity) throws Exception {
		PartialTour pt = new PartialTour(g);
		pt.append(startCity);
		while (pt.greedyAppend()) {
			//System.out.println(pt);
		}
		return new FullTour(pt);
	}
	
	
	public static FullTour gatedSearch(Graph g) throws Exception {
		PartialTour currentPartialTour = new PartialTour(g);
		currentPartialTour.append(0);
		PartialTourWeightComparator comparator = new PartialTourWeightComparator();
		BoundedPriorityQueue<PartialTour> tourQueue = new BoundedPriorityQueue<>(g.getSize()*10, comparator);
		tourQueue.add(currentPartialTour);
		while (!currentPartialTour.isComplete()) {
			currentPartialTour = tourQueue.pop(0);
			System.out.println("Current tour: " + currentPartialTour);
			System.out.println("Weight: " + currentPartialTour.computeWeight());
			System.out.println("Queue size: " + tourQueue.size() + "\n");
			for (int i : currentPartialTour.getUnvisitedCities()) {
				tourQueue.add(currentPartialTour.extend(i));
			}
		}
		return new FullTour(currentPartialTour);
		
		
	}
	
}
