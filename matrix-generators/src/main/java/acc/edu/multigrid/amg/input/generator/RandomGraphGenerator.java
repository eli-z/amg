package acc.edu.multigrid.amg.input.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomGraphGenerator {
	private static Logger logger = LogManager.getLogger(RandomGraphGenerator.class);
	
	private static final Function<Double, Boolean> generateMember = sparceFactor -> Math.random() <= sparceFactor;
	private static final Supplier<Integer> existenceSupplier = () -> 1;

	
	public static <T extends Number> URI generateUndirectedWeighted(int dimension, final Supplier<T> weightSupplier, final double sparceFactor){
		return generateAndSaveToFile(dimension, sparceFactor, weightSupplier, (row, col) -> col < row,
		(a,b) -> Math.min(a, b), "/undirected_" + dimension + ".mtx", "symmetric");
	}
	
	public static <T extends Number> URI generateDirectedWeighted(int dimension, Supplier<T> weightSupplier, boolean countNeighbours, double sparceFactor){
		return null;
//		List<Pair<T>> listedGraph = generateGraph(dimension, sparceFactor, weightSupplier, (row, col) -> col != row);
	}	
	public static <T extends Number> URI generateDirected(int dimension, boolean countNeighbours, double sparceFactor){
		return generateDirectedWeighted(dimension, existenceSupplier, countNeighbours, sparceFactor);
	}
	
	public static <T extends Number> URI generateUndirected(int dimension, double sparceFactor){
		return generateUndirectedWeighted(dimension, existenceSupplier, sparceFactor);
	}
	
	private static <T extends Number> URI generateAndSaveToFile(int dimension,
	 final double sparceFactor, 
	 final Supplier<T> weightSupplier,
	 BiFunction<Integer, Integer, Boolean> memberCheck, 
	 ToIntBiFunction<Integer, Integer> rangeFunction, 
	 String fileName, 
	 String mtProperty){
		List<Pair<T>> listedGraph = generateGraph(dimension, sparceFactor, weightSupplier, memberCheck, rangeFunction);
		try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))){
			pw.println("%%MatrixMarket matrix coordinate pattern " + mtProperty);
			pw.println(dimension + " " + dimension + " " + listedGraph.size());
			listedGraph.stream()
				.map(
					pairVal -> (String)(pairVal.getRow() + " " + pairVal.getCol() + (weightSupplier == existenceSupplier ? "" : (" " + pairVal.getValue())))
					)
				.forEach(pw::println);
			return Paths.get("/undirected_" + dimension + ".mtx").toUri();
		} catch (IOException e) {
			logger.error("Cannot write to file",e);
			return null;
		}
		
	}
	private static <T extends Number> List<Pair<T>> generateGraph(final int dimension,
			final double sparceFactor,
			final Supplier<T> weightSupplier,
			final BiFunction<Integer, Integer, Boolean> memberCheck,
			final ToIntBiFunction<Integer, Integer> rangeFunction
			){
		 final List<Pair<T>> result = Collections.synchronizedList(new LinkedList<>());
  		 IntStream.rangeClosed(1, dimension)
  		 	.parallel()
  		 	.forEach(row -> IntStream.range(1, rangeFunction.applyAsInt(row, dimension))
  		 						.parallel()
  		 						.filter(col -> generateMember.apply(sparceFactor) && memberCheck.apply(row, col))
  		 						.forEach(colN -> result.add(new Pair<T>(row, colN, weightSupplier.get()))));
  		 
  		 result.sort(new Comparator<Pair<T>>() {

 			@Override
 			public int compare(Pair<T> o1, Pair<T> o2) {
 				int diff = o1.getRow() - o2.getRow();
 				if(diff == 0)
 					return o1.getCol() - o2.getCol();
 				return diff;
 			}
 		});
		return result;		
	}
	
	
	private static class Pair<T extends Number>{
		private int row;
		private int col;
		private T value;
		
		public Pair(int row, int col, T value) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}


		public int getCol() {
			return col;
		}


		public T getValue() {
			return value;
		}

	}
}
