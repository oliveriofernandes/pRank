package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import util.Loader;
import core.MulticlassPerceptron;

public class MTest {

	double[][] multiLabels;
	
	@Test
	public void execute(){
	
	StringBuilder localPath = new StringBuilder(System.getProperty("user.dir")); 
	localPath.append(File.separator+"datasets"+File.separator+"dataset");
	
	Loader loader = new Loader();
	File file = new File(localPath.toString());
	
	
	try {
		loader.parser(file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	multiLabels = loader.getMatrix();
	
	
	
	MulticlassPerceptron multiclass = new MulticlassPerceptron(multiLabels, 100);
	
	localPath.setLength(0);
	
	localPath.append(new StringBuilder(System.getProperty("user.dir")));
	
	localPath.append(File.separator+"datasets"+File.separator+"testset");
	
	Loader testLoader = new Loader();
	File fileTest = new File(localPath.toString());
	
	try {
		testLoader.parser(fileTest);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	multiLabels = testLoader.getMatrix();
	
	double[] currentExample;
	int currentLabel;
	int currentPrediction;
	
	
	for (int index = 0; index < multiLabels.length; index++) {
		//Assign current data values.
		currentExample = multiclass.getExample(index);
		currentLabel = multiclass.getTarget(index);
		
		currentPrediction = multiclass.predictLabel(currentExample, currentLabel);
		
		//Print input data for the current test
		printInputData(currentExample, currentLabel, currentPrediction);
		//make current assertion!
		assertEquals(currentPrediction, currentLabel);
	}
	
	
	}
	
private void printInputData(double[] ds, double target, double output) {
		
		System.out.print( "Input: ");
		for (int i = 0; i < ds.length; i++) {
			System.out.print(ds[i] + " ");
		}
		System.out.println(" -- Target: " + target + " -- output: " + output);
	}
	
}
