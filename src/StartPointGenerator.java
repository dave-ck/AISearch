public class StartPointGenerator {
	
	// done
	public static FullTour Greedy(Graph g) throws Exception {
		PartialTour pt = new PartialTour(g);
		pt.append(0);
		while (pt.greedyAppend()) {
			//System.out.println(pt);
		}
		System.out.println("Greedy tour: " + pt);
		return new FullTour(pt);
	}
	
	public static FullTour lookAhead(Graph g) throws Exception {
		PartialTour pt = new PartialTour(g);
		pt.append(0);
		while (pt.bestAppend(4)) {
			//System.out.println(pt);
		}
		System.out.println("Incomplete pt: " + pt);
		while (pt.greedyAppend()) {
			// System.out.println(pt);
		}
		System.out.println("Best - first tour: " + pt);
		return new FullTour(pt);
	}
	
	public static FullTour gatedSearch(Graph g) throws Exception {
		PartialTour currentPartialTour = new PartialTour(g);
		currentPartialTour.append(0);
		PartialTourWeightComparator comparator = new PartialTourWeightComparator();
		FixedSizePriorityQueue<PartialTour> tourQueue = new FixedSizePriorityQueue<>(10000, comparator);
		tourQueue.add(currentPartialTour);
		while (!currentPartialTour.isComplete()) {
			currentPartialTour = tourQueue.pollFirst();
			System.out.println("\n Current queue: " + tourQueue + "\n");
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
