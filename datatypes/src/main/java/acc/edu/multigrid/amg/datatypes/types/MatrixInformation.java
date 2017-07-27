package acc.edu.multigrid.amg.datatypes.types;

public class MatrixInformation {
	private boolean symetric;
	private int rows;
	private int columns;
	private int values;
	
	public boolean isSymetric() {
		return symetric;
	}
	public void setSymetric(boolean symetric) {
		this.symetric = symetric;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public int getValues() {
		return values;
	}
	public void setValues(int values) {
		this.values = values;
	}
	public MatrixInformation(boolean symetric, int rows, int columns, int values) {
		super();
		this.symetric = symetric;
		this.rows = rows;
		this.columns = columns;
		this.values = values;
	}
	
	
}
