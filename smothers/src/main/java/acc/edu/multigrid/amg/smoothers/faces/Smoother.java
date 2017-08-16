package acc.edu.multigrid.amg.smoothers.faces;

import java.util.TreeSet;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.RowEntry;

public interface Smoother {
	public double[] smooth(SparseMatrix matrix, double[] bVec, double[] xVec, int iterations);
	public String getName();
	
	default double rowSum(int index, double b, double[] xVec, TreeSet<RowEntry> matrixRow) {
		//TODO think about some other method of fetching Aii element ?!!!! 
		return  (1 / matrixRow.floor(new RowEntry(index, 0)).getValue()) * (b - matrixRow
				.stream()
				.parallel()
				.filter(row -> row.getCol() != index)
				.mapToDouble(row -> xVec[row.getCol()] * row.getValue())
				.sum());
	}


}
