import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class SimAnnealer {
	
	public static FullTour SimAnneal(FullTour initialTour, double startTemp, double beta, double approxZero) {
		//TODO: IMPLEMENT SO CLONING ONLY OCCURS WHEN A NEW OPTIMUM IS FOUND, NOT AT EVERY STEP.
		System.out.println("SimAnneal Called");
		Random random = new Random();
		//ArrayList<Double> schedule = ExponentialSchedule(startTemp, beta, 0.00001);
		FullTour currentTour = initialTour;
		FullTour currentBest = initialTour;
		int time = 0;
		for(double currentTemperature : QuadraticMultiplicativeSchedule(startTemp, beta, approxZero)){
			FullTour successor = currentTour.randomReversedSuccessor();
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
		}
		return currentBest;
	}

	
	public static ArrayList<Double> ExponentialSchedule(double startTemp, double beta, double approxZero) {
		double temperature = startTemp;
		
		ArrayList<Double> schedule = new ArrayList<>();
		schedule.add(startTemp);
		int currentIndex = 0;
		while (temperature > approxZero) {
			temperature = schedule.get(currentIndex) / beta;
			schedule.add(temperature);
			currentIndex++;
		}
		return schedule;
	}
	
	public static ArrayList<Double> QuadraticMultiplicativeSchedule(double startTemp, double beta, double approxZero) {
		System.out.println("Generating schedule...");
		double temperature = startTemp;
		ArrayList<Double> schedule = new ArrayList<>();
		schedule.add(startTemp);
		int currentIndex = 0;
		while (temperature > approxZero) {
			temperature = startTemp/(beta*currentIndex*currentIndex+1);
			schedule.add(temperature);
			currentIndex++;
		}
		System.out.println("Successfully generated schedule of length: " + schedule.size() +".");
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

