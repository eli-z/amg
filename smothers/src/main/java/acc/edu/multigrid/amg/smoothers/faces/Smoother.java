package acc.edu.multigrid.amg.smoothers.faces;

import acc.edu.multigrid.amg.datatypes.faces.SparseMatrix;

public interface Smoother {
	public double[] smooth(SparseMatrix matrix);

}
