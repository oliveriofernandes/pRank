package tests;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.CSR;
import util.Example;
import coreCSR.RankPerceptron;

public class RankingPerceptronTestCRS {
	double docsA[][] ={ {5.0,1.0,3.0,1.0,2.0,3.0},{7.0,3.0,9.0,9.0,4.0,5.0},{2.0,9.0,5.0,2.0,6.0,1.0},
	{3.0,4.0,1.0,8.0,9.0,2.0}, {8.0,1.0,3.0,4.0,2.0,4.0} };
	
	double docsB[][] ={ {3.0,9.0,4.0,8.0,7.0,3.0}, {7.0,9.0,1.0,7.0,2.0,2.0}, {1.0,7.0,5.0,3.0,1.0,1.0} };
	
	HashMap<Integer,Example> examples;
	
	@Before
	public void settings(){
		
		
		examples = new HashMap<Integer,Example>();
		examples.put(1, new Example(new CSR(docsA)));
		examples.put(2, new Example(new CSR(docsB)));
		
		
	}	
	@Test
	public void execute(){
		
		RankPerceptron rp = new RankPerceptron(examples, 100);
		
		Assert.assertEquals(rp.weights.length, 6);
		double[] weights = {1.0, 2.0, 3.0, 4.0, 5.0};
		rp.weights = weights;
		//rp.computeActivation( examples.get(0) );
		rp.training();
		System.out.println(rp.weights);
		
		
		
	}

}
