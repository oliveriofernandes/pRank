package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Example;
import util.LoaderMSLR;

import core.RankPerceptron;

public class PairWiseTestMSLR {

	public void settings(){
	}
	
	@Test
	public void execute(){
		
		System.out.println("init..");
	
		
		String path = System.getProperty("user.dir").concat(
				File.separator + "datasets" + File.separator + "MSLR-WEB10K" + File.separator + "Fold1" + File.separator
						+ "sampleTrain.txt");

	//	List<Example> examples  = LoaderMSLR.getDataset(path);
		
		//RankPerceptron rp = new RankPerceptron(examples, 100);
		
		//Assert.assertEquals(rp.weights.length, 136);
		//double[] weights = {1.0, 2.0, 3.0, 4.0, 5.0};
		//rp.weights = weights;
		//rp.computeActivation( examples.get(0) );
	//	rp.training();
	//	System.out.println(rp.weights);
		
		
		
	}

}
