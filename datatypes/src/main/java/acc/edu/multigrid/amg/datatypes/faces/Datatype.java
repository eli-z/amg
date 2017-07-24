package acc.edu.multigrid.amg.datatypes.faces;

public interface Datatype {
	public static final Integer one = 1;
	public static final Integer minusone = -1;
	public static final Integer zero = 0;
	
	public boolean isOnesMatrix();
	public boolean isSkew();
	public boolean isSymetric();
	public int getTotalRows();
	public int getTotalCols();
	public int getTotalValues();
	public int getIntValue(int row, int column);
	public double getDoubleValue(int row, int column);
	public boolean hasValueAt(int row, int column);
	public void insertValue(int row, int column, Double value);

}
