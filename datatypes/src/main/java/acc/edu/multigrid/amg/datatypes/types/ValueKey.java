package acc.edu.multigrid.amg.datatypes.types;



public class ValueKey implements Comparable<ValueKey>{

	private static final String separator = ":";

	private int row;
	private int column;
	private String stringKey;

	public ValueKey(int row, int column)
	{
		this.row = row;
		this.column = column;
		stringKey = String.valueOf(row) + separator + String.valueOf(column);
	}

	public boolean isRow(int row)
	{
		return this.row == row;
	}

	public boolean isColumn(int column)
	{
		return this.column == column;
	}

	@Override
	public int compareTo(ValueKey o) {
		int rowVal = row - o.row;
		int colVal = column - o.column;
		if(rowVal == 0)
			return colVal;
		return rowVal;
	}

	@Override
	public String toString() {
		return "ValueKey [row=" + row + ", column=" + column + "]";
	}

	@Override
	public int hashCode() {
		return stringKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueKey other = (ValueKey) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}



}
