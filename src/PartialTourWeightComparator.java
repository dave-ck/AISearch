import java.util.Comparator;

public class PartialTourWeightComparator implements Comparator<PartialTour> {
	/**
	@return the difference in weights of the two tours: w = x-y
	 */
	@Override
	public int compare(PartialTour x, PartialTour y)
	{
		return x.computeWeight()-y.computeWeight();
	}
}


