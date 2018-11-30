import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class SimAnnealer {
	double startTemp;
	double beta;
	double approxZero;
	
	public SimAnnealer(double startTemp, double beta, double approxZero){
		this.startTemp = startTemp;
		this.beta = beta;
		this.approxZero = approxZero;
	}
	
	public  FullTour SimAnneal(FullTour initialTour) {
		//TODO: IMPLEMENT SO CLONING ONLY OCCURS WHEN A NEW OPTIMUM IS FOUND, NOT AT EVERY STEP.
		System.out.println("SimAnneal Called");
		Random random = new Random();
		FullTour currentTour = initialTour;
		int currentWeight = initialTour.computeWeight();
		FullTour currentBest = initialTour;
		int currentBestWeight = initialTour.computeWeight();
		FullTour successor;
		int successorWeight;
		int time = 0;
		double currentTemperature = startTemp;
		System.out.println("Estimated schedule length: " + scheduleLength());
		while (currentTemperature > approxZero){
			double adaptedTemperature = currentTemperature;
			//make cooling non-monotonic adaptive
			double adaptiveFactor = 1 + ((currentWeight-currentBestWeight)/currentWeight);
			adaptedTemperature *= adaptiveFactor;
			//comment out these 2 lines to make monotonic and non-adaptive
			successor = currentTour.randomReversedSuccessor();
			currentWeight = currentTour.computeWeight();
			successorWeight = successor.computeWeight();
			int deltaE = successorWeight - currentWeight;
			if (deltaE <= 0) {
				currentTour = successor;
			} else {
				double probability = Math.exp(-1 * deltaE / adaptedTemperature);
				if (random.nextDouble() < probability) {
					currentTour = successor;
					currentWeight = successorWeight;
				}
			}
			if (currentBestWeight > currentWeight) {
				currentBest = currentTour;
				currentBestWeight = currentWeight;
				System.out.println("Time: " + time + " adaptive factor: " + adaptiveFactor +
						", current temperature: " + currentTemperature +  ", current best weight: " + currentBestWeight);
			}
			time++;
			currentTemperature /= beta;
		}
		return currentBest;
	}

	//approximate schedule length given parameters
	public  int scheduleLength(){
		return (int) Math.floor((Math.log(startTemp) - Math.log(approxZero))/Math.log(beta));
	}
	

	
	public  ArrayList<Double> ExponentialSchedule() {
		System.out.println("Generating schedule...");
		double temperature = startTemp;
		ArrayList<Double> schedule = new ArrayList<>();
		schedule.add(startTemp);
		int currentIndex = 0;
		while (temperature > approxZero) {
			temperature = schedule.get(currentIndex) / beta;
			schedule.add(temperature);
			currentIndex++;
		}
		System.out.println("Successfully generated schedule of length: " + schedule.size() +".");
		return schedule;
	}
	
	public  ArrayList<Double> QuadraticMultiplicativeSchedule() {
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
	
	
	
	public  FullTour HillClimb(FullTour initial) {
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

