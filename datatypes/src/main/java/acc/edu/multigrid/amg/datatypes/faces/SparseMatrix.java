package acc.edu.multigrid.amg.datatypes.faces;

import java.util.TreeSet;

import acc.edu.multigrid.amg.datatypes.types.RowEntry;

public interface SparseMatrix {
	public int getRowsCount();
	public int getColumnsCount();
	public int getValuesCount();
	public boolean isZeroBasedIndexes();
	public void honorZeroBasedIndexes();
	public TreeSet<RowEntry> getMatrixRow(int row);
	public boolean hasValueAt(int row, int col);
	
}
