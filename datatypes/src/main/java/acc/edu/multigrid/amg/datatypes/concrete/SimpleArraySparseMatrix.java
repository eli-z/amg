package acc.edu.multigrid.amg.datatypes.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.MatrixEntry;

public class SimpleArraySparseMatrix implements SparseMatrix{
	private static Logger logger = LogManager.getLogger(SimpleArraySparseMatrix.class);
	
	private int rows;
	private int cols;
	private int values;
	private int[] rowArray;
	private int[] colArray;
	private double[] valueArray;
	private boolean symetric;
	private ArrayList<MatrixEntry> entryArray;
	

	
	
	public SimpleArraySparseMatrix(int rows, int cols, int values, boolean symetric) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.symetric = symetric;
		this.values = values;
		rowArray = new int[values + 1];
		colArray = new int[values + 1];
		valueArray = new double[values + 1];
		entryArray = new ArrayList<>(values);
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
	
	public void insertValue(int row, int col, double value, int index)
	{
		if(index > 0 && index <= values)
		{
			rowArray[index] = row;
			colArray[index] = col;
			valueArray[index] = value;
			entryArray.add(new MatrixEntry(row, col, value));
		}
	}

	public void sortEntryArray()
	{
		long start = System.currentTimeMillis();
		Collections.sort(entryArray, new Comparator<MatrixEntry>() {
			@Override
			public int compare(MatrixEntry o1, MatrixEntry o2) {
				int rowDiff = o1.getRow() - o2.getRow();
				int colDif = o1.getCol() - o2.getCol();
				return rowDiff == 0 ? colDif : rowDiff;
			}
		});
		logger.info("Sorting values took: " + (System.currentTimeMillis() - start) + "ms");
	}
	@Override
	public String toString() {
		return "SimpleArraySparseMatrix [rows=" + rows + ", cols=" + cols + ", values=" + values + "]";
	}
	
	
	
}
