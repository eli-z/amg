package acc.edu.multigrid.amg.datatypes.concrete;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.RowEntry;

public class SimpleArraySparseMatrix implements SparseMatrix{
	private static Logger logger = LogManager.getLogger(SimpleArraySparseMatrix.class);
	private static Comparator<RowEntry> rowComparator = new Comparator<RowEntry>() {
		
		@Override
		public int compare(RowEntry o1, RowEntry o2) {
			return o1.getCol() - o2.getCol();
		}
	};
	private int rows;
	private int cols;
	private int values;
	private boolean symetric;
	private TreeSet<RowEntry>[] sparseMatrix;
	

	
	
	@SuppressWarnings("unchecked")
	public SimpleArraySparseMatrix(int rows, int cols, int values, boolean symetric) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.symetric = symetric;
		if(rows != cols && symetric)
			logger.error("Non square matrix cannot be symmetric: rows {}, cols {}", rows, cols);
		this.values = symetric ? values * 2 : values;
//		rowArray = new int[values + 1];
//		colArray = new int[values + 1];
//		valueArray = new double[values + 1];
//		entryArray = new ArrayList<>(values);
		sparseMatrix = new TreeSet[rows + 1];
		IntStream.rangeClosed(1, rows).parallel().forEach(index -> sparseMatrix[index] = new TreeSet<>(rowComparator));
	}

	public boolean isSymetric() {
		return symetric;
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColumnsCount() {
		return cols;
	}

	@Override
	public int getValuesCount() {
		return values;
	}
	
	public void insertValue(int row, int col, double value)
	{
			addElementToRow(row, col, value);
	}
	
	private void addElementToRow(int row, int col, double value)
	{
		synchronized(sparseMatrix[row])
		{
			sparseMatrix[row].add(new RowEntry(col, value));
		}
	}

	@Override
	public String toString() {
		return "SimpleArraySparseMatrix [rows=" + rows + ", cols=" + cols + ", values=" + values + ", symetric="
				+ symetric + "]";
	}

	@Override
	public TreeSet<RowEntry> getMatrixRow(int row) {
		return sparseMatrix[row];
	}

	@Override
	public boolean hasValueAt(int row, int col) {
		return sparseMatrix[row].contains(new RowEntry(col, 0));

	}
}
