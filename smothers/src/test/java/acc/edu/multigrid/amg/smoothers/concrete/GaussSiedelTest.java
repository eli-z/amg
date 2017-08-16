package acc.edu.multigrid.amg.smoothers.concrete;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.factory.DatatypeFactory;

public class GaussSiedelTest {
	private SparseMatrix testMatrix;
	private double[] bVec;
	private double[] xVec;
	@Before
	public void setUp() throws Exception {
		double[][] test = {
				{10, -1, 2, 0},
				{-1, 11, -1, 3},
				{2, -1, 10, -1},
				{0, 3, -1, 8}
		};
		testMatrix = DatatypeFactory.createSimpleSparseMatrix(test);
		bVec = new double[]{6,25,-11,15};
		xVec = new double[4];
	}

	@Test
	public void test() {
		double[] result = new GaussSiedelSmoother().smooth(testMatrix, bVec, xVec, 4);
		double[] expected = {1, 2, -1,1};
		assertArrayEquals(expected, result, 0.001);
	
	}
}
