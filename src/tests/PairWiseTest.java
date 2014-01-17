package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import core.RankPerceptron;

public class PairWiseTest {
	double doc0[] = {5.0,1.0,3.0,1.0,2.0,3.0};
	double doc1[] = {7.0,3.0,9.0,9.0,4.0,5.0};
	double doc2[] = {2.0,9.0,5.0,2.0,6.0,1.0};
	double doc3[] = {3.0,4.0,1.0,8.0,9.0,2.0};
	double doc4[] = {8.0,1.0,3.0,4.0,2.0,4.0};
	
	double doc5[] = {3.0,9.0,4.0,8.0,7.0,3.0};
	double doc6[] = {7.0,9.0,1.0,7.0,2.0,2.0};
	double doc7[] = {1.0,7.0,5.0,3.0,1.0,1.0};
	
	List<double[][]> examples;
	
	@Before
	public void settings(){
		
		//Documents included in example 1
		double[][] example1 = {doc0,doc1,doc2,doc3,doc4};
		for (int i = 0; i < example1.length; i++) {
			System.out.println('\n');
			for (int j = 0; j < example1[i].length; j++) {
				System.out.print(" " + example1[i][j]);	
			}
			
		}
		System.out.println('\n');
		
		//Documents included in example 2
		double[][]example2 = {doc5,doc6,doc7};
	
		examples = new ArrayList<double[][]>();
		examples.add(example1);
		examples.add(example2);
	}
	
	@Test
	public void execute(){
		
		System.out.println("init..");
		
		RankPerceptron rp = new RankPerceptron(examples, 100);
		
		Assert.assertEquals(rp.weights.length, 5);
		double[] weights = {1.0, 2.0, 3.0, 4.0, 5.0};
		rp.weights = weights;
		//rp.computeActivation( examples.get(0) );
		rp.training();
		System.out.println(rp.weights);
		
		
		
	}

}