package acc.edu.multigrid.amg.smoothers.concrete;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.RowEntry;
import acc.edu.multigrid.amg.smoothers.faces.Smoother;

public class GaussSiedelSmoother implements Smoother {

	@Override
	public double[] smooth(SparseMatrix matrix, double[] bVec, double[] xVec, int iterations) {
		List<Set<Integer>> paraExces = fetchParalellIndices(matrix);
		while(iterations-- > 0)
			paraExces.stream()
				.forEach(paraEx -> paraEx.parallelStream()
							.forEach(rowNum -> xVec[rowNum] = rowSum(rowNum, bVec[rowNum], xVec, matrix.getMatrixRow(rowNum)))
						);
		return xVec;
	}

	private List<Set<Integer>> fetchParalellIndices(SparseMatrix matrix) {
		Set<Integer> black = new HashSet<>();
		Set<Integer> white = new HashSet<>();
		Set<Integer> ignoreList = new HashSet<>();
		List<Set<Integer>> result = new LinkedList<>();
		TreeSet<RowEntry> row;
		while(ignoreList.size() < matrix.getRowsCount())
		{
			black.clear();
			white.clear();
			for(int i = 0; i < matrix.getRowsCount(); i++)
			{
				if(ignoreList.contains(i))
					continue;
				row = matrix.getMatrixRow(i);
				if(!white.contains(i))
				{
					int current = i;
					black.add(i);
					row.stream().filter(item -> item.getCol() != current && !ignoreList.contains(item.getCol()))
						.forEach(item -> {
							if(black.contains(item.getCol()))
								black.remove(current);
							white.add(item.getCol());
					});
				}
			}
			if(!black.isEmpty())
			{
				result.add(new HashSet<>(black));
				ignoreList.addAll(black);
			}
		}
		return result;
	}

	@Override
	public String getName() {
		return "GAUSS_SIEDEL";
	}

	
}
