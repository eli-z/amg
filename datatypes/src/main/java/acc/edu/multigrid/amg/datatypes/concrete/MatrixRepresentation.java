package acc.edu.multigrid.amg.datatypes.concrete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import acc.edu.multigrid.amg.datatypes.faces.Datatype;
import acc.edu.multigrid.amg.datatypes.types.ValueKey;




@Deprecated
public class MatrixRepresentation implements Datatype{
//	private Map<Integer,Set<Integer>> flagMap = null;
	private ArrayList<Set<Integer>> flagArray = null;
	private Map<ValueKey, Double> valueMap = null;
	private boolean onesMatrix = false;
	private boolean skew = false;
	private boolean symetric = false;
	
	private int totalRows;
	private int totalCols;
	private int totalValues;
	

	public MatrixRepresentation(boolean onesMatrix, int totalRows, int totalCols, int totalValues, boolean symetric, boolean skew) {
		this.onesMatrix = onesMatrix;
		this.totalRows = totalRows;
		this.totalCols = totalCols;
		this.totalValues = totalValues;
		this.symetric = symetric;
		this.skew = skew;
//		flagMap = new HashMap<>(totalRows);
		flagArray = new ArrayList<>(totalRows);
		for(int i = 0; i< totalRows; i++)
			flagArray.add(new HashSet<>());
		
		if(!onesMatrix)
			valueMap = new HashMap<>(totalValues);
	}

	public void insertValue(int row, int column, Double value){
		Set<Integer> colSet = flagArray.get(row - 1);
		colSet.add(column);
		if(!isOnesMatrix())
			valueMap.put(new ValueKey(row, column), value);
			
	}
	
	public boolean isOnesMatrix() {
		return onesMatrix;
	}

	public boolean isSkew() {
		return skew;
	}

	public boolean isSymetric() {
		return symetric;
	}

	public int getTotalRows() {
		return totalRows;
	}


	public int getTotalCols() {
		return totalCols;
	}



	public int getTotalValues() {
		return totalValues;
	}



	public int getIntValue(int row, int column){
		return getValue(row, column).intValue();
	}
	
	public double getDoubleValue(int row, int column){
		return getValue(row, column).doubleValue();
	}
	
	private Number getValue(int row, int column){
		if(onesMatrix)
		{
			if(hasValueStrict(row, column))
				return one;
			if(symetric)
				if(hasValueStrict(column, row))
					if(skew)
						return minusone;
					else
						return one;
		}
		else
		{
			Double val = valueMap.get(new ValueKey(row, column));
			if(val != null)
				return val;
			else if(symetric)
			{
				val = valueMap.get(new ValueKey(column, row));
				if(val != null)
				{
					if(skew)
						val *= -1d;
					return val;
				}
			}
		}
		return zero;
	}
	
	public boolean hasValueAt(int row, int column){
		
		if(hasValueStrict(row, column))
			return true;
		if(symetric && hasValueStrict(column, row))
			return true;
		return false;
/*		Set<Integer> colSet = flagArray.get(row - 1);
		if(colSet != null && colSet.contains(column))
			return true;
		else if(symetric)
		{
			colSet = flagArray.get(column - 1);
			return (colSet != null && colSet.contains(column));
		}
		return false;
*/	}
	
	private boolean hasValueStrict(int row, int column){
		Set<Integer> colSet = flagArray.get(row - 1);
		return (colSet != null && colSet.contains(column));
	}
	
	

	@Override
	public String toString() {
		return "MatrixRepresentation [onesMatrix=" + onesMatrix + ", skew=" + skew + ", symetric=" + symetric
				+ ", totalRows=" + totalRows + ", totalCols=" + totalCols + ", totalValues=" + totalValues + "]";
	}






}
