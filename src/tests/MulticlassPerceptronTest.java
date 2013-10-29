package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.Loader;
import core.MulticlassPerceptron;

public class MulticlassPerceptronTest {

	/* Test sets - values for OR and AND boolean functions
	 * with 2 and 3 variables (data sets linearly separables). */
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

	double[][] multiLabels;
	
	/** Test whether some manipulation data functions in the training 
	 * set are functioning properly */
	@Before
	public void testMethods(){
		
		MulticlassPerceptron multiclassPerceptron = new MulticlassPerceptron(orWith3Variables,100);
		double [] expected= {1, 1, -1, -1};
		double [] actual= multiclassPerceptron.getExample(1);
		
		assertEquals(multiclassPerceptron.getTarget(0),-1);
		assertEquals(multiclassPerceptron.getTarget(7),1);
		
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i],0);	
		}
	}

	@Test
	public void executeTest() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		
		StringBuilder localPath = new StringBuilder(System.getProperty("user.dir")); 
		localPath.append(File.separator+"datasets"+File.separator+"dataset");
		
		Loader loader = new Loader();
		File file = new File(localPath.toString());
		
		try {
			loader.parser(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		multiLabels = loader.getMatrix();
		
		Class<? extends MulticlassPerceptronTest> clazz = this.getClass();
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
		int currentPrediction;
		
		//Calls the PerceptronRule for getting the weight vector
		MulticlassPerceptron multiclass = new MulticlassPerceptron(dataTraining, 100);
		
		multiclass.trainingMulticlassPerceptron();
		 
		System.out.println("Learned Weights: ");
		
		//Print the weight learned
		for (Map.Entry<Integer, double[]> entry : multiclass.weightVectorsMap.entrySet()) {
			double[] w = entry.getValue();
			System.out.print("Label = " + entry.getKey() + '\n');
			for (int i = 0; i < w.length; i++)
				System.out.print(w[i] + " ");
			System.out.println('\n');
		}

		for (int index = 0; index < dataTraining.length; index++) {
			//Assign current data values.
			currentExample = multiclass.getExample(index);
			currentLabel = multiclass.getTarget(index);
			
			currentPrediction = multiclass.predictLabel(currentExample, currentLabel);
			
			//Print input data for the current test
			printInputData(currentExample, currentLabel, currentPrediction);
			//make current assertion!
			assertEquals(currentPrediction, currentLabel);
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