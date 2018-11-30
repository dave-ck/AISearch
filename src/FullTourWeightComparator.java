import java.util.Comparator;

public class FullTourWeightComparator implements Comparator<FullTour> {
	/**
	@return the difference in weights of the two tours: w = x-y
	 */
	@Override
	public int compare(FullTour x, FullTour y)
	{
		return x.computeWeight()-y.computeWeight();
	}
}


