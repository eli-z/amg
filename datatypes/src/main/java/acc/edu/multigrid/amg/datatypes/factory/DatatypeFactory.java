package acc.edu.multigrid.amg.datatypes.factory;

import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import acc.edu.multigrid.amg.datatypes.concrete.SimpleArraySparseMatrix;
import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;
import acc.edu.multigrid.amg.datatypes.types.MatrixEntry;
import acc.edu.multigrid.amg.datatypes.types.MatrixInformation;

public class DatatypeFactory {
	public static SparseMatrix createSimpleSparseMatrix(int rows, int cols, int values, boolean symetric, Stream<MatrixEntry> entryStream)
	{
		SimpleArraySparseMatrix result = new SimpleArraySparseMatrix(rows, cols, values, symetric);
	//	OfInt indexIterator = IntStream.rangeClosed(1, values).iterator();
		entryStream.parallel().forEach(val -> {
			result.insertValue(val.getRow(), val.getCol(), val.getValue());
			if(symetric)
				result.insertValue(val.getCol(), val.getRow(), val.getValue());
				});
	//	result.sortEntryArray();
		return result;
	}
	
	public static SparseMatrix createSimpleSparseMatrix(MatrixInformation mInformation, Stream<MatrixEntry> entryStream)
	{
		return createSimpleSparseMatrix(mInformation.getRows(), mInformation.getColumns(), mInformation.getValues(), mInformation.isSymetric(), entryStream);
	}
	
	public static SparseMatrix createSimpleSparseMatrix(double[][] matrix)
	{
		int values = 0;
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[0].length; j++)
				if(matrix[i][j] != 0)
					values++;
		SimpleArraySparseMatrix result = new SimpleArraySparseMatrix(matrix.length, matrix[0].length, values, false);
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[0].length; j++)
				if(matrix[i][j] != 0)
					result.insertValue(i, j, matrix[i][j]);
		return result;
	}
}
