package acc.edu.multigrid.amg.smoothers.concrete;

import java.util.TreeSet;
import java.util.stream.IntStream;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.RowEntry;
import acc.edu.multigrid.amg.smoothers.faces.Smoother;

public class JacobiSmoother implements Smoother {

	@Override
	public double[] smooth(SparseMatrix matrix, double[] bVec, double[] xVec) {
		double[] result = new double[xVec.length];
		
		IntStream.range(0, xVec.length).parallel().forEach(
				index -> result[index] =  rowSum(index, bVec[index], xVec, matrix.getMatrixRow(index))
				);
		return result;
	}

	private double rowSum(int index, double b, double[] xVec, TreeSet<RowEntry> matrixRow) {
		//TODO think about some other method of fetching Aii element ?!!!! 
		return  (1 / matrixRow.floor(new RowEntry(index + 1, 0)).getValue()) * (b - matrixRow
				.stream()
				.parallel()
				.filter(row -> row.getCol() != index + 1)
				.mapToDouble(row -> xVec[row.getCol() - 1] * row.getValue())
				.sum());
	}

	@Override
	public String getName() {
		return "JACOBI";
	}

}
