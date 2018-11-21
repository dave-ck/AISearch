public class SimAnnealer {
	
	public static FullTour SimAnneal(FullTour initial) {
		return null;
	}
	
	public static FullTour HillClimb(FullTour initial) {
		FullTour current = initial;
		FullTour optimal = initial;
		int optimalWeight = initial.computeWeight();
		long time = System.nanoTime();
		System.out.println(time);
		do {
			for (FullTour ft : current.getSuccessors()) {
				if(ft.computeWeight() < optimalWeight){
					optimal = ft;
					optimalWeight = ft.computeWeight();
				}
			}
		} while (!current.equals(optimal) && System.nanoTime() < time + 5000000); //timeout after 5 seconds
		return optimal;
	}
	
}

