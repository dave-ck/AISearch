import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphFunctionalityTest {
	private int targetCity;
	private Graph g, clone;
	private int[][] matrix, cloneMatrix;
	private PartialTour pt;
	private int num = 3;	//number for testing pretty much anything, can change here
	
	@Before
	public void setUp() throws Exception {
		targetCity = 1;
		g = new Graph("NEWAISearchfile012.txt");
		matrix=g.matrix();
		clone = g.clone();
		cloneMatrix = clone.matrix();
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				matrix[i][j]++;
			}	
		}
		pt = new PartialTour(g);
		pt.append(8);
		pt.append(2);
		pt.append(3);
		pt.append(4);
		
	}
	
	@Test
	public void GraphCloneTest(){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
			assertTrue(matrix[i][j]!=cloneMatrix[i][j]);
			}	
		}		
	}
	
	@Test
	public void BackTrackTest(){
		assertTrue(pt.backTrack(num).size()==pt.size()-num);
	}
	
	@Test
	public void PartialTourSize(){
		assertTrue(pt.size()==4);
	}
	
	@Test
	public void PartialTourExtend(){
		PartialTour ptex = pt.extend(5);
		ptex.append(9);
		assertTrue(ptex.size()==6);
		assertTrue(pt.size()==4);
		assertFalse(pt.visits(9));
		assertFalse(pt.visits(5));
		assertTrue(ptex.visits(9));
		assertTrue(ptex.visits(5));
	}
	
	@Test
	public void PartialTourComplete(){
		int[] c = {0,1,5,6,7,9,10,11};
		for(int i : c){
			assertFalse(pt.isComplete());
			pt.append(i);
		}
		assertTrue(pt.isComplete());
	}
	

}
