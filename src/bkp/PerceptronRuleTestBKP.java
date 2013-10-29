package bkp;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import util.Loader;
import core.Perceptron;

public class PerceptronRuleTestBKP {

	/* Set points linearly separable - values for OR and AND boolean functions
	 * with 2 and 3 variables. */
	double orWith2Variables[][] = { { -1.0, -1, -1 }, { 1, -1, 1 },
			{ -1, 1, 1 }, { 1, 1, 1 } };

	double andWith2Variables[][] = { { -1.0, -1, -1 }, { 1, -1, -1 },
			{ -1, 1, -1 }, { 1, 1, 1 } };
	
	double orWith3Variables[][] = { { -1, -1, -1, -1 }, { 1, -1, -1, 1 },
		{ -1, 1, -1, 1 }, { -1, -1, 1, 1 }, { 1, 1, -1, 1 },
			{ 1, -1, 1, 1 }, { -1, 1, 1, 1 }, { 1, 1, 1, 1 } };

	double andWith3Variables[][] = { { -1, -1, -1, -1 }, { 1, -1, -1, -1 },
			{ -1, 1, -1, -1 }, { -1, -1, 1, -1 }, { 1, 1, -1, -1 },
			{ 1, -1, 1, -1 }, { -1, 1, 1, -1 }, { 1, 1, 1, 1 } };

	double learningRate = 0.5;

	
	@Before
	public void testMethods(){
		
		PerceptronRuleBKP pRule = new PerceptronRuleBKP(orWith3Variables, this.learningRate);
		
		double [] expected= {1, 1, -1, -1};
		double [] actual= pRule.getExample(1);
		
		
		assertEquals(pRule.getTarget(0),-1);
		assertEquals(pRule.getTarget(7),1);
		
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i],0);	
		}
		
		
	}

	@Test
	public void executeTest() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		
		Class<? extends PerceptronRuleTestBKP> clazz = this.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		
		// Each double matrix attribute is a training set and
		// for each ones, it's executed the perceptron test!
		for (int i = 0; i < fields.length; i++) {
			
			fields[i].setAccessible(true);
			
			if (fields[i].getType().isArray()) {

				//makeTest(fields[i]);
				makeTest2(fields[i]);
				
			}
		}

		StringBuilder localPath = new StringBuilder(System.getProperty("user.dir")); 
		localPath.append(File.separator+"datasets"+File.separator+"dataset");
		
		Loader loader = new Loader();
		File file = new File(localPath.toString());
		
		try {
			loader.parser(file);
			
			PerceptronRuleBKP perceptron = new PerceptronRuleBKP(loader.getMatrix(), 0.5);
			
		//	ArrayList<double[]> weights = perceptron.trainingMulticlass();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

//	private void makeTest(Field field) throws IllegalArgumentException, IllegalAccessException {
//		
//		int maxCount = 1000;
//		//It obtains a dataTraining
//		double[][] dataTraining = (double[][]) field.get(this);
//		
//		//Call the PerceptronRule for getting the weight vector
//		PerceptronRuleBKP pRule = new PerceptronRuleBKP(dataTraining, this.learningRate);
//		
//		//Get the weight perceptron
//		double [] weights = pRule.training();
//		
//		//It instantiates a perceptron inputs
//		//double[][] inputs = new double[dataTraining.length][weights.length];
//
//		System.out.print("Weight: ");
//		for (int i = 0; i < weights.length; i++) {
//			System.out.print(weights[i] + " ");
//		}
//		System.out.println('\n');
//
//		pRule.training( orWith3Variables, maxCount );
//		
//		for (int index = 0; index < dataTraining.length; index++) {
//			
//			outputData(pRule.getExample(index), pRule.getTarget(index), Perceptron.activation( weights, pRule.getExample(index) ) );
//			
//			assertEquals(Perceptron.activation(weights, pRule.getExample(index))*pRule.getTarget(index),true );
//		}
//		System.out.println('\n');
//	}
	
	private void makeTest2(Field field) throws IllegalArgumentException, IllegalAccessException {
		
		//It obtains a dataTraining
		double[][] dataTraining = (double[][]) field.get(this);
		
		//Call the PerceptronRule for getting the weight vector
		PerceptronRuleBKP pRule = new PerceptronRuleBKP(dataTraining, this.learningRate);
		
		//Get the weight perceptron
		double [] weights = pRule.training();
		
		if(weights.length!=dataTraining[0].length-1)
			dataTraining = pRule.adjustExamples(dataTraining);
		
		//It instantiates a perceptron inputs
		//double[][] inputs = new double[dataTraining.length][weights.length];

		System.out.print("Weight: ");
		for (int i = 0; i < weights.length; i++) {
			System.out.print(weights[i] + " ");
		}
		System.out.println('\n');

	//	List<double[]> ws = pRule.trainingMulticlass();
		
		for (int index = 0; index < dataTraining.length; index++) {
			
			outputData(pRule.getExample(index, dataTraining), pRule.getTarget(index, dataTraining), Perceptron.activation(weights, pRule.getExample(index)));
			
			assertEquals(Perceptron.activation(weights, pRule.getExample(index, dataTraining))*pRule.getTarget(index, dataTraining)>=0,true);
		}

//		for (double[] ds : ws) {
//			System.out.println("weight " );
//			for (int i = 0; i < ds.length; i++) {
//				System.out.println(ds[i]);
//				
//			}
//		} 
		
		System.out.println('\n');
	}

	private void outputData(double[] ds, double target, double output) {
		
		System.out.print( "Input: ");
		for (int i = 0; i < ds.length; i++) {
			System.out.print(ds[i] + " ");
		}
		System.out.println(" -- Target: " + target + " -- output: " + output);
	}

}