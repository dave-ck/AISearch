import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class SimAnnealer {
	
	public static FullTour SimAnneal(FullTour initialTour, double startTemp, double beta) {
		System.out.println("SimAnneal Called");
		Random random = new Random();
		ArrayList<Double> schedule = Schedule(startTemp, beta, 0.00001);
		FullTour currentTour = initialTour;
		FullTour currentBest = initialTour;
		int consecutiveRejects = 0;
		int time = 0;
		// TODO: implement functionality to keep "best tour so far"
		for (double temperature : schedule) {
			FullTour successor = currentTour.randomSuccessor();
			int deltaE = successor.computeWeight() - currentTour.computeWeight();
			if (deltaE <= 0) {
				currentTour = successor;
			} else {
				double probability = Math.exp(-1 * deltaE / temperature);
				if (random.nextDouble() < probability) {
					currentTour = successor;
					/*System.out.println("Chose \"downhill\" solution with probability " + probability +
							" after " + consecutiveRejects + " consecutive rejects (at time " + time +
							", with temperature " + temperature +").");
					consecutiveRejects = 0;
				} else {
					consecutiveRejects++;*/
				}
			}
			if (currentBest.computeWeight() > currentTour.computeWeight()) {
				currentBest = currentTour;
			}
			time++;
		}
		return currentBest;
	}
	
	public static ArrayList<Double> Schedule(double startTemp, double beta, double zeroApprox) {
		double temperature = startTemp;
		
		ArrayList<Double> schedule = new ArrayList<>();
		schedule.add(startTemp);
		int currentIndex = 0;
		while (temperature > zeroApprox) {
			temperature = schedule.get(currentIndex) / beta;
			schedule.add(temperature);
			currentIndex++;
		}
		return schedule;
	}
	
	public static FullTour HillClimb(FullTour initial) {
		FullTour current = initial;
		FullTour optimal = initial;
		int optimalWeight = initial.computeWeight();
		long time = System.nanoTime();
		System.out.println(time);
		do {
			for (FullTour ft : current.getSuccessors()) {
				if (ft.computeWeight() < optimalWeight) {
					optimal = ft;
					optimalWeight = ft.computeWeight();
				}
			}
		} while (!current.equals(optimal) && System.nanoTime() < time + 50000000); //timeout after 50 seconds
		return optimal;
	}
	
}

