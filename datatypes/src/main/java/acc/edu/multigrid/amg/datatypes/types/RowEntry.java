package acc.edu.multigrid.amg.datatypes.types;

public class RowEntry {
	private int col;
	private double value;
	
	
	public RowEntry() {
	
	}
	
	
	public RowEntry(int col, double value) {
		super();
		this.col = col;
		this.value = value;
	}


	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RowEntry other = (RowEntry) obj;
		if (col != other.col)
			return false;
		return true;
	}
	
	
	
	

}
