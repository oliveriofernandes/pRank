package tests;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import core.BinaryPerceptron;
import core.Perceptron;

public class BinaryPerceptronTest {

	/* Test sets - values for OR and AND boolean functions
	 * with 2 and 3 variables (data sets linearly separables). */
	double orWith2Variables [][]  = { { -1.0, -1, -1 }, { 1, -1, 1 },
			{ -1, 1, 1 }, { 1, 1, 1 } };

	double andWith2Variables[][] = { { -1.0, -1, -1 }, { 1, -1, -1 },
			{ -1, 1, -1 }, { 1, 1, 1 } };
	
	double orWith3Variables[][] = { { -1, -1, -1, -1 }, { 1, -1, -1, 1 },
		{ -1, 1, -1, 1 }, { -1, -1, 1, 1 }, { 1, 1, -1, 1 },
			{ 1, -1, 1, 1 }, { -1, 1, 1, 1 }, { 1, 1, 1, 1 } };

	double andWith3Variables[][] = { { -1, -1, -1, -1 }, { 1, -1, -1, -1 },
			{ -1, 1, -1, -1 }, { -1, -1, 1, -1 }, { 1, 1, -1, -1 },
			{ 1, -1, 1, -1 }, { -1, 1, 1, -1 }, { 1, 1, 1, 1 } };

	
	/** Test whether some manipulation data functions in the training 
	 * set are functioning properly */
	@Before
	public void testMethods(){
		
		BinaryPerceptron bPerceptron= new BinaryPerceptron(orWith3Variables,100);
		double [] expected= {1, 1, -1, -1};
		double [] actual= bPerceptron.getExample(1);
		
		assertEquals(bPerceptron.getTarget(0),-1);
		assertEquals(bPerceptron.getTarget(7),1);
		
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i],0);	
		}
	}

	@Test
	public void executeTest() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		
		Class<? extends BinaryPerceptronTest> clazz = this.getClass();
		//vector of training sets
		Field[] fields = clazz.getDeclaredFields();
		
		// Each attribute is a training set and
		// for each one's, it's executed the perceptron test!
		for (int i = 0; i < fields.length; i++) {
			
			fields[i].setAccessible(true);
			if (fields[i].getType().isArray()) 
				//This call executes all necessary assertions for each 
				//data training (field) 
				makeTest(fields[i]);
		}
	}

	private void makeTest(Field field) throws IllegalArgumentException, 
	IllegalAccessException {
		
		//Obtains a dataTraining
		double[][] dataTraining = (double[][]) field.get(this);
		
		double[] currentExample;
		int currentLabel;
		double activation;
		
		//Calls the PerceptronRule for getting the weight vector
		BinaryPerceptron bPerceptron= new BinaryPerceptron(dataTraining, 100);
		
		//Get the weight perceptron
		double [] weights = bPerceptron.trainingBinaryPerceptron();
		
		//Print the weight learned 
		System.out.print("Learned Weight: ");
		for (int i = 0; i < weights.length; i++)
			System.out.print(weights[i] + " ");
		
		System.out.println('\n');

		for (int index = 0; index < dataTraining.length; index++) {
			//assign current data values.
			currentExample = bPerceptron.getExample(index);
			currentLabel = bPerceptron.getTarget(index);
			activation = Perceptron.activation(weights, currentExample);
			
			//Print input data for the current test
			printInputData(currentExample, currentLabel, activation);
			//make assertions!
			assertEquals(activation*currentLabel>=0, true);
		}

		System.out.println('\n');
	}

	private void printInputData(double[] ds, double target, double output) {
		
		System.out.print( "Input: ");
		for (int i = 0; i < ds.length; i++) {
			System.out.print(ds[i] + " ");
		}
		System.out.println(" -- Target: " + target + " -- output: " + output);
	}
}