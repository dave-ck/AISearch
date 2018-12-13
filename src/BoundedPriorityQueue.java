import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;

public class BoundedPriorityQueue<E> extends Vector<E>{
	
	private int bound;
	private Comparator<E> eComparator;
	
	public BoundedPriorityQueue(int bound, Comparator<E> eComparator){
		super();
		this.bound = bound;
		this.eComparator = eComparator;
	}
	
	public int getBound(){
		return bound;
	}
	
	@Override
	public boolean add(E e){
		if (size() <= bound){
			super.add(e);
			Collections.sort(this, eComparator);
			return true;
		}
		// if e is better than the worst existing element
		if (eComparator.compare(get(bound-1), e)>0){
			//remove the "worst" existing element, substitute with e
			this.remove(bound-1);
			this.add(e);
			Collections.sort(this, eComparator);
			return true;
		}
		return false;
	}
	
	public E pop(int index){
		E result = get(index);
		remove(index);
		return result;
	}
}
