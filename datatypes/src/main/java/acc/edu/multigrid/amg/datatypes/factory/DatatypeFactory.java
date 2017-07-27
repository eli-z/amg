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
		OfInt indexIterator = IntStream.rangeClosed(1, values).iterator();
		entryStream.forEach(val -> result.insertValue(val.getRow(), val.getCol(), val.getValue(), indexIterator.next()));
		result.sortEntryArray();
		return result;
	}
	
	public static SparseMatrix createSimpleSparseMatrix(MatrixInformation mInformation, Stream<MatrixEntry> entryStream)
	{
		return createSimpleSparseMatrix(mInformation.getRows(), mInformation.getColumns(), mInformation.getValues(), mInformation.isSymetric(), entryStream);
	}
}
