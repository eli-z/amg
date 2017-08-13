package acc.edu.multigrid.amg.smoothers.concrete;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.factory.DatatypeFactory;

public class JacobiTest {
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
		double[] result = new JacobiSmoother().smooth(testMatrix, bVec, xVec);
		double[] expected = {0.6, 2.27272, -1.1,1.875};
		assertArrayEquals(expected, result, 0.001);
		
		expected = new double[]{1.04727, 1.7159, -0.80522, 0.88522};
		result = new JacobiSmoother().smooth(testMatrix, bVec, result);
		assertArrayEquals(expected, result, 0.001);
		
		expected = new double[]{0.93263, 2.05330, -1.0493, 1.13088};
		result = new JacobiSmoother().smooth(testMatrix, bVec, result);
		assertArrayEquals(expected, result, 0.001);
		
		expected = new double[]{1.01519, 1.95369, -0.9681, 0.97384};
		result = new JacobiSmoother().smooth(testMatrix, bVec, result);
		assertArrayEquals(expected, result, 0.001);
		
		expected = new double[]{0.98899, 2.0114, -1.0102, 1.02135};
		result = new JacobiSmoother().smooth(testMatrix, bVec, result);
		assertArrayEquals(expected, result, 0.001);
		
	}

}
