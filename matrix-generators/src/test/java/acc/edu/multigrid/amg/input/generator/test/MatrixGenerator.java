package acc.edu.multigrid.amg.input.generator.test;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

import acc.edu.multigrid.amg.input.generator.RandomGraphGenerator;

public class MatrixGenerator {
	private static final Function<Double, Boolean> generateMember = sparceFactor -> Math.random() <= sparceFactor;
	@Test
	public void test() {
/*		double sum = 0;
		for(int i = 0; i < 1000000; i++)
		{
			for(int j = 0; j < i; j++)
			{
				sum++;
			}
			if(i % 1000 == 0 )
				System.out.println(i + " done");
		}
		int trueV = 0;
		long time = System.nanoTime();
		long timeM = System.currentTimeMillis();
		int runs = 100000;
		for(int i = 0; i < runs; i++)
		{
			if(generateMember.apply(0.0001))
				trueV++;
		}
		time = (System.nanoTime() - time) / runs;
		timeM = (System.currentTimeMillis() - timeM) / runs;
		System.out.println(time + " " + timeM + " " + trueV);*/
//		assertTrue(RandomGraphGenerator.generateUndirected(1000000, .00001d) != null);
	}

}
