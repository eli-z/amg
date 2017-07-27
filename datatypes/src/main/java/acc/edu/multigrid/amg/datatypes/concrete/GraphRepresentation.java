package acc.edu.multigrid.amg.datatypes.concrete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acc.edu.multigrid.amg.datatypes.faces.Datatype;
import acc.edu.multigrid.amg.datatypes.types.ValueKey;




@Deprecated
public class GraphRepresentation implements Datatype{
	private static Logger logger = LogManager.getLogger(GraphRepresentation.class);
	private ArrayList<Set<Integer>> flagArray = null;
	private int[] diagonalArray;
	private Map<ValueKey, Double> valueMap = null;
	private boolean onesMatrix = false;
	private boolean skew = false;
	private boolean symetric = false;
	private boolean countNeighbours = false;
	
	private int totalRows;
	private int totalCols;
	private int totalValues;
	

	public GraphRepresentation(boolean onesMatrix, int totalRows, int totalCols, int totalValues, boolean symetric, boolean skew, boolean countNeighbours) {
		this.onesMatrix = onesMatrix;
		this.totalRows = totalRows;
		this.totalCols = totalCols;
		this.totalValues = totalValues;
		this.symetric = symetric;
		this.skew = skew;
		diagonalArray = new int[totalRows + 1];
		this.countNeighbours = countNeighbours;
//		flagMap = new HashMap<>(totalRows);
		flagArray = new ArrayList<>(totalRows);
		
		for(int i = 0; i< totalRows; i++)
			flagArray.add(new HashSet<>());
		
		if(!onesMatrix)
			valueMap = new HashMap<>(totalValues);
	}

	public void insertValue(int row, int column, Double value){
		if(onDiagonal(row, column) && !countNeighbours)
		{
			logger.debug("Graph representation cannot have any value at place row=col");
			return;
		}
			
		Set<Integer> colSet = flagArray.get(row - 1);
		colSet.add(column);
		if(!isOnesMatrix())
			valueMap.put(new ValueKey(row, column), value);
		if(countNeighbours)
			synchronized (this) {
				diagonalArray[row]++;
			}

			
	}
	@Override
	public boolean isOnesMatrix() {
		return onesMatrix;
	}
	@Override
	public boolean isSkew() {
		return skew;
	}
	@Override
	public boolean isSymetric() {
		return symetric;
	}
	@Override
	public int getTotalRows() {
		return totalRows;
	}

	@Override
	public int getTotalCols() {
		return totalCols;
	}


	@Override
	public int getTotalValues() {
		return totalValues;
	}


	@Override
	public int getIntValue(int row, int column){
		return getValue(row, column).intValue();
	}
	
	@Override
	public double getDoubleValue(int row, int column){
		return getValue(row, column).doubleValue();
	}
	
	private Number getValue(int row, int column){
		if(onDiagonal(row, column))
			return diagonalArray[row];
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
	
	private boolean onDiagonal(int row, int column) {
		return row == column;
	}

	public boolean hasValueAt(int row, int column){
		
		if(hasValueStrict(row, column))
			return true;
		if(symetric && hasValueStrict(column, row))
			return true;
		return false;
	}
	
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
