package acc.edu.multigrid.amg.datatypes.types;

public class MatrixEntry {
	private int row;
	private int col;
	private double value;
	
	
	public MatrixEntry(int row, int col, double value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public double getValue() {
		return value;
	}
	
	
}
