package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.CRS;
import util.Example;
import coreCSR.RankPerceptron;

public class RankingPerceptronTestCRS {
		
	List<Example> examples;
	
	@Before
	public void settings(){
	
		double docsA[][] ={ {5.0,1.0,3.0,1.0,2.0,3.0},{7.0,3.0,9.0,9.0,4.0,5.0},{2.0,9.0,5.0,2.0,6.0,1.0},
				{3.0,4.0,1.0,8.0,9.0,2.0}, {8.0,1.0,3.0,4.0,2.0,4.0} };

		double docsB[][] ={ {3.0,9.0,4.0,8.0,7.0,3.0}, {7.0,9.0,1.0,7.0,2.0,2.0}, {1.0,7.0,5.0,3.0,1.0,1.0} };
		Example exampleA;
		Example exampleB;
		
		
		exampleA = new Example(new CRS(docsA),1);
		exampleB = new Example(new CRS(docsB),2);
		
		examples = new ArrayList<Example>();
		
		examples.add(exampleA);
		examples.add(exampleB);
		
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
