package acc.edu.multigrid.amg.smoothers.concrete;

import java.util.stream.IntStream;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.smoothers.faces.Smoother;

public class JacobiSmoother implements Smoother {


	public double[] smooth(SparseMatrix matrix, double[] bVec, double[] xVec) {
		double[] result = new double[xVec.length];
		
		IntStream.range(0, xVec.length).parallel().forEach(
				index -> result[index] =  rowSum(index, bVec[index], xVec, matrix.getMatrixRow(index))
				);
		return result;
	}
	

	@Override
	public String getName() {
		return "JACOBI";
	}



	@Override
	public double[] smooth(SparseMatrix matrix, double[] bVec, double[] xVec, int iterations) {
		for(int i = 0; i < iterations; i++)
			xVec = smooth(matrix, bVec, xVec);
		return xVec;
	}

}
