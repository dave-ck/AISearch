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
		int currentWeight = initialTour.getWeight();
		FullTour currentBest = initialTour;
		int currentBestWeight = initialTour.getWeight();
		FullTour successor;
		int successorWeight;
		int time = 0;
		int timeChangeless = 0;
		double currentTemperature = startTemp;
		int timeOut = 1000000 + scheduleLength()/100;
		System.out.println("Estimated schedule length: " + scheduleLength());
		while (currentTemperature > approxZero){
			double adaptedTemperature = currentTemperature;
			//make cooling non-monotonic adaptive
			double adaptiveFactor = 1 + ((currentWeight-currentBestWeight)/currentWeight);
			adaptedTemperature *= adaptiveFactor;
			//comment out these 2 lines to make monotonic and non-adaptive
			successor = currentTour.randomReversedSuccessor();
			currentWeight = currentTour.getWeight();
			successorWeight = successor.getWeight();
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
				timeChangeless = 0;
				currentBest = currentTour;
				currentBestWeight = currentWeight;
				System.out.println("Time: " + time + " adaptive factor: " + adaptiveFactor +
						", current temperature: " + currentTemperature +  ", current best weight: " + currentBestWeight);
			}
			time++;
			timeChangeless++;
			currentTemperature /= beta;
			
			if (timeChangeless > timeOut){
				timeChangeless = 0;
				TSP.writeTour("Backups/Waddup"+System.currentTimeMillis(), currentBest);
			}
			
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
		int optimalWeight = initial.getWeight();
		long time = System.nanoTime();
		System.out.println(time);
		do {
			for (FullTour ft : current.getSuccessors()) {
				if (ft.getWeight() < optimalWeight) {
					optimal = ft;
					optimalWeight = ft.getWeight();
				}
			}
		} while (!current.equals(optimal) && System.nanoTime() < time + 50000000); //timeout after 50 seconds
		return optimal;
	}
	
}

