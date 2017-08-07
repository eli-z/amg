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
		
		IntStream.rangeClosed(1, matrix.getRowsCount()).parallel().forEach(
				index -> result[index] =  rowSum(index, bVec[index], xVec, matrix.getMatrixRow(index))
				);
		return result;
	}

	private double rowSum(int index, double b, double[] xVec, TreeSet<RowEntry> matrixRow) {
		
		return  (1 / matrixRow.floor(new RowEntry(index, 0)).getValue()) *(b - matrixRow
				.stream()
				.parallel()
				.filter(row -> row.getCol() != index)
				.mapToDouble(row -> xVec[row.getCol()] * row.getValue())
				.sum());
	}

	@Override
	public String getName() {
		return "JACOBI";
	}

}
