package acc.edu.multigrid.amg.datatypes.types;

import java.util.function.Supplier;

public class MatrixEntryWithPosition extends MatrixEntry {
	private int position;
	public MatrixEntryWithPosition(int row, int col, double value, int position) {
		super(row, col, value);
		this.position = position;
	}
	public int getPosition() {
		return position;
	}
	
	
	
}
