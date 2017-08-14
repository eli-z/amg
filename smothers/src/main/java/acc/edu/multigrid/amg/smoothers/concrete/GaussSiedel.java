package acc.edu.multigrid.amg.smoothers.concrete;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.RowEntry;
import acc.edu.multigrid.amg.smoothers.faces.Smoother;

public class GaussSiedel implements Smoother {

	@Override
	public double[] smooth(SparseMatrix matrix, double[] bVec, double[] xVec) {
		List<ParalellExecutables> paraExces = fetchParalellIndices(matrix);
		return null;
	}

	private List<ParalellExecutables> fetchParalellIndices(SparseMatrix matrix) {
		Set<Integer> black = new HashSet<>();
		Set<Integer> white = new HashSet<>();
		Set<Integer> ignoreList = new HashSet<>();
		TreeSet<RowEntry> row;
		while(ignoreList.size() < matrix.getRowsCount())
		{
			for(int i = 0; i < matrix.getRowsCount(); i++)
			{
				row = matrix.getMatrixRow(i);
				if(!white.contains(i))
				{
					int current = i;
					black.add(i);
					row.stream().filter(item -> item.getCol() != current)
						.forEach(item -> {
							if(black.contains(item.getCol()))
								black.remove(item.getCol());
							white.add(item.getCol());
						
					});
				}
			}
//			else if(!black.contains(i)){
				
//			}
		}
		return null;
	}

	@Override
	public String getName() {
		return "GAUSS_SIEDEL";
	}

	
	private static class ParalellExecutables{
		
	}
}
