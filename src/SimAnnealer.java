import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class SimAnnealer {
	
	public static FullTour SimAnneal(FullTour initialTour, double startTemp, double beta, double approxZero) {
		System.out.println("SimAnneal Called");
		Random random = new Random();
		//ArrayList<Double> schedule = Schedule(startTemp, beta, 0.00001);
		FullTour currentTour = initialTour;
		FullTour currentBest = initialTour;
		int time = 0;
		double currentTemperature = startTemp;
		do {
			FullTour successor = currentTour.randomSuccessor();
			int deltaE = successor.computeWeight() - currentTour.computeWeight();
			if (deltaE <= 0) {
				currentTour = successor;
			} else {
				double probability = Math.exp(-1 * deltaE / currentTemperature);
				if (random.nextDouble() < probability) {
					currentTour = successor;
				}
			}
			if (currentBest.computeWeight() > currentTour.computeWeight()) {
				currentBest = currentTour;
				System.out.println("Time: " + time + ", current temperature: " + currentTemperature +  ", current best weight: " + currentBest.computeWeight());
			}
			time++;
			currentTemperature = currentTemperature/beta;
		} while(currentTemperature>approxZero);
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

